package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    private String password;
}
