package com.example.ElearningTLU.Dto.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SemesterResponse {
    String semesterGroupId;
    private float baseCost;
    private LocalDate start;
    private LocalDate finish;
    private LocalDate timeDangKyHoc;
    private String groupId;
    private String status;
}
