package com.example.school.service.Student;

import com.example.school.dto.Session.SessionDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.SessionMapper;
import com.example.school.model.Session.Session;
import com.example.school.model.Student.Student;
import com.example.school.repo.SessionRepository;
import com.example.school.repo.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    ModelMapper mapper = new ModelMapper();
    //List<Session> sessionList = sessionRepository.findAll(); /todo Why not ?

    public Student addStudent(Student student){
        sessionRepository.saveAll(student.getSessionList());
        for (int i = 0; i < student.getSessionList().size(); i++) {
            student.getSessionList().get(i).setStudent(student);
        }
        return studentRepository.save(student);
    }

    public Student findStudentById(Integer id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        }
        throw new ResourceNotFoundException("Student with ID " + id +" was not found");
    }

    public Student removeStudent(Integer id) {
        Student student=findStudentById(id);
        studentRepository.delete(student);
        return student;
    }

    public Student updateStudent(Student student){
        Student newStudent =findStudentById(student.getId());
        updateStudentInfo(student, newStudent);
        return studentRepository.save(newStudent);
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    public List<SessionDto> getPaidSessions(Integer studentId){
        Student student=findStudentById(studentId);
        List<Session> sessionList = student.getSessionList();
        List<Session> paidSessions = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(paidSessions,SessionDto[].class));
    }

    public List<SessionDto> getNotPaidSessionsMonthYear(Integer studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> notPaidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(notPaidSessionsMonthYear,SessionDto[].class));
    }

    public List<SessionDto> getPaidSessionsMonthYear(Integer studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> paidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(paidSessionsMonthYear,SessionDto[].class));
    }
    //todo ?????????????
    public List<SessionDto> deptToday(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        Function<Session, SessionDto> mapToDto = sessionMapper::mapToDto; //interfata functionala care primeste un parametru si il transpofrma in altceva
        return   sessionList.stream()
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .map(mapToDto) // .map(session -> sessionMapper.mapToDto(session))
                .collect(Collectors.toList());
    }

    public List<SessionDto> deptMonth(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> deptMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().isAfter(LocalDate.now()))
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(deptMonth,SessionDto[].class));
    }

    public List<SessionDto> monthPay(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> monthPay = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(monthPay,SessionDto[].class));
    }

    public List<SessionDto> remainingPay(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> remainingPay = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(remainingPay,SessionDto[].class));
    }

    public List<SessionDto> payed(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> payed = sessionList.stream()
                .filter(session -> session.getLocalDate().now().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(payed,SessionDto[].class));
    }

    public List<SessionDto> remainingPayS(Integer studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> remainingPayS = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(remainingPayS,SessionDto[].class));
    }

    public List<SessionDto> monthPayS(Integer studentId, Month month,Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> monthPayS = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(monthPayS,SessionDto[].class));
    }

    public List<SessionDto> payedS(List<Session> sessionList,Integer studentId, Month month, Year year) {
        List<Session> payedS = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(payedS,SessionDto[].class));
    }

    public List<SessionDto> studentsDebtsTotal(List<Session> sessionList) {
        List<Session> debtsTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(debtsTotal,SessionDto[].class));
    }

    public List<SessionDto> studentsPayedTotal(List<Session> sessionList) {
        List<Session> payedTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(payedTotal,SessionDto[].class));
    }

    public List<SessionDto> studentsMonthIncomeTotal(List<Session> sessionList) {
        List<Session> totalMonthIncome = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(totalMonthIncome,SessionDto[].class));
    }

    public List<SessionDto> studentsDebtsTotaMonth(List<Session> sessionList,Month month,Year year) {
        List<Session> debtsTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(debtsTotalMonth,SessionDto[].class));
    }

    public List<SessionDto> studentsPayedTotalMonth(List<Session> sessionList,Month month,Year year) {
        List<Session> payedTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(payedTotalMonth,SessionDto[].class));
    }

    public List<SessionDto> studentsMonthIncomeTotalMonth(List<Session> sessionList,Month month,Year year) {
        List<Session> monthIncomeTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return Arrays.asList(mapper.map(monthIncomeTotalMonth,SessionDto[].class));
    }

    private void updateStudentInfo(Student student, Student newStudent) {
        newStudent.setAge(student.getAge());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.getStudentContactDetails().setEmail(student.getStudentContactDetails().getEmail());
        newStudent.getStudentContactDetails().setFirstName(student.getStudentContactDetails().getFirstName());
        newStudent.getStudentContactDetails().setLastName(student.getStudentContactDetails().getLastName());
        newStudent.setGender(student.getGender());
    }


}
