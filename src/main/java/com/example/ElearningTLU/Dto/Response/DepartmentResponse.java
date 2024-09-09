package com.example.ElearningTLU.Dto.Response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentResponse {
    private String departmentId;
    private String departmentName;
    private String lead;
    private List<MajorResponse> ListMajor= new ArrayList<>();
}
