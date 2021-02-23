package com.example.school.controller;

import com.example.school.dto.StudentDto;
import com.example.school.mapper.SessionMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.Student;
import com.example.school.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SessionMapper sessionMapper;


    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/listStudents")
    public List<StudentDto> listStudents() {
        List<Student> students = studentService.getStudentList();
        return students.stream()
                .map(student -> studentMapper.mapToDto(student))
                .collect(Collectors.toList());
    }

    @PostMapping("/addStudent")
    public StudentDto addStudent(@RequestBody @Valid Student student){
        logger.info("Trecut prin controller");
        Student s = studentService.addStudent(student);
        StudentDto studentDto = studentMapper.mapToDto(s);
        return studentMapper.mapToDto(student);
    }


    @DeleteMapping("/removeStudent/{id}")
    public StudentDto removeStudent(@PathVariable Integer id){
        Student student = studentService.findStudentById(id);
        studentService.removeStudent(id);
        return studentMapper.mapToDto(student);
    }

    @PutMapping("/updateStudent")
    public StudentDto updateStudent(@RequestBody @Valid Student student){
        studentService.updateStudent(student);
        return studentMapper.mapToDto(student);
    }

    @GetMapping("/findStudentById/{id}")
    public StudentDto findStudentById(@PathVariable Integer id){
        Student student = studentService.findStudentById(id);
        return studentMapper.mapToDto(student);
    }
}
