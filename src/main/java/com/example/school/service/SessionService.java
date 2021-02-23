package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Session;
import com.example.school.model.Student;
import com.example.school.repo.SessionRepository;
import com.example.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    public Session addSession(Session session, Integer id){
        Student student = studentService.findStudentById(id);
        student.getSessionList().add(session);
        studentRepository.save(student);
        session.setStudent(student);// OK
        sessionRepository.save(session);
        return session;
    }

    public Session updateSession(Session session) {
        Session newSession = finSessionById(session.getIdSession());
        newSession.setIdSession(session.getIdSession());
        newSession.setStudent(session.getStudent());
        newSession.setDuration(session.getDuration());
        newSession.setLanguageProgramming(session.getLanguageProgramming());
        newSession.setPaid(session.getPaid());
        newSession.setLocalDate(session.getLocalDate());
        newSession.setPricePerHour(session.getPricePerHour());
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





}
