package com.example.school.controller;

import com.example.school.dto.SessionDto;
import com.example.school.mapper.SessionMapper;
import com.example.school.model.Session;
import com.example.school.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SessionController {

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

    private Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("addSessionToStudent/{id}")
    private SessionDto addSessionToStudent(@PathVariable Integer id , @RequestBody Session session)
    {
        sessionService.addSession(session,id);
        return sessionMapper.mapToDto(session);
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
