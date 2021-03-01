package com.example.school.mapper;

import com.example.school.dto.SessionDto;
import com.example.school.model.Session;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionMapper {

    public SessionDto mapToDto(Session session){
        return new SessionDto(session.getIdSession(),session.getLanguageProgramming(),session.getDuration(),session.getPricePerHour(),session.isPaid(),session.getLocalDate(),session.isRecurrent());
    }

    public List<SessionDto> mapToDtoList(List<Session> session){
        List<SessionDto> sessionDtos = new ArrayList<>();
        for (int i = 0; i < session.size(); i++) {
            sessionDtos.add(mapToDto(session.get(i)));
        }
        return sessionDtos;
    }
}
