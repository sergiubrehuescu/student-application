package com.example.school.controller;

import com.example.school.dto.SessionDto;
import com.example.school.dto.StudentDto;
import com.example.school.mapper.SessionMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.Session;
import com.example.school.model.Student;
import com.example.school.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
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

    @GetMapping("student/notPaidSessions/{studentId}")
    public List<SessionDto> notPaidSessions(@PathVariable Integer studentId){
        return  studentService.notPaidSessions(studentId);
    }

    @GetMapping("student/notPaidSessions/{studentId}/{month}")
    public List<SessionDto> notPaidSessionsMonth(@PathVariable Integer studentId,@PathVariable Month month){
        return  studentService.notPaidSessionsMonth(studentId,month);
    }

    @GetMapping("student/paidSessions/{studentId}")
    public List<SessionDto> paidSessions(@PathVariable Integer studentId){
        return  studentService.paidSessions(studentId);
    }

    @GetMapping("student/paidSessions/{studentId}/{month}")
    public List<SessionDto> paidSessionsMonth(@PathVariable Integer studentId,@PathVariable Month month){
        return  studentService.paidSessionsMonth(studentId,month);
    }

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
        //pargur fiecare session din entity...map to sessiodnto...toate sessiondto intr-o lista goala de tip sessionDTO....setare acea lista goala pe noua lista din student DTO
        for (int i = 0; i < student.getSessionList().size(); i++) {
            Session session = student.getSessionList().get(i);
            SessionDto sessionDto= sessionMapper.mapToDto(session);
            studentDto.getSessions().add(sessionDto);
        }
        return studentDto;
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
        StudentDto studentDto = studentMapper.mapToDto(student);
        for (int i = 0; i < student.getSessionList().size(); i++) {
            Session session = student.getSessionList().get(i);
            SessionDto sessionDto = sessionMapper.mapToDto(session);
            studentDto.getSessions().add(sessionDto);
        }
        return studentDto;
    }

}
