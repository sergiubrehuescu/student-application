package com.example.school.dto.StatisticsOverAll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeekHours {

    String interval; //todo 2 Local dates
    float hours=0;
}
