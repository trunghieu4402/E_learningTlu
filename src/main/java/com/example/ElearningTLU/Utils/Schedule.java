package com.example.ElearningTLU.Utils;

import com.example.ElearningTLU.Services.SemesterService.SemesterGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Schedule {
    @Autowired
    private SemesterGroupServiceImpl semesterGroupService;
@Scheduled(cron ="0 0 0 * * *")
    public void AutoUpdateTimeTableForStudent() {
    System.out.println("Cập Nhật");
        semesterGroupService.UpdateTimeTable();
        semesterGroupService.UpdateSemester();
    }
}
