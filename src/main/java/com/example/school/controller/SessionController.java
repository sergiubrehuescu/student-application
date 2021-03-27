package com.example.school.controller;

import com.example.school.dto.Session.SessionDto;
import com.example.school.mapper.SessionMapper;
import com.example.school.model.Session.Session;
import com.example.school.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

    private Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("addToStudent/{id}")
    private SessionDto addSessionToStudent(@PathVariable Integer id , @RequestBody Session session)
    {
        sessionService.addSession(session,id);
        return sessionMapper.mapToDto(session);
    }

    @DeleteMapping("remove/{idSession}")
    public SessionDto removeSession(@PathVariable Integer idSession){
        Session session = sessionService.finSessionById(idSession);
        sessionService.removeSession(idSession);
        return sessionMapper.mapToDto(session);
    }

    @PutMapping("update")
    public SessionDto updateSession(@RequestBody @Valid Session session){
        sessionService.updateSession(session);
        return sessionMapper.mapToDto(session);
    }

    @PutMapping("pay/{id}")
    public SessionDto paySession(@PathVariable @Valid Integer id){
        return sessionService.paySession(id);
    }


    @PutMapping("addRecurent/{studentId}/{localDate}")
    public String addSessionsRecurent(@PathVariable Integer studentId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @RequestBody Session session){
        return sessionService.addSessionsRecurent(studentId,localDate,session);
    }




}
