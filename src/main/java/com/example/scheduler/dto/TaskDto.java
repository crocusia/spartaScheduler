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
    private final Timestamp updateAt; //DB 데이터를 그대로 반환

    public TaskDto(Long id, Task task, Timestamp updateTime) {
        this.taskId = id;
        this.userId = task.getUserId();
        this.content = task.getContent();
        this.updateAt = updateTime;
    }
}
