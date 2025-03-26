package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateRequestDto {
    private Long userId;        //동명이인 허용으로 인해 PK userId 사용
    private String content;     //할 일
    private String password;    //비밀번호
}
