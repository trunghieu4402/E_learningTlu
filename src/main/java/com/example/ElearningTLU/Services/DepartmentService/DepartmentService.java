package com.example.ElearningTLU.Services.DepartmentService;

import com.example.ElearningTLU.Dto.DepartmentDto;
import com.example.ElearningTLU.Dto.Response.DepartmentResponse;
import com.example.ElearningTLU.Dto.Response.MajorResponse;
import com.example.ElearningTLU.Entity.Department;
import com.example.ElearningTLU.Entity.Major;
import com.example.ElearningTLU.Entity.Person;
import com.example.ElearningTLU.Entity.Teacher;
import com.example.ElearningTLU.Repository.DepartmentRepository;
import com.example.ElearningTLU.Repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements DepartmentServiceImp {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
            private PersonRepository personRepository;

    ModelMapper mapper = new ModelMapper();
    public ResponseEntity<?> addDepartment(DepartmentDto departmentDto)
    {

//        System.out.println(departmentDto.getDepartmentId()+"//"+ departmentDto.getDepartmentName());
        if(this.departmentRepository.findById(departmentDto.getDepartmentId()).isPresent())
        {
            return new ResponseEntity<>("Khoa da ton tai",HttpStatus.CONFLICT);

        }
        Department department = this.mapper.map(departmentDto,Department.class);
        if(departmentDto.getLeadDepartment()!=null)
        {
            if(this.personRepository.findById(departmentDto.getLeadDepartment()).isEmpty())
            {
                return new ResponseEntity<>("Giao Vien "+departmentDto.getLeadDepartment()+"Khong ton Tai",HttpStatus.NOT_FOUND);
            }
            Person person = this.personRepository.findById(departmentDto.getLeadDepartment()).get();

            Teacher teacher = this.mapper.map(person,Teacher.class);
            department.setLeadDepartment(teacher);
        }


//        System.out.println(departmentDto.getDepartmentId());
        this.departmentRepository.save(department);
        return new ResponseEntity<>(department,HttpStatus.OK);
    }
    public ResponseEntity<?> getAllDepartment()
    {
        List<Department> departmentList = this.departmentRepository.findAll();
        List<DepartmentResponse> departmentResponses = new ArrayList<>();
        for(Department department:departmentList)
        {
            List<MajorResponse> Majorlist = new ArrayList<>();
            for(Major major:department.getMajorList())
            {
                MajorResponse majorResponse = this.mapper.map(major, MajorResponse.class);
                Majorlist.add(majorResponse);
            }
            DepartmentResponse response = this.mapper.map(department, DepartmentResponse.class);
            response.setListMajor(Majorlist);
            if(department.getLeadDepartment()!=null)
            {
                response.setLead(department.getLeadDepartment().getPersonId());
            }
            departmentResponses.add(response);
        }
        return new ResponseEntity<>(departmentResponses,HttpStatus.OK);
    }
    public ResponseEntity<?> getDepartmentById(String id)
    {

        if(this.SearchByID(id).isEmpty())
        {
            return new ResponseEntity<>("Thông Tin Không Tồn Tại",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(SearchByID(id).get(),HttpStatus.OK);
    }
    public ResponseEntity<?> deleteDepartmentById(String id)
    {
        if(this.SearchByID(id).isEmpty())
        {
            return new ResponseEntity<>("Thông Tin Không Tồn Tại",HttpStatus.NOT_FOUND);
        }
        this.departmentRepository.delete(this.SearchByID(id).get());
        return new ResponseEntity<>("Xoa Thanh Cong", HttpStatus.OK);

    }
    public ResponseEntity<?> editDepartment(DepartmentDto departmentDto)
    {
        if(this.SearchByID(departmentDto.getDepartmentId()).isEmpty())
        {
            return new ResponseEntity<>("Thông Tin Không Tồn Tại",HttpStatus.NOT_FOUND);
        }
        Person person = this.personRepository.findById(departmentDto.getLeadDepartment()).get();
        Teacher teacher = this.mapper.map(person,Teacher.class);
        Department department = this.departmentRepository.findById(departmentDto.getDepartmentId()).get();
        department = this.mapper.map(departmentDto,Department.class);
        department.setLeadDepartment(teacher);
        department=this.departmentRepository.save(department);
        return new ResponseEntity<>(department,HttpStatus.OK);
    }
    public Optional<Department> SearchByID(String id)
    {
        Optional<Department> department = this.departmentRepository.findById(id);
        return department;
    }
}
