package com.example.school.controller;

import com.example.school.model.StudentStatistics;
import com.example.school.model.StudentsStatistics;
import com.example.school.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/student/{studentId}")
    public StudentStatistics studentStatistics(@PathVariable Integer studentId){
        return statisticsService.studentStatistics(studentId);
    }

    @GetMapping("/student/{studentId}/{month}")
    public StudentStatistics studentStatisticsMonth(@PathVariable Integer studentId,@PathVariable Month month){
        return statisticsService.studentStatisticsMonth(studentId,month);
    }

    @GetMapping("/month")
    public StudentsStatistics studentsStatistics(){
        return statisticsService.studentsStatistics();
    }

    @GetMapping("/month/{monthName}")
    public StudentsStatistics studentsStatistics(@PathVariable Month monthName){
        return statisticsService.studentsStatisticsMonth(monthName);
    }

}
