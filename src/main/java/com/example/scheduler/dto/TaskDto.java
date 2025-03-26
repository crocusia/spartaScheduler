package com.example.scheduler.dto;

import com.example.scheduler.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

//Service와 Repository 계층을 오가는 DTO
//userId를 갖는 DTO

@Getter
@AllArgsConstructor
public class TaskDto {
    private final Long taskId;
    private final Long userId;  //유저 아이디로 서비스 레이어에서 유저 이름을 조회
    private final String content;
    private final String updateAt;

    // 날짜 포맷 (YYYY-MM-DD HH:MM)
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TaskDto(Task task) {
        this.taskId = task.getTaskId();
        this.userId = task.getUserId();
        this.content = task.getContent();
        this.updateAt = formatter.format(task.getUpdatedAt()); //LocalTimeDate를 String으로 변환
    }

    public TaskDto(Long id, Task task, Timestamp updateTime){
        this.taskId = id;
        this.userId = task.getUserId();
        this.content = task.getContent();
        this.updateAt = formatter.format(updateTime); //LocalTimeDate를 String으로 변환
    }
}
