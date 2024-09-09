package com.example.ElearningTLU.Repository;

import com.example.ElearningTLU.Entity.Class_Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRoomStudentRepository extends JpaRepository<Class_Student,Long> {

    @Query(value = "SELECT * FROM db_e_learningtlu.class_student where class_id=:classRoomId and student_id=:studentId ;",nativeQuery = true )
    Optional<Class_Student> findByClassRoomAndStudent(@Param("classRoomId") Long id, @Param("studentId") String StudentId);

}
