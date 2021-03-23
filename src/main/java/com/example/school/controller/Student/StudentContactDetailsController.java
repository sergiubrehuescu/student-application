package com.example.school.controller.Student;

import com.example.school.dto.Student.StudentContactDetailsDto;
import com.example.school.mapper.StudentContactDetailsMapper;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.service.Student.StudentContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentContactDetailsController {

    @Autowired
    private StudentContactDetailsService studentContactDetailsService;

    @Autowired
    private StudentContactDetailsMapper studentContactDetailsMapper;

    @GetMapping("findStudentDetails/{studentId}")
    public StudentContactDetailsDto findStudentDetails(@PathVariable Integer studentId){
        StudentContactDetails studentContactDetails = studentContactDetailsService.findStudentDetailsById(studentId);
        return studentContactDetailsMapper.mapToDtoWithoutStudent(studentContactDetails);

    }
}
