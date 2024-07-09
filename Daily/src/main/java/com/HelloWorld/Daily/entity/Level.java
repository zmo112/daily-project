package com.HelloWorld.Daily.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Level extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "level")
    private Member member;

    private Long point;

    public static Level of(Member member){
        return Level.builder()
                .member(member)
                .point(0L)
                .build();
    }

    public void addPointsWhenWriteDaily(){
        this.point += 1000;
    }

    public void addPointsWhenGetLike(){
        this.point += 300;
    }

    public void minusPointsWhenGetLike(){
        this.point -= 300;
    }

    // 레벨을 계산하는 메서드
    public static Long calculateLevel(Long points) {

        long level = 0L;
        int threshold = 0;

        for (int i = 1; i <= 100; i++) {
            level = i;
            threshold += i * 1000;
            if (points <= threshold) {
                break;
            }
        }

        return level;
    }
}
