package com.example.ElearningTLU.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public  class Course {
    @Id
    private String courseId;
    private String courseName;
    private int credits;
    private double coefficient;
    private CourseType type;
    private int requestCredits;


    @ManyToMany
    @JsonIgnoreProperties({"courseName","credits","coefficient","type","requestCredits","Prerequisites"})
    private List<Course> Prerequisites = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "Course_Department",
//            joinColumns = @JoinColumn(name = "CourseID"),
//            inverseJoinColumns = @JoinColumn(name = "DepartmentID")
//    )

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Course_SemesterGroup> courseSemesterGroups= new ArrayList<>();

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CourseDepartment> listDepartment= new ArrayList<>();

   @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
   @JsonIgnore
    private List<CourseMajor>listMajor= new ArrayList<>();

    @OneToOne(mappedBy = "course")
    @JsonIgnore
    private StatisticsStudent statisticsStudent;

//    @OneToMany(mappedBy = "course")
//    @JsonIgnore
//    List<Course_SemesterGroup> courseSemesterList = new ArrayList<>();
}