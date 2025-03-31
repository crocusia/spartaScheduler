package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class TaskDeleteRequestDto {
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자입니다.")
    private String password;    //비밀번호
}
