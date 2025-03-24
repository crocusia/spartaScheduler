package com.example.scheduler.dto;

import com.example.scheduler.entity.Task;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class TaskResponseDto {
    private final Long id;
    private final String name;
    private final String content;
    private final String updateAt;

    // 날짜 포맷 (YYYY-MM-DD HH:MM)
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.name = task.getUser().getName();
        this.content = task.getContent();
        this.updateAt = task.getUpdatedAt().format(formatter); //LocalTimeDate를 String으로 변환
    }
}
