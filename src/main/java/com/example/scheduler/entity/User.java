package com.example.scheduler.entity;


import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
