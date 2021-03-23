package com.example.school.service.Student;

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
        //List<Session> sessionList = sessionRepository.findAll(); //todo do i need sessionList here ?
        updateStudentInfo(student, newStudent);
        return studentRepository.save(newStudent);
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    public List<SessionDto> getPaidSessions(Integer studentId){
        Student student=findStudentById(studentId);
        List<Session> sessionList = student.getSessionList(); //todo can it be refactored ?
        List<Session> paidSessions = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessions);
    }

    public List<SessionDto> getNotPaidSessionsMonthYear(Integer studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> notPaidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(notPaidSessionsMonthYear);
    }

    public List<SessionDto> getPaidSessionsMonthYear(Integer studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> paidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(paidSessionsMonthYear);
    }

    public List<SessionDto> deptToday(Integer studentId) {
        //todo REFACTORING !!!!!!!
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
        return sessionMapper.mapToDtoList(deptMonth);
    }

    public List<SessionDto> monthPay(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> monthPay = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthPay);
    }

    public List<SessionDto> remainingPay(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> remainingPay = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(remainingPay);
    }

    public List<SessionDto> payed(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> payed = sessionList.stream()
                .filter(session -> session.getLocalDate().now().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payed);
    }

    public List<SessionDto> remainingPayS(Integer studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> remainingPayS = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(remainingPayS);
    }

    public List<SessionDto> monthPayS(Integer studentId, Month month,Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> monthPayS = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthPayS);
    }

    public List<SessionDto> payedS(Integer studentId, Month month, Year year) {
        List<Session> sessionList = sessionRepository.findAll();
        List<Session> payedS = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedS);
    }

    public List<SessionDto> studentsDebtsTotal() {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> debtsTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(debtsTotal);
    }

    public List<SessionDto> studentsPayedTotal() {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> payedTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedTotal);
    }

    public List<SessionDto> studentsMonthIncomeTotal() {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> totalMonthIncome = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(totalMonthIncome);
    }

    public List<SessionDto> studentsDebtsTotaMonth(Month month,Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> debtsTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(debtsTotalMonth);
    }

    public List<SessionDto> studentsPayedTotalMonth(Month month,Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> payedTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(payedTotalMonth);
    }

    public List<SessionDto> studentsMonthIncomeTotalMonth(Month month,Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        List<Session> monthIncomeTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return sessionMapper.mapToDtoList(monthIncomeTotalMonth);
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
