package com.example.ElearningTLU.Dto.Response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TranscriptResponse {
    List<CourseGradeResponse> listCourseGrade = new ArrayList<>();
    private double Score;
    private int totalCredits;
}
