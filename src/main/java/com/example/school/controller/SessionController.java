package com.example.school.controller;

import com.example.school.dto.SessionDto;
import com.example.school.mapper.SessionMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.Session;
import com.example.school.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class SessionController {

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private StudentMapper studentMapper;

    private Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("addSessionToStudent/{id}")
    private SessionDto addSessionToStudent(@PathVariable Integer id , @RequestBody Session session)
    {
        sessionService.addSession(session,id);
        return sessionMapper.mapToDto(session);
    }

    @PutMapping("/paySession/{id}")
    public void paySession(@PathVariable @Valid Integer id){
        sessionService.paySession(id);
    }

    @PutMapping("/statusHours/{localDateOne}/{localDateTwo}")
    public String statusHours(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDateOne,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDateTwo){
        return sessionService.statusHours(localDateOne,localDateTwo);
    }

    @PutMapping("addSessionsRecurent/{studentId}/{localDate}")
    public String addSessionsRecurent(@PathVariable Integer studentId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @RequestBody Session session){
        return sessionService.addSessionsRecurent(studentId,localDate,session);
    }

    @PutMapping("/updateSession")
    public SessionDto updateSession(@RequestBody @Valid Session session){
        sessionService.updateSession(session);
        return sessionMapper.mapToDto(session);
    }

    @DeleteMapping("/removeSession/{idSession}")
    public SessionDto removeSession(@PathVariable Integer idSession){
        Session session = sessionService.finSessionById(idSession);
        sessionService.removeSession(idSession);
        return sessionMapper.mapToDto(session);
    }

}
