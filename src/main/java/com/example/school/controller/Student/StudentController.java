package com.example.school.controller.Student;

import com.example.school.dto.LiveStream.LiveStreamDto;
import com.example.school.dto.Session.SessionDto;
import com.example.school.dto.Student.StudentContactDetailsDto;
import com.example.school.dto.Student.StudentDto;
import com.example.school.model.Student.Student;
import com.example.school.service.Student.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
public class StudentController {
    //todo design pattern singleton

    @Autowired
    private StudentService studentService;

    ModelMapper mapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("add")
    public StudentDto addStudent(@RequestBody @Valid Student student){
        logger.info("Trecut prin controller");
        Student s = studentService.addStudent(student);

        StudentDto studentDto = mapper.map(student,StudentDto.class);
        studentDto.setStudentContactDetailsDto(mapper.map(student.getStudentContactDetails(),StudentContactDetailsDto.class));
        studentDto.setSessions(Arrays.asList(mapper.map(student.getSessionList(), SessionDto[].class)));
        studentDto.setLiveStreams(Arrays.asList(mapper.map(student.getLiveStreamList(), LiveStreamDto[].class)));

        return studentDto;
    }

    @DeleteMapping("remove/{id}")
    public StudentDto removeStudent(@PathVariable Integer id){
        return mapper.map(studentService.removeStudent(id),StudentDto.class);
    }

    @PutMapping("update")
    public StudentDto updateStudent(@RequestBody @Valid Student student){
        studentService.updateStudent(student);
        return mapper.map(student,StudentDto.class);
    }

    @GetMapping("/{id}")
    public StudentDto findStudentById(@PathVariable Integer id){
        Student student = studentService.findStudentById(id);
        StudentDto studentDto = mapper.map(student,StudentDto.class);

        studentDto.setStudentContactDetailsDto(mapper.map(student.getStudentContactDetails(), StudentContactDetailsDto.class));
        studentDto.setSessions(Arrays.asList(mapper.map(student.getSessionList(), SessionDto[].class)));
        studentDto.setLiveStreams(Arrays.asList(mapper.map(student.getLiveStreamList(), LiveStreamDto[].class)));
        return studentDto;
    }

    @GetMapping("listStudentsAll")
    public List<StudentDto> listStudentsAll() {
        List<Student> students = studentService.getStudentList();
        return students.stream()
                .map(student -> {
                    StudentDto studentDto = mapper.map(student,StudentDto.class);
                    studentDto.setStudentContactDetailsDto(mapper.map(student.getStudentContactDetails(), StudentContactDetailsDto.class));
                    studentDto.setSessions(Arrays.asList(mapper.map(student.getSessionList(), SessionDto[].class)));
                    studentDto.setLiveStreams(Arrays.asList(mapper.map(student.getLiveStreamList(), LiveStreamDto[].class)));
                    return studentDto;
                })
                .collect(Collectors.toList());
    }

}
