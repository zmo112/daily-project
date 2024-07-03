package com.HelloWorld.Daily.controller;

import com.HelloWorld.Daily.common.ApiResponse;
import com.HelloWorld.Daily.dto.DailyDTO;
import com.HelloWorld.Daily.service.DailyLikeService;
import com.HelloWorld.Daily.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DailyRestController {

    private final DailyService dailyService;

    private final DailyLikeService dailyLikeService;

    // 홈에서 띄워지는 Daily
    @GetMapping("/dailies/{offset}/{limit}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<DailyDTO.ResponseDTOs> getDailies(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int offset, @PathVariable int limit){

        if (userDetails == null) {
            return ApiResponse.createSuccess(dailyService.getDailies("anonymous", offset, limit));
        }

        return ApiResponse.createSuccess(dailyService.getDailies(userDetails.getUsername(), offset, limit));
    }

    // TODO : 클릭시 보여지는 디테일 페이지
    @GetMapping("/daily")
    public void getDailies(){

    }

    // 홈에서 띄워지는 Daily
    @GetMapping("/my/dailies/{offset}/{limit}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<DailyDTO.ResponseDTOs> getMyDailies(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int offset, @PathVariable int limit){

        if (userDetails == null) {
            return ApiResponse.createSuccess(dailyService.getMyDailies("anonymous", offset, limit));
        }

        return ApiResponse.createSuccess(dailyService.getMyDailies(userDetails.getUsername(), offset, limit));
    }

    // Daily 생성
    @PostMapping("/daily")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> postDailies(@AuthenticationPrincipal UserDetails userDetails, @RequestBody DailyDTO.RequestDTO requestDTO){

        dailyService.saveDaily(userDetails.getUsername(), requestDTO);

        return ApiResponse.createSuccessWithNoContent(); // 공통 API를 반환하기 위한 ApiResponse 객체 사용
    }

    // 좋아요 기능
    @PostMapping("/dailyLike/{dailyId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> doDailyLike(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long dailyId){
        return ApiResponse.createSuccess(dailyLikeService.doDailyLike(userDetails.getUsername(), dailyId));
    }
}
