package com.HelloWorld.Daily.service;

import com.HelloWorld.Daily.common.MessageCode;
import com.HelloWorld.Daily.entity.DailyLike;
import com.HelloWorld.Daily.exception.customException.NotExistMemberException;
import com.HelloWorld.Daily.repository.DailyLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DailyLikeService {

    private final DailyLikeRepository dailyLikeRepository;

    @Transactional
    public boolean doDailyLike(UserDetails userDetails, Long dailyId) {

        if (userDetails == null) {
            throw new NotExistMemberException(MessageCode.DOES_NOT_EXIST_MEMBER.getMessage());
        }

        String userName = userDetails.getUsername();

        DailyLike dailyLike = dailyLikeRepository.selectDailyLikeByDaily(dailyId);

        if (dailyLike.isClickedLike(userName)) {
            dailyLike.disLike(userName);
            return false;
        }

        if (!dailyLike.isClickedLike(userName)) {
            dailyLike.addLike(userName);
            return true;
        }

        return false;
    }
}
