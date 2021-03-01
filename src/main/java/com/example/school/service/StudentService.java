package com.example.school.service;

import com.example.school.dto.SessionDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.SessionMapper;
import com.example.school.model.Session;
import com.example.school.model.Student;
import com.example.school.repo.SessionRepository;
import com.example.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
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

        newStudent.setAge(student.getAge());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setEmail(student.getEmail());
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setGender(student.getGender());
        return studentRepository.save(newStudent);
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }


    public List<SessionDto> paidSessions(Integer studentId) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> paidSessionMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessionMonth);
    }

    public List<SessionDto> notPaidSessionsMonth(Integer studentId, Month month) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> notPaidSessionsUntilNowMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().now().getMonth().equals(month))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(notPaidSessionsUntilNowMonth);
    }

    public List<SessionDto> paidSessionsMonth(Integer studentId,Month month) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> paidSessionMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessionMonth);
    }

    public List<SessionDto> notPaidSessions(Integer studentId) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> unpaidSessions = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(unpaidSessions);
    }

    public List<SessionDto> toPayTillEndOfMonth(Integer studentId) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> unpaidSessionMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().isAfter(LocalDate.now()) && session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(unpaidSessionMonth);
    }

    public List<SessionDto> totalPay(Integer studentId) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> totalPay = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalPay);
    }

    public List<SessionDto> paidSessionsTotal(Integer studentId) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> paidSessionsTotal = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessionsTotal);
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

    public List<SessionDto> notPaidSessionsFromNowMonth(Integer studentId, Month month) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> notPaidSessionsUntilNowMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().isAfter(LocalDate.now()) && session.getLocalDate().now().getMonth().equals(month))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(notPaidSessionsUntilNowMonth);
    }

    public List<SessionDto> totalPayMonth(Integer studentId, Month month) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> totalPayMonth = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalPayMonth);
    }

    public List<SessionDto> payedTotalMonth(Integer studentId, Month month) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> totalPay = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().now().getMonth().equals(month))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalPay);
    }

    public List<SessionDto> studentsDebtsTotal() {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> debtsTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) || session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(debtsTotal);
    }

    public List<SessionDto> studentsPayedTotal() {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> payedTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) || session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedTotal);
    }

    public List<SessionDto> studentsMonthIncomeTotal() {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> totalMonthIncome = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()) || session.getLocalDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalMonthIncome);
    }

    public List<SessionDto> studentsDebtsTotaMonth(Month month) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> debtsTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(debtsTotalMonth);
    }

    public List<SessionDto> studentsPayedTotalMonth(Month month) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> payedTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedTotalMonth);
    }

    public List<SessionDto> studentsMonthIncomeTotalMonth(Month month) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> monthIncomeTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthIncomeTotalMonth);
    }
}
