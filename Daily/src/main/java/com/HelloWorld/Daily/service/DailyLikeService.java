package com.HelloWorld.Daily.service;

import com.HelloWorld.Daily.exception.MessageCode;
import com.HelloWorld.Daily.entity.Daily;
import com.HelloWorld.Daily.entity.DailyLike;
import com.HelloWorld.Daily.entity.Level;
import com.HelloWorld.Daily.exception.customException.NotExistMemberException;
import com.HelloWorld.Daily.repository.DailyLikeRepository;
import com.HelloWorld.Daily.repository.DailyRepository;
import com.HelloWorld.Daily.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyLikeService {

    private final MemberRepository memberRepository;

    private final DailyLikeRepository dailyLikeRepository;

    private final DailyRepository dailyRepository;

    @Transactional
    public boolean doDailyLike(UserDetails userDetails, Long dailyId) {

        if (userDetails == null) {
            throw new NotExistMemberException(MessageCode.DOES_NOT_EXIST_MEMBER.getMessage());
        }

        Optional<Daily> daily = dailyRepository.findById(dailyId);

        if (daily.isEmpty()) {
            throw new NotExistMemberException(MessageCode.DOES_NOT_EXIST_MEMBER.getMessage());
        }

        // 현재 인가 유저 이름
        String userName = userDetails.getUsername();

        // Daily 글쓴이 유저 이름
        Level level = memberRepository.findByUserName(daily.get().getMember().getUsername()).get().getLevel();

        DailyLike dailyLike = dailyLikeRepository.selectDailyLikeByDaily(dailyId);

        if (dailyLike.isClickedLike(userName)) {
            dailyLike.disLike(userName);
            level.minusPointsWhenGetLike();
            return false;
        }

        if (!dailyLike.isClickedLike(userName)) {
            dailyLike.addLike(userName);
            level.addPointsWhenGetLike();
            return true;
        }

        return false;
    }
}
