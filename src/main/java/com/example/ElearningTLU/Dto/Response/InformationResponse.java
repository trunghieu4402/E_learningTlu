package com.example.ElearningTLU.Dto.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InformationResponse {
    protected String PersonId;
    protected String FullName;
    protected String PhoneNumber;
    protected boolean Sex;
    protected String Email;
    protected String DateOfBirth;
    private String departmentId;
    private String majorId;
    private boolean status;
    private LocalDate startStudyTime;
    private LocalDate finishStudyTime;
    private float score;
    private Integer totalCredits;
}
