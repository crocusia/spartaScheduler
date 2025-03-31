package com.example.scheduler.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {
    private String password;
}
