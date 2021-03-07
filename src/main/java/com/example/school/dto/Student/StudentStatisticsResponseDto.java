package com.example.school.dto.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatisticsResponseDto {
    // put in DTO
    int deptToday;
    int deptMonth;
    int monthPay;
    int remainingPay;
    int payed;

}
