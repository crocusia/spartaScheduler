package com.example.scheduler.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long id; //고유 키
    private String name; //이름
    private String email; //이메일
    private LocalDateTime createAt; //유저 생성일
    private LocalDateTime updateAt; //유저 정보 수정일
}
