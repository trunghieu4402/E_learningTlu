package com.example.ElearningTLU.Utils;

import com.example.ElearningTLU.Entity.*;
import com.example.ElearningTLU.Entity.Class;
import com.example.ElearningTLU.Repository.SemesterGroupRepository;
import com.example.ElearningTLU.Repository.TimeTableRepository;

import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.server.Session;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class TimeTableUtils {
    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private SemesterGroupRepository semesterGroupRepository;
    public void AutoUpdateTimeTable(Semester_Group semesterGroup)
    {
        for (Course_SemesterGroup courseSemesterGroup : semesterGroup.getCourseSemesterList())
        {
            for (Class aClass : courseSemesterGroup.getClassList())
            {
                TimeTable timeTable = new TimeTable();
                timeTable.setPerson(aClass.getTeacher());
                timeTable.setStart(aClass.getStart());
                timeTable.setEnd(aClass.getFinish());
                timeTable.setClassRoomName(aClass.getName());
                timeTable.setRoomId(aClass.getRoom().getRoomId());
                timeTable.setClassRoomId(aClass.getClassRoomId());
                timeTable.setSemesterGroupId(aClass.getCourseSemesterGroup().getSemesterGroup().getSemesterGroupId());
                timeTable.setTeacherId(aClass.getTeacher().getPersonId());
                this.timeTableRepository.save(timeTable);
            }
        }

    }
//    @Scheduled(fixedRate = 10000) // Chạy vào lúc nửa đêm mỗi ngày
//    @Transactional
//    public String AutoUpdateTimeTableForStudent() {
//        LocalDate date = LocalDate.of(2024,9,11);
//                date=date.minusDays(1);
//        System.out.println(date);
//        if(semesterGroupRepository.FindSemesterGroupByNowTime(date.toString()).isEmpty())
//        {
//            return "Ko co Ky Hoc Can Cap Nhat";
//        }
//        Semester_Group semesterGroup = semesterGroupRepository.FindSemesterGroupByNowTime(date.toString()).get();
//        System.out.println(semesterGroup.getSemester());
//        for (Course_SemesterGroup courseSemesterGroup : semesterGroup.getCourseSemesterList()) {
//            for (Class aClass : courseSemesterGroup.getClassList()) {
//                for (Class_Student student : aClass.getClassStudents()) {
//                    TimeTable timeTable = new TimeTable();
//                    timeTable.setPerson(student.getStudent());
//                    timeTable.setStart(aClass.getStart());
//                    timeTable.setEnd(aClass.getFinish());
//                    timeTable.setClassRoomName(aClass.getName());
//                    timeTable.setRoomId(aClass.getRoom().getRoomId());
//                    timeTable.setClassRoomId(aClass.getClassRoomId());
//                    timeTable.setSemesterGroupId(aClass.getCourseSemesterGroup().getSemesterGroup().getSemesterGroupId());
//                    timeTable.setTeacherId(aClass.getTeacher().getPersonId());
//                    this.timeTableRepository.save(timeTable);
//                }
//            }
//        }
//        return "Da Cap Nhat Xong";
//    }
}
