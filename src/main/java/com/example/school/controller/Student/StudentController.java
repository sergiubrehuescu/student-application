package com.example.school.controller.Student;

import com.example.school.dto.Student.StudentDto;
import com.example.school.mapper.LiveStreamMapper;
import com.example.school.mapper.SessionMapper;
import com.example.school.mapper.StudentContactDetailsMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.Student.Student;
import com.example.school.service.Student.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private LiveStreamMapper liveStreamMapper;

    @Autowired
    private StudentContactDetailsMapper studentContactDetailsMapper;

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("add")
    public StudentDto addStudent(@RequestBody @Valid Student student){
        logger.info("Trecut prin controller");
        Student s = studentService.addStudent(student);
        StudentDto studentDto = studentMapper.mapToDto(s);

        studentDto.setStudentContactDetailsDto(studentContactDetailsMapper.mapToDtoWithoutStudent(student.getStudentContactDetails()));
        studentDto.setSessions(sessionMapper.mapToDtoList(student.getSessionList()));
        studentDto.setLiveStreams(liveStreamMapper.mapToLiveStreamListDto(student.getLiveStreamList()));

        return studentDto;
    }

    @DeleteMapping("remove/{id}")
    public StudentDto removeStudent(@PathVariable Integer id){
        return studentMapper.mapToDto(studentService.removeStudent(id));
    }

    @PutMapping("update")
    public StudentDto updateStudent(@RequestBody @Valid Student student){
        studentService.updateStudent(student);
        return studentMapper.mapToDto(student);
    }

    @GetMapping("/{id}")
    public StudentDto findStudentById(@PathVariable Integer id){
        Student student = studentService.findStudentById(id);
        StudentDto studentDto = studentMapper.mapToDto(student);

        studentDto.setStudentContactDetailsDto(studentContactDetailsMapper.mapToDtoWithoutStudent(student.getStudentContactDetails()));
        studentDto.setSessions(sessionMapper.mapToDtoList(student.getSessionList()));
        studentDto.setLiveStreams(liveStreamMapper.mapToLiveStreamListDto(student.getLiveStreamList()));
/*         student.getSessionList().stream()
                .map(s-> sessionMapper.mapToDto(s))
                        .collect(Collectors.toList());
                //.forEach(x-> studentDto.getSessions().add(x));*/
        return studentDto;
    }

    @GetMapping("listStudentsAll")
    public List<StudentDto> listStudentsAll() {
        List<Student> students = studentService.getStudentList();
        return students.stream()
                .map(student -> {
                    StudentDto studentDto = studentMapper.mapToDto(student);
                    studentDto.setStudentContactDetailsDto(studentContactDetailsMapper.mapToDtoWithoutStudent(student.getStudentContactDetails()));
                    studentDto.setSessions(sessionMapper.mapToDtoList(student.getSessionList()));
                    studentDto.setLiveStreams(liveStreamMapper.mapToLiveStreamListDto(student.getLiveStreamList()));
                    return studentDto;
                })
                .collect(Collectors.toList());
    }

}
