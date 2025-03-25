package com.example.scheduler.dto;

import com.example.scheduler.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;

//Service와 Repository 계층을 오가는 DTO
//userId를 갖는 DTO

@Getter
public class TaskDto {
    private final Long id;
    private final Long userId;  //유저 아이디로 서비스 레이어에서 유저 이름을 조회
    private final String content;
    private final String updateAt;

    // 날짜 포맷 (YYYY-MM-DD HH:MM)
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public TaskDto(Task task) {
        this.id = task.getId();
        this.userId = task.getUserId();
        this.content = task.getContent();
        this.updateAt = formatter.format(task.getUpdatedAt()); //LocalTimeDate를 String으로 변환
    }

    public TaskDto(Long id, Task task){
        this.id = id;
        this.userId = task.getUserId();
        this.content = task.getContent();
        this.updateAt = formatter.format(task.getUpdatedAt()); //LocalTimeDate를 String으로 변환
    }
}
