package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateRequestDto {
    @NotNull(message = "유저 아이디는 필수입니다.")
    private Long userId;        //동명이인 허용으로 인해 PK userId 사용
    @NotBlank(message = "할 일은 필수입니다.")
    @Size(max = 200, message = "할 일은 최대 200자까지 입력 가능합니다.")
    private String content;     //할 일
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
    private String password;    //비밀번호
}

