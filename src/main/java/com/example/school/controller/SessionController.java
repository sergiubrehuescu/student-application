package com.example.school.controller;

import com.example.school.dto.Session.SessionDto;
import com.example.school.model.Session.Session;
import com.example.school.service.SessionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    private Logger logger = LoggerFactory.getLogger(SessionController.class);

    ModelMapper mapper = new ModelMapper();

    @PostMapping("addToStudent/{id}")
    private SessionDto addSessionToStudent(@PathVariable Integer id , @RequestBody Session session)
    {
        sessionService.addSession(session,id);
        return mapper.map(session,SessionDto.class);
    }

    @DeleteMapping("remove/{idSession}")
    public SessionDto removeSession(@PathVariable Integer idSession){
        Session session = sessionService.finSessionById(idSession);
        sessionService.removeSession(idSession);
        return mapper.map(session,SessionDto.class);
    }

    @PutMapping("update")
    public SessionDto updateSession(@RequestBody @Valid Session session){
        sessionService.updateSession(session);
        return mapper.map(session,SessionDto.class);
    }

    @PutMapping("pay/{id}")
    public SessionDto paySession(@PathVariable @Valid Integer id){
        return sessionService.paySession(id);
    }

    @PutMapping("unpay/{id}")
    public SessionDto unpaySession(@PathVariable @Valid Integer id){
        return sessionService.unpaySession(id);
    }

    @PutMapping("addRecurent/{studentId}/{localDate}")
    public List<SessionDto> addSessionsRecurent(@PathVariable Integer studentId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @RequestBody Session session){
        return sessionService.addSessionsRecurent(studentId,localDate,session);
    }

}
