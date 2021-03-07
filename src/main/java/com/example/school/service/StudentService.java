package com.example.school.service;

import com.example.school.dto.Session.SessionDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.SessionMapper;
import com.example.school.model.Session.Session;
import com.example.school.model.Student.Student;
import com.example.school.repo.SessionRepository;
import com.example.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
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

    public Student addStudent(Student student){
        sessionRepository.saveAll(student.getSessionList());
        for (int i = 0; i < student.getSessionList().size(); i++) {
            student.getSessionList().get(i).setStudent(student);
        }
        studentRepository.save(student);
        return student;
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
        List<Session> sessionList = sessionRepository.findAll();
        newStudent.setAge(student.getAge());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.getStudentContactDetails().setEmail(student.getStudentContactDetails().getEmail());
        newStudent.getStudentContactDetails().setFirstName(student.getStudentContactDetails().getFirstName());
        newStudent.getStudentContactDetails().setLastName(student.getStudentContactDetails().getLastName());
        newStudent.setGender(student.getGender());
        return studentRepository.save(newStudent);
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }


    public List<SessionDto> paidSessions(Integer studentId) { //todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> paidSessions = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) && session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessions);
    }

    public List<SessionDto> notPaidSessionsMonthYear(Integer studentId, Month month, Year year) { //todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> notPaidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().getMonth().equals(month) && (session.getLocalDate().getYear()==year.getValue()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(notPaidSessionsMonthYear);
    }

    public List<SessionDto> paidSessionsMonthYear(Integer studentId, Month month, Year year) { //todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> paidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().getMonth().equals(month) && (session.getLocalDate().getYear()==year.getValue()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessionsMonthYear);
    }

    public List<SessionDto> deptToday(Integer studentId) { //todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        Function<Session, SessionDto> mapToDto = sessionMapper::mapToDto; //interfata functionala care primeste un parametru si il transpofrma in altceva
        return   sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().isBefore(LocalDate.now()) && !session.isPaid())
                .map(mapToDto) // .map(session -> sessionMapper.mapToDto(session))
                .collect(Collectors.toList());
    }

    public List<SessionDto> deptMonth(Integer studentId) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> deptMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().isAfter(LocalDate.now()) && session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) )
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(deptMonth);
    }

    public List<SessionDto> monthPay(Integer studentId) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> monthPay = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthPay);
    }

    public List<SessionDto> remainingPay(Integer studentId) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> paidSessionsTotal = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().now().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessionsTotal);
    }

    public List<SessionDto> payed(Integer studentId) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> payed = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().now().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payed);
    }

    public List<SessionDto> notPaidSessionsUntilNowMonth(Integer studentId, Month month) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> notPaidSessionsUntilNowMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()) && session.getLocalDate().now().getMonth().equals(month))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(notPaidSessionsUntilNowMonth);
    }

    public List<SessionDto> remainingPayS(Integer studentId, Month month, Year year) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> notPaidSessionsUntilNowMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().isAfter(LocalDate.now()) && session.getLocalDate().now().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(notPaidSessionsUntilNowMonth);
    }

    public List<SessionDto> monthPayS(Integer studentId, Month month,Year year) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> monthPayS = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().getMonth().equals(month) && session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthPayS);
    }

    public List<SessionDto> payedS(Integer studentId, Month month, Year year) {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> totalPay = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId) && session.getLocalDate().now().getMonth().equals(month) && session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalPay);
    }

    public List<SessionDto> studentsDebtsTotal() {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> debtsTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) || session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(debtsTotal);
    }

    public List<SessionDto> studentsPayedTotal() {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> payedTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) || session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedTotal);
    }

    public List<SessionDto> studentsMonthIncomeTotal() {//todo OK OK OK OK OK OK//todo OK OK OK OK OK OK
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> totalMonthIncome = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) || session.getLocalDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalMonthIncome);
    }

    public List<SessionDto> studentsDebtsTotaMonth(Month month,Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> debtsTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month) && session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(debtsTotalMonth);
    }

    public List<SessionDto> studentsPayedTotalMonth(Month month,Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> payedTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month)&& session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedTotalMonth);
    }

    public List<SessionDto> studentsMonthIncomeTotalMonth(Month month,Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> monthIncomeTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month)&& session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthIncomeTotalMonth);
    }


}
