package com.example.ElearningTLU.Services.SemesterService;

import com.example.ElearningTLU.Dto.SemesterGroupRequest;
import com.example.ElearningTLU.Entity.GroupStudent;
import com.example.ElearningTLU.Entity.Semester;
import com.example.ElearningTLU.Entity.*;
import com.example.ElearningTLU.Entity.Semester_Group;
import com.example.ElearningTLU.Entity.Class;
import com.example.ElearningTLU.Repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class SemesterGroupService implements SemesterGroupServiceImpl{
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private SemesterGroupRepository semesterGroupRepository;
    @Autowired
    private GroupStudentRepository groupStudentRepository;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
            private ClassRepository classRepository;
    ModelMapper mapper = new ModelMapper();
    public ResponseEntity<?> addSemesterGroup(SemesterGroupRequest semesterGroupRequest)
    {

        LocalDate now= LocalDate.now();
        Semester_Group lastSemesterGroup = new Semester_Group();
        long max= 0L;
        List<Semester_Group> list =this.semesterGroupRepository.findByGroupAndYear(semesterGroupRequest.getGroupID(),now.getYear());
        for(Semester_Group group: list)
        {
           long b =now.until(group.getFinish(),ChronoUnit.DAYS);
           if(b>max)
           {
               max=b;
               lastSemesterGroup=group;
           }

        }
        if(!list.isEmpty()&&lastSemesterGroup.getFinish().until(LocalDate.parse(semesterGroupRequest.getTimeDKHoc()),ChronoUnit.DAYS)<0)
        {
            return new ResponseEntity<>("Thoi Gian Dang Ky Bi Trung Voi Ky "+lastSemesterGroup.getSemesterGroupId(),HttpStatus.BAD_REQUEST);
        }

        Semester_Group semesterGroup = new Semester_Group();
//        semesterGroup.setSemesterGroupId();
        GroupStudent groupStudent = this.groupStudentRepository.findById(semesterGroupRequest.getGroupID()).get();
        Semester semester = new Semester();
        List<Semester>semesterList = this.semesterRepository.findAll();
        for(int i=0;i<semesterList.size();i++)
        {
            if(list.isEmpty())
            {
                semester=semesterList.get(0);
                break;
            }
            if(lastSemesterGroup.getSemester().equals(semesterList.get(i)))
            {
                if(i==2)
                {
                    i=-1;
                }
                i++;
                semester=semesterList.get(i);
            }
        }
        LocalDate TimeStart = LocalDate.parse(semesterGroupRequest.getStart());
        LocalDate TimeEnd = LocalDate.parse(semesterGroupRequest.getEnd());
        LocalDate TimeDK = LocalDate.parse(semesterGroupRequest.getTimeDKHoc());
        semesterGroup.setSemesterGroupId(semester.getSemesterId()+"_"+groupStudent.getGroupId()+"_"+TimeStart.getYear()+"_"+TimeEnd.getYear());
        semesterGroup.setSemester(semester);
        semesterGroup.setGroup(groupStudent);
        semesterGroup.setStart(TimeStart);
        semesterGroup.setFinish(TimeEnd);
        semesterGroup.setActive(false);
        if(TimeDK.until(LocalDate.now(),ChronoUnit.DAYS)>=0)
        {
            return new ResponseEntity<>("Thời Gian Mở Kỳ Học Không Phù Hợp", HttpStatus.CONFLICT);
        }
        if(TimeEnd.until(TimeStart,ChronoUnit.DAYS)>=0)
        {
            return new ResponseEntity<>("Thời Gian Mở Kỳ Học Không Phù Hợp", HttpStatus.CONFLICT);
        }
        if(TimeDK.until(TimeStart, ChronoUnit.DAYS)<=0)
        {
            return new ResponseEntity<>("Ngày đăng ký học phải trước ngày bắt đầu kỳ học mới", HttpStatus.CONFLICT);
        }
        semesterGroup.setTimeDangKyHoc(TimeDK);
        semesterGroup.setBaseCost(semesterGroupRequest.getBaseCost());
        semesterGroup=this.semesterGroupRepository.save(semesterGroup);
        return new ResponseEntity<>(semesterGroup,HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> getAllSemesterGroup()
    {
        return new ResponseEntity<>(this.semesterGroupRepository.findAll(),HttpStatus.OK);
    }
    public ResponseEntity<?> getSemesterGroupById(String id)
    {
        Optional<Semester_Group> semesterGroup = this.semesterGroupRepository.findById(id);
        if(semesterGroup.isEmpty())
        {
            return new ResponseEntity<>("Kỳ học không ton tai",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(semesterGroup.get(),HttpStatus.OK);
    }
    public ResponseEntity<?> deleteSemesterGroupById(String id)
    {
        Optional<Semester_Group> semesterGroup = this.semesterGroupRepository.findById(id);
        if(semesterGroup.isEmpty())
        {
            return new ResponseEntity<>("Kỳ học không ton tai",HttpStatus.NOT_FOUND);
        }
        if(semesterGroup.get().isActive())
        {
             return new ResponseEntity<>("Kỳ này không thể xóa",HttpStatus.BAD_REQUEST);
        }
        this.semesterGroupRepository.delete(semesterGroup.get());
        return new ResponseEntity<>("Xoa Thanh Cong",HttpStatus.OK);
    }
    public ResponseEntity<?> updateSemesterGroup(SemesterGroupRequest semesterGroupRequest)
    {
        Semester_Group semesterGroup = this.semesterGroupRepository.findById(semesterGroupRequest.getSemesterGroupId()).get();
        if(semesterGroup.isActive())
        {
            return new ResponseEntity<>("Không the chỉnh sửa kỳ này",HttpStatus.BAD_REQUEST);
        }
        else
        {
            LocalDate TimeStart = LocalDate.parse(semesterGroupRequest.getStart());
            LocalDate TimeEnd = LocalDate.parse(semesterGroupRequest.getEnd());
            LocalDate TimeDK = LocalDate.parse(semesterGroupRequest.getTimeDKHoc());
            if(TimeStart.until(LocalDate.now(),ChronoUnit.DAYS)>0 || TimeEnd.until(TimeStart,ChronoUnit.DAYS)>0)
            {
                return new ResponseEntity<>("Thời Gian Mở Kỳ Học Không Phù Hợp", HttpStatus.CONFLICT);
            }
            if(TimeDK.until(TimeStart, ChronoUnit.DAYS)<0)
            {
                return new ResponseEntity<>("Ngày đăng ký học phải trước ngày bắt đầu kỳ học mới", HttpStatus.CONFLICT);
            }
            if(TimeDK.until(LocalDate.now(),ChronoUnit.DAYS)==0)
            {
                semesterGroup.setActive(true);
            }
            else {
                semesterGroup.setActive(false);
            }
            semesterGroup.setBaseCost(semesterGroupRequest.getBaseCost());
            semesterGroup.setStart(LocalDate.parse(semesterGroupRequest.getStart()));
            semesterGroup.setFinish(LocalDate.parse(semesterGroupRequest.getEnd()));
            semesterGroup.setTimeDangKyHoc(LocalDate.parse(semesterGroupRequest.getTimeDKHoc()));
             this.semesterGroupRepository.save(semesterGroup);
             return new ResponseEntity<>("Cap Nhat Thanh cong",HttpStatus.OK);
        }
    }
    public ResponseEntity<?> getAllSemesterGroupIsNonActive()
    {
        return new ResponseEntity<>(this.semesterGroupRepository.getAllSemesterGroupByActive(false),HttpStatus.OK);
    }
    public void AutoUpdate() {
        LocalDate date = LocalDate.of(2024,9,11).minusDays(1);
        if (this.semesterGroupRepository.FindSemesterGroupByNowTime(date.toString()).isEmpty()) {
            return;
        }
        Semester_Group semester_group = this.semesterGroupRepository.FindSemesterGroupByNowTime(date.toString()).get();
        if(semester_group.isActive())
        {
            return;
        }
        semester_group.setActive(true);
        this.semesterGroupRepository.save(semester_group);
        System.out.println("Câp Nhat tai ngay: " + date + "//" + semester_group.getSemesterGroupId());
        List<Class> classList = this.classRepository.getAllClassBySemesterId(semester_group.getSemesterGroupId());
        for (Class aClass : classList) {
            TimeTable tbTeacher = new TimeTable();
            tbTeacher.setPerson(aClass.getTeacher());
            tbTeacher.setSemesterGroupId(aClass.getSemesterGroupId());
            tbTeacher.setRoomId(aClass.getRoom().getRoomId());
            tbTeacher.setClassRoomId(aClass.getClassRoomId());
            tbTeacher.setStart(aClass.getStart());
            tbTeacher.setClassRoomName(aClass.getName());
            tbTeacher.setEnd(aClass.getFinish());
            this.timeTableRepository.save(tbTeacher);
            for (Class_Student student : aClass.getClassStudents()) {
                TimeTable tbStudent = new TimeTable();
                tbStudent.setPerson(student.getStudent());
                tbStudent.setStart(aClass.getStart());
                tbStudent.setEnd(aClass.getFinish());
                tbStudent.setClassRoomName(aClass.getName());
                tbStudent.setRoomId(aClass.getRoom().getRoomId());
                tbStudent.setClassRoomId(aClass.getClassRoomId());
                tbStudent.setSemesterGroupId(aClass.getCourseSemesterGroup().getSemesterGroup().getSemesterGroupId());
                tbStudent.setTeacherId(aClass.getTeacher().getPersonId());
                this.timeTableRepository.save(tbStudent);
//            System.out.println(aClass.getClassRoomId());
            }
            semester_group.setActive(false);

            this.semesterGroupRepository.save(semester_group);
        }
    }
}
