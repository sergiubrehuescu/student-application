package com.example.school.mapper;

import com.example.school.dto.SessionDto;
import com.example.school.model.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    public SessionDto mapToDto(Session session){
        return new SessionDto(session.getIdSession(),session.getLanguageProgramming(),session.getDuration(),session.getPricePerHour(),session.getPaid(),session.getLocalDate());
    }
}
