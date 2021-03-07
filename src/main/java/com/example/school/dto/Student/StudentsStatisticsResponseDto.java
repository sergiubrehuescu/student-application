package com.example.school.dto.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsStatisticsDto {

    private Integer debts;
    private Integer payed;
    private Integer totalMonthIncome;
}
