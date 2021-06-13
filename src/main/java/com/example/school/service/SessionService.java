package com.example.school.service;

import com.example.school.dto.Session.SessionDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Session.Session;
import com.example.school.model.Student.Student;
import com.example.school.repo.SessionRepository;
import com.example.school.repo.StudentRepository;
import com.example.school.service.Student.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    ModelMapper mapper = new ModelMapper();

    public Session addSession(Session session, Integer id){
        Student student = studentService.findStudentById(id);
        student.getSessionList().add(session);
        session.setStudent(student);
        sessionRepository.save(session);
        return session;
    }

    public Session updateSession(Session session) {
        Session newSession = finSessionById(session.getIdSession());
        updateSessionInfo(session, newSession);
        return sessionRepository.save(newSession);
    }

    public Session finSessionById(Integer idSession) {
        Optional<Session> session = sessionRepository.findById(idSession);
        if(session.isPresent()){
            return session.get();
        }
        throw new ResourceNotFoundException("Session with ID " + idSession +" was not found");
    }

    public Session removeSession(Integer idSession) {
        Session session=finSessionById(idSession);
        sessionRepository.delete(session);
        return session;
    }

    public SessionDto paySession(Integer idSession) {
        Session session = finSessionById(idSession);
        session.setPaid(true);
        sessionRepository.save(session);
        return mapper.map(session,SessionDto.class);
    }

    public SessionDto unpaySession(Integer idSession) {
        Session session = finSessionById(idSession);
        session.setPaid(false);
        sessionRepository.save(session);
        return mapper.map(session,SessionDto.class);
    }

    public List<SessionDto> addSessionsRecurent(Integer studentId, LocalDate localDate, Session session) {
        Student student = studentService.findStudentById(studentId);

        for (int i = 0; i < DAYS.between(localDate, localDate.plusMonths(3)); i+=7) {
            System.out.println(localDate.plusDays(i));
            Session newSession = new Session(session.getIdSession(),session.getLanguageProgramming(),session.getDuration(),session.getPricePerHour(),session.isPaid(),localDate.plusDays(i),session.isRecurrent(),student);
            student.getSessionList().add(newSession);
        }
        studentRepository.save(student);
        return Arrays.asList(mapper.map(student.getSessionList(),SessionDto[].class));
        }

    private void updateSessionInfo(Session session, Session newSession) {
        newSession.setIdSession(session.getIdSession());
        newSession.setStudent(session.getStudent());
        newSession.setDuration(session.getDuration());
        newSession.setLanguageProgramming(session.getLanguageProgramming());
        newSession.setPaid(session.isPaid());
        newSession.setLocalDate(session.getLocalDate());
        newSession.setPricePerHour(session.getPricePerHour());
    }



}
