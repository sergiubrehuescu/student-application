package com.example.school.dto.Session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {

    private Integer idSession;
    private String languageProgramming;
    private Integer duration;
    private Integer pricePerHour;
    private boolean paid;
    private LocalDate localDate;
    private boolean recurrent;
}
