package com.example.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatistics {

    private Integer toPayToNow;
    private Integer toPayTillEndOfMonth;
    private Integer totalPay;
    private Integer payedTotal;

}
