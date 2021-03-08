package com.example.school.controller;

import com.example.school.mapper.StudentMapper;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.service.Student.StudentContactDetailsService;
import com.example.school.service.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentContactDetailsController {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentContactDetailsService studentContactDetailsService;

    @GetMapping("findStudentDetailsById/{studentId}")//todo OK OK OK OK OK OK
    public StudentContactDetails findStudentDetailsById(@PathVariable Integer studentId){
        return  studentContactDetailsService.findStudentDetailsById(studentId);
    }
}
