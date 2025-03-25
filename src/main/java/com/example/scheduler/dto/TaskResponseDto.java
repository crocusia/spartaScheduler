package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//응답을 위한 DTO
//유저 Id가 아닌 이름을 가진다.

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;          //일정 ID
    private String name;      //유저 이름
    private String content;   //할 일
    private String updateAt;  //수정일

    public TaskResponseDto(String name, TaskDto task) {
        this.taskId = task.getTaskId();
        this.name = name;
        this.content = task.getContent();
        this.updateAt = task.getUpdateAt();
    }
}
