package com.example.school.dto.Student.StatisticsOverAll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsOverAllDto {

    float monthHours;
    ArrayList<WeekHours> weekHours =new ArrayList<WeekHours>();
}
