package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

//응답을 위한 DTO
//유저 Id가 아닌 이름을 가진다.

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;          //일정 ID
    private String name;      //유저 이름
    private String content;   //할 일
    private String updateAt;  //수정일

    // 날짜 포맷 (YYYY-MM-DD HH:MM)
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TaskResponseDto(String name, TaskDto task) {
        this.taskId = task.getTaskId();
        this.name = name;
        this.content = task.getContent();
        this.updateAt = formatter.format(task.getUpdateAt()); //날짜 변환
    }

    public TaskResponseDto(Long taskId, String name, String content, Timestamp updateAt) {
        this.taskId = taskId;
        this.name = name;
        this.content = content;
        this.updateAt = formatter.format(updateAt); //날짜 변환
    }

}
