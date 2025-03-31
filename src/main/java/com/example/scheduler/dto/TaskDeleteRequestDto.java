package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TaskDeleteRequestDto {
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;    //비밀번호
}
