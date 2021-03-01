package com.example.school.service;

import com.example.school.dto.SessionDto;
import com.example.school.model.StudentStatistics;
import com.example.school.model.StudentsStatistics;
import com.example.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    StudentStatistics studentStatistics = new StudentStatistics();
    StudentsStatistics studentsStatistics = new StudentsStatistics();

    public StudentStatistics studentStatistics(Integer studentId) {
        int toPayToNow=0,toPayTillEndOfMonth=0,totalPay=0,payedTotal=0;
        List<SessionDto> sessionDtoListToPayToNow = studentService.notPaidSessions(studentId); //double
        List<SessionDto> sessionDtoListToPayTillEndOfMonth = studentService.toPayTillEndOfMonth(studentId);
        List<SessionDto> sessionDtoListTotalPay = studentService.totalPay(studentId);
        List<SessionDto> sessionDtoListPayedTotal = studentService.paidSessionsTotal(studentId);

        for (SessionDto sessionDto : sessionDtoListToPayToNow) {
            toPayToNow += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListToPayTillEndOfMonth) {
            toPayTillEndOfMonth += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListTotalPay) {
            totalPay += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListPayedTotal) {
            payedTotal += getPayedSession(sessionDto);
        }
        setPayment(toPayToNow, toPayTillEndOfMonth, totalPay, payedTotal, studentStatistics);

        return studentStatistics;
    }

    public StudentStatistics studentStatisticsMonth(Integer studentId, Month month) {
        int toPayToNow=0,toPayTillEndOfMonth=0,totalPay=0,payedTotal=0;
        List<SessionDto> sessionDtoListToPayToNow = studentService.notPaidSessionsUntilNowMonth(studentId,month);
        List<SessionDto> sessionDtoListToPayTillEndOfMonth = studentService.notPaidSessionsFromNowMonth(studentId,month);
        List<SessionDto> sessionDtoListTotalPay = studentService.totalPayMonth(studentId,month);
        List<SessionDto> sessionDtoListPayedTotal = studentService.payedTotalMonth(studentId,month);

        for (SessionDto sessionDto : sessionDtoListToPayToNow) {
            toPayToNow += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListToPayTillEndOfMonth) {
            toPayTillEndOfMonth += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListTotalPay) {
            totalPay += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListPayedTotal) {
            payedTotal += getPayedSession(sessionDto);
        }
        setPayment(toPayToNow, toPayTillEndOfMonth, totalPay, payedTotal, studentStatistics);

        return studentStatistics;
    }

    public StudentsStatistics studentsStatistics() {
        int debts=0,payed=0,totalMonthIncome=0;
        List<SessionDto> debtsTotal = studentService.studentsDebtsTotal();
        List<SessionDto> payedTotal = studentService.studentsPayedTotal();
        List<SessionDto> monthIncomeTotal = studentService.studentsMonthIncomeTotal();

        for (SessionDto sessionDto : debtsTotal) {
            debts += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : payedTotal) {
            payed += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : monthIncomeTotal) {
            totalMonthIncome += getPayedSession(sessionDto);
        }
        setPaymentAllStudents(debts, payed, totalMonthIncome, studentsStatistics);

        return studentsStatistics;
    }

    public StudentsStatistics studentsStatisticsMonth(Month month) {
        int debts=0,payed=0,totalMonthIncome=0;
        List<SessionDto> debtsTotalMonth = studentService.studentsDebtsTotaMonth(month);
        List<SessionDto> payedTotalMonth = studentService.studentsPayedTotalMonth(month);
        List<SessionDto> monthIncomeTotalMonth = studentService.studentsMonthIncomeTotalMonth(month);

        for (SessionDto sessionDto : debtsTotalMonth) {
            debts += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : payedTotalMonth) {
            payed += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : monthIncomeTotalMonth) {
            totalMonthIncome += getPayedSession(sessionDto);
        }
        setPaymentAllStudents(debts, payed, totalMonthIncome, studentsStatistics);

        return studentsStatistics;
    }

    private int getPayedSession(SessionDto sessionDto) {
        return sessionDto.getDuration() * sessionDto.getPricePerHour() / 60;
    }

    private void setPayment(int toPayToNow, int toPayTillEndOfMonth, int totalPay, int payedTotal, StudentStatistics studentStatistics) {
        studentStatistics.setToPayToNow(toPayToNow);
        studentStatistics.setToPayTillEndOfMonth(toPayTillEndOfMonth);
        studentStatistics.setTotalPay(totalPay);
        studentStatistics.setPayedTotal(payedTotal);
    }

    private void setPaymentAllStudents(int debts, int payed, int totalMonthIncome, StudentsStatistics studentsStatistics) {
        studentsStatistics.setDebts(debts);
        studentsStatistics.setPayed(payed);
        studentsStatistics.setTotalMonthIncome(totalMonthIncome);
    }
}
