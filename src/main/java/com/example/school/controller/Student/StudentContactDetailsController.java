package com.example.school.controller.Student;

import com.example.school.dto.Student.StudentContactDetailsDto;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.service.Student.StudentContactDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentContactDetailsController {

    @Autowired
    private StudentContactDetailsService studentContactDetailsService;

    ModelMapper mapper = new ModelMapper();

    @GetMapping("findStudentDetails/{studentId}")
    public StudentContactDetailsDto findStudentDetails(@PathVariable Integer studentId){
        StudentContactDetails studentContactDetails = studentContactDetailsService.findStudentDetailsById(studentId);
        return mapper.map(studentContactDetails,StudentContactDetailsDto.class);
    }
}
