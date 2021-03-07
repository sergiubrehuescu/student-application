package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Session.Session;
import com.example.school.model.Student.Student;
import com.example.school.repo.SessionRepository;
import com.example.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Session addSession(Session session, Integer id){
        Student student = studentService.findStudentById(id);
        student.getSessionList().add(session);
        //studentRepository.save(student); // will create 2 sessions with a mull ptr for student_id
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
        newSession.setPaid(session.isPaid());
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

    public String paySession(Integer idSession) { //OK
        Session session = finSessionById(idSession);
        session.setPaid(true);
        sessionRepository.save(session);
        return "Sesiunea cu id-ul " + idSession + " a fost platita !";
    }

    public String addSessionsRecurent(Integer studentId,LocalDate localDate,Session session) {//OK
        int counter=0;
        Student student = studentService.findStudentById(studentId);
        LocalDate newDate = localDate;
        for (int i = 0; i < DAYS.between(localDate, localDate.plusMonths(3)); i+=7,counter++) {
            Session newSession = new Session(session.getIdSession(),session.getLanguageProgramming(),session.getDuration(),session.getPricePerHour(),session.isPaid(),newDate.plusDays(7),session.isRecurrent(),student);
            addSession(newSession,studentId);
        }
        return ("Pentru " + student.getStudentContactDetails().getFirstName()+ " " + student.getStudentContactDetails().getLastName() + " au fost adaugate " +counter + " sedinte noi din data " + localDate + " pana la data " + localDate.plusMonths(3));
    }

    public String statusHours(LocalDate localDateOne,LocalDate localDateTwo) {
        List<Session> sessionList = sessionRepository.findAll();
        double totalHours=0;
        double totalHoursNext7Days=0;
        for (Session session : sessionList) {
            if (session.getLocalDate().isAfter(localDateOne) && session.getLocalDate().isBefore(localDateTwo)) {
                totalHours += session.getDuration().floatValue()/60;
            }
        }
        for (Session session : sessionList) {
            if (session.getLocalDate().isAfter(localDateOne) && session.getLocalDate().isBefore(localDateOne.plusDays(7))) {
                totalHoursNext7Days  = session.getDuration().floatValue() / 60;
            }
        }
        System.out.println("TOTAL Hours intre data " + localDateOne + "si data " + localDateOne.plusDays(7) + "este de " + totalHoursNext7Days + " ore");

        return ("TOTAL Hours intre data " + localDateOne + "si data " + localDateTwo + "este de " + totalHours + " ore");

    }


}
