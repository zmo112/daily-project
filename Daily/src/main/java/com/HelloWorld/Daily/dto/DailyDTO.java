package com.HelloWorld.Daily.dto;

import com.HelloWorld.Daily.entity.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class DailyDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDTO {

        private boolean itIsPublic;

        // JAVA에서는 어노테이션 인자에 메서드 호출을 사용할 수 없다.
        @NotBlank(message = "최소 1글자 이상을 작성해야 합니다.")
        @Length(min = 2, max = 1000, message = "최소 1글자, 최대 1000 글자 이하로 작성하여야 합니다.")
        private String thanks1;

        @NotBlank(message = "최소 1글자 이상을 작성해야 합니다.")
        @Length(min = 2, max = 1000, message = "최소 1글자, 최대 1000 글자 이하로 작성하여야 합니다.")
        private String thanks2;

        @NotBlank(message = "최소 1글자 이상을 작성해야 합니다.")
        @Length(min = 2, max = 1000, message = "최소 1글자, 최대 1000 글자 이하로 작성하여야 합니다.")
        private String thanks3;

        @NotBlank(message = "최소 1글자 이상을 작성해야 합니다.")
        @Length(min = 2, max = 1000, message = "최소 1글자, 최대 1000 글자 이하로 작성하여야 합니다.")
        private String penitence1;

        @NotBlank(message = "최소 1글자 이상을 작성해야 합니다.")
        @Length(min = 2, max = 1000, message = "최소 1글자, 최대 1000 글자 이하로 작성하여야 합니다.")
        private String penitence2;

        @NotBlank(message = "최소 1글자 이상을 작성해야 합니다.")
        @Length(min = 2, max = 1000, message = "최소 1글자, 최대 1000 글자 이하로 작성하여야 합니다.")
        private String penitence3;

    }

    @Getter
    @Builder
    public static class ResponseDTOs {

        private List<ResponseDTO> responseDTOS;

        public static ResponseDTOs of(List<ResponseDTO> responseDTOS) {
            return ResponseDTOs.builder()
                    .responseDTOS(responseDTOS)
                    .build();
        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDTO {

        private String author;

        private Long dailyId;

        private int dailyLikeCnt;

        private Long authorLevel;

        private boolean isClickedLike;

        private boolean isPublic;

        private String thanks1;

        private String thanks2;

        private String thanks3;

        private String penitence1;

        private String penitence2;

        private String penitence3;

        public static ResponseDTO of(Daily daily, DailyLike dailyLike, DailyContent dailyContent, String memberName){
            return ResponseDTO.builder()
                    .author(daily.getMember().getNickName())
                    .dailyId(daily.getId())
                    .dailyLikeCnt(dailyLike.getUsersCount())
                    .authorLevel(Level.calculateLevel(daily.getMember().getLevel().getPoint()))
                    .isClickedLike(dailyLike.isClickedLike(memberName))
                    .isPublic(daily.isPublic())
                    .thanks1(dailyContent.getThanks1())
                    .thanks2(dailyContent.getThanks2())
                    .thanks3(dailyContent.getThanks3())
                    .penitence1(dailyContent.getPenitence1())
                    .penitence2(dailyContent.getPenitence2())
                    .penitence3(dailyContent.getPenitence3())
                    .build();
        }
    }

}
