package com.example.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsStatistics {

    private Integer debts;
    private Integer payed;
    private Integer totalMonthIncome;
}
