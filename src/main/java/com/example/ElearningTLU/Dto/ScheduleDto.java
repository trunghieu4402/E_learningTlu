package com.example.ElearningTLU.Dto;

import lombok.Data;

@Data
public class ScheduleDto {
    private String semesterGroupId;
    private String classRoomId;
    private String roomId;
    private int start;
    private int finish;
}
