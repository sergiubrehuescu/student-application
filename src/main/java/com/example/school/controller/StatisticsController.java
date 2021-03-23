package com.example.school.controller;

import com.example.school.dto.Session.SessionDto;
import com.example.school.dto.Student.PastStudentStatisticsResponseDto;
import com.example.school.dto.Student.StudentStatisticsResponseDto;
import com.example.school.dto.Student.StudentsStatisticsResponseDto;
import com.example.school.service.StatisticsService;
import com.example.school.service.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("getNotPaidSession/{studentId}")
    public List<SessionDto> getNotPaidSession(@PathVariable Integer studentId){
        return  studentService.deptToday(studentId);
    }

    @GetMapping("getNotPaidSessions/{studentId}/{month}/{year}")
    public List<SessionDto> getNotPaidSessionsMonthYear(@PathVariable Integer studentId, @PathVariable Month month, @PathVariable Year year){
        return  studentService.getNotPaidSessionsMonthYear(studentId,month,year);
    }

    @GetMapping("getPaidSessions/{studentId}")
    public List<SessionDto> getPaidSessions(@PathVariable Integer studentId){
        return  studentService.getPaidSessions(studentId);
    }
    //daca il trimiti sa ia in considerare luna
    //daca nu il trimiti sa ia dupa luna curenta

    @GetMapping("getPaidSessions/{studentId}/{month}/{year}")
    public List<SessionDto> getPaidSessionsMonthYear(@PathVariable Integer studentId,@PathVariable Month month,@PathVariable Year year){
        return  studentService.getPaidSessionsMonthYear(studentId,month,year);
    }

    @GetMapping("student/{studentId}")
    public StudentStatisticsResponseDto studentStatistics(@PathVariable Integer studentId){
        return statisticsService.studentStatistics(studentId);
    }

    @GetMapping("student/{studentId}/{month}/{year}")
    public PastStudentStatisticsResponseDto studentStatisticsMonth(@PathVariable Integer studentId, @PathVariable Month month, @PathVariable Year year){
        return statisticsService.studentStatisticsMonthYear(studentId,month,year);
    }

    @GetMapping("month")
    public StudentsStatisticsResponseDto studentsStatistics(){
        return statisticsService.studentsStatistics();
    }

    @GetMapping("month/{monthName}/{year}")
    public StudentsStatisticsResponseDto studentsStatistics(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.studentsStatisticsMonthYear(monthName,year);
    }

/*    @GetMapping("hours/{monthName}/{year}")
    public StatisticsOverAllDto statisticsOverAll(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.statisticsOverAll(monthName,year);
    }*/
}
