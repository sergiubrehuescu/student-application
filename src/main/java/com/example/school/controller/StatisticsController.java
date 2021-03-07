package com.example.school.controller;

import com.example.school.dto.Student.StatisticsOverAll.StatisticsOverAllDto;
import com.example.school.dto.Student.StudentStatisticsResponseDto;
import com.example.school.dto.Student.PastStudentStatisticsResponseDto;
import com.example.school.dto.Student.StudentsStatisticsResponseDto;
import com.example.school.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/student/{studentId}")
    public StudentStatisticsResponseDto studentStatistics(@PathVariable Integer studentId){
        return statisticsService.studentStatistics(studentId);
    }

    @GetMapping("/student/{studentId}/{month}/{year}")
    public PastStudentStatisticsResponseDto studentStatisticsMonth(@PathVariable Integer studentId, @PathVariable Month month, @PathVariable Year year){
        return statisticsService.studentStatisticsMonthYear(studentId,month,year);
    }

    @GetMapping("/month")
    public StudentsStatisticsResponseDto studentsStatistics(){
        return statisticsService.studentsStatistics();
    }

    @GetMapping("/month/{monthName}/{year}")
    public StudentsStatisticsResponseDto studentsStatistics(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.studentsStatisticsMonthYear(monthName,year);
    }

    @GetMapping("/hours/{monthName}/{year}")
    public StatisticsOverAllDto statisticsOverAll(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.statisticsOverAll(monthName,year);
    }

}
