package com.example.scheduler.entity;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class Task {
    private Long id;
    private User user;
    private String content;
    private String passward;
    private LocalDateTime updatedAt;
}
