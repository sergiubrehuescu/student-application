package com.example.school.controller;

import com.example.school.mapper.StudentMapper;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.service.StudentContactDetailsService;
import com.example.school.service.StudentService;
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

/*    @PostMapping("/addStudent")//todo OK OK OK OK OK OK
    public StudentDto addStudent(@RequestBody @Valid Student student){
        Student s = studentService.addStudent(student);
        StudentDto studentDto = studentMapper.mapToDto(s);
        DrivingLicense drivingLicense = s.getDrivingLicense();
        DrivingLicenseDto drivingLicenseDto = drivingLicenseMapper.mapToDto(drivingLicense);
        studentDto.setDrivingLicenseDto(drivingLicenseDto);
        //pargur fiecare session din entity...map to sessiodnto...toate sessiondto intr-o lista goala de tip sessionDTO....setare acea lista goala pe noua lista din student DTO
        for (int i = 0; i < student.getSessionList().size(); i++) {
            Session session = student.getSessionList().get(i);
            SessionDto sessionDto= sessionMapper.mapToDto(session);
            studentDto.getSessions().add(sessionDto);
        }

        return studentDto;
    }*/
}
