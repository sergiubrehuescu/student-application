package com.example.school.dto.Student;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PastStudentStatisticsResponseDto {

    int monthPay;
    int remainingPay;
    int payed;
}
