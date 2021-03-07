package com.example.school.controller;

import com.example.school.dto.DrivingLicense.DrivingLicenseDto;
import com.example.school.dto.Session.SessionDto;
import com.example.school.dto.Student.StudentDto;
import com.example.school.mapper.DrivingLicenseMapper;
import com.example.school.mapper.SessionMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.DrivingLicense.DrivingLicense;
import com.example.school.model.Session.Session;
import com.example.school.model.Student.Student;
import com.example.school.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

/*LiveStream

        data
        limbaj_de_programare
        topic
        Lista participanti*/
// One to Many // One to one //Many to Many //Validare @Anaotation //
// Map Struct
// Mapper
// JPA Hibernate
//

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private DrivingLicenseMapper drivingLicenseMapper;

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("student/notPaidSessions/{studentId}")//todo OK OK OK OK OK OK
    public List<SessionDto> notPaidSessions(@PathVariable Integer studentId){
        return  studentService.deptToday(studentId);
    }

    @GetMapping("student/notPaidSessions/{studentId}/{month}/{year}")//todo OK OK OK OK OK OK
    public List<SessionDto> notPaidSessionsMonthYear(@PathVariable Integer studentId,@PathVariable Month month,@PathVariable Year year){
        return  studentService.notPaidSessionsMonthYear(studentId,month,year);
    }

    @GetMapping("student/paidSessions/{studentId}")//todo OK OK OK OK OK OK
    public List<SessionDto> paidSessions(@PathVariable Integer studentId){
        return  studentService.paidSessions(studentId);
    }
    //daca il trimiti sa ia in considerare luna
    //daca nu il trimiti sa ia dupa luna curenta

    @GetMapping("student/paidSessions/{studentId}/{month}/{year}")//todo OK OK OK OK OK OK
    public List<SessionDto> paidSessionsMonthYear(@PathVariable Integer studentId,@PathVariable Month month,@PathVariable Year year){
        return  studentService.paidSessionsMonthYear(studentId,month,year);
    }

    @GetMapping("/listStudents")
    public List<StudentDto> listStudents() {
        List<Student> students = studentService.getStudentList();
        return students.stream()
                .map(student -> studentMapper.mapToDto(student))
                .collect(Collectors.toList());
    }

    @PostMapping("/addStudent")//todo OK OK OK OK OK OK
    public StudentDto addStudent(@RequestBody @Valid Student student){
        logger.info("Trecut prin controller");
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
    }

    @DeleteMapping("/removeStudent/{id}")
    public StudentDto removeStudent(@PathVariable Integer id){
        return studentMapper.mapToDto(studentService.removeStudent(id));
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
        /*for (int i = 0; i < student.getSessionList().size(); i++) {
            Session session = student.getSessionList().get(i);
            SessionDto sessionDto = sessionMapper.mapToDto(session);
            studentDto.getSessions().add(sessionDto);
        }*/
        student.getSessionList().stream()
                .map(s-> sessionMapper.mapToDto(s))
        //        .collect(Collectors.toList());
               .forEach(x-> studentDto.getSessions().add(x));

        return studentDto;
    }

}
