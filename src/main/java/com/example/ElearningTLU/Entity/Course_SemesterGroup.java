package com.example.ElearningTLU.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Course_SemesterGroup {
    @Id
    //id=mamon+maKyHoc
    private String courseSemesterGroupId;

    @ManyToOne
    @JoinColumn(name = "semesterGroupId")
    @JsonIgnore
    private Semester_Group semesterGroup;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @JsonIgnore
    private Course course;


    @OneToMany(mappedBy = "courseSemesterGroup",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Class> classList = new ArrayList<>();
}
