package com.HelloWorld.Daily.service;

import com.HelloWorld.Daily.exception.MessageCode;
import com.HelloWorld.Daily.dto.DailyDTO;
import com.HelloWorld.Daily.entity.*;
import com.HelloWorld.Daily.exception.customException.NotExistMemberException;
import com.HelloWorld.Daily.exception.customException.WrittenDailyInADayException;
import com.HelloWorld.Daily.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;

    private final MemberRepository memberRepository;

    private final DailyLikeRepository dailyLikeRepository;

    private final DailyContentRepository dailyContentRepository;

    private final LevelRepository levelRepository;

    @Transactional(readOnly = true)
    public DailyDTO.ResponseDTOs getDailies(String memberName, int offset, int limit){

        List<Daily> dailies = getDailyList(offset, limit);

        return DailyDTO.ResponseDTOs.of(
                dailies.stream().map(
                daily -> getResponseDTO(daily, memberName)
                ).toList()
        );
    }

    @Transactional(readOnly = true)
    public DailyDTO.ResponseDTOs getMyDailies(String memberName, int offset, int limit){

        List<Daily> dailies = getMyDailyList(memberName, offset, limit);

        return DailyDTO.ResponseDTOs.of(
                dailies.stream().map(
                        daily -> getResponseDTO(daily, memberName)
                ).toList()
        );
    }

    @Transactional
    public void saveDaily(UserDetails userDetails, DailyDTO.RequestDTO requestDTO){

        if (userDetails == null) {
            throw new NotExistMemberException(MessageCode.DOES_NOT_EXIST_MEMBER.getMessage());
        }

        String userName = userDetails.getUsername();

        // 해당 유저가 하루 내로 작성한 글이 있는지 확인 -> Exception 반환
        if (dailyRepository.findDailyInDay(userName).isPresent()) {
            throw new WrittenDailyInADayException(MessageCode.WRITTEN_DAILY_IN_A_DAY.getMessage());
        }

        // 글쓴이
        Member member = memberRepository.findByUserName(userName)
                .orElseThrow(() -> new NotExistMemberException(MessageCode.DOES_NOT_EXIST_MEMBER.getMessage()));

        // Point 추가
        Level level = member.getLevel();

        level.addPointsWhenWriteDaily();

        saveEntityAboutDaily(member, requestDTO);

    }

    // Daily 관련 Entity save
    private void saveEntityAboutDaily(Member member, DailyDTO.RequestDTO requestDTO){
        Daily daily = dailyRepository.save(Daily.of(requestDTO, member));
        dailyLikeRepository.save(DailyLike.of(daily));
        dailyContentRepository.save(DailyContent.of(daily, requestDTO));
    }


    // ResponseDTO 조회 및 객체화
    private DailyDTO.ResponseDTO getResponseDTO(Daily daily, String memberName){
        return DailyDTO.ResponseDTO.of(
                daily,
                dailyLikeRepository.selectDailyLikeByDaily(daily.getId()),
                dailyContentRepository.selectDailyContentByDaily(daily.getId()),
                memberName
        );
    }

    // Daily 조회
    private List<Daily> getDailyList(int offset, int limit) {
        return dailyRepository.findDailiesWithOffsetAndLimit(PageRequest.of(offset, limit));
    }

    // My Daily 조회
    private List<Daily> getMyDailyList(String memberName, int offset, int limit) {
        return dailyRepository.findMyDailiesWithOffsetAndLimit(memberName, PageRequest.of(offset, limit));
    }
}
