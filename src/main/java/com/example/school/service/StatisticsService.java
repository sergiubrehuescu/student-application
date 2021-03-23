package com.example.school.service;

import com.example.school.dto.Session.SessionDto;
import com.example.school.dto.Student.PastStudentStatisticsResponseDto;
import com.example.school.dto.Student.StudentStatisticsResponseDto;
import com.example.school.dto.Student.StudentsStatisticsResponseDto;
import com.example.school.service.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatisticsService {

    @Autowired
    private StudentService studentService;

    public StudentStatisticsResponseDto studentStatistics(Integer studentId) {
        AtomicInteger deptToday= new AtomicInteger(); //int nu poate fi folosit in forEach
        int deptMonth=0;
        int monthPay=0;
        int remainingPay=0;
        int payed=0;
        StudentStatisticsResponseDto studentStatisticsResponseDto = new StudentStatisticsResponseDto();
        List<SessionDto> sessionDtoListDeptToday = studentService.deptToday(studentId); //double
        List<SessionDto> sessionDtoListDeptMonth = studentService.deptMonth(studentId);
        List<SessionDto> sessionDtoListMonthPay = studentService.monthPay(studentId);
        List<SessionDto> sessionDtoRemainingPay = studentService.remainingPay(studentId);
        List<SessionDto> sessionDtoListPayed = studentService.payed(studentId);

        sessionDtoListDeptToday.forEach(x-> deptToday.addAndGet(getPayedSession(x))); //nu poti folosi un int/Integer in forEach

        deptMonth += sessionDtoListDeptMonth.stream().mapToInt(this::getPayedSession).sum();

        for (SessionDto sessionDto : sessionDtoListMonthPay) {
            monthPay += getPayedSession(sessionDto);
        }

        for (SessionDto sessionDto : sessionDtoRemainingPay) {
            remainingPay += getPayedSession(sessionDto);
        }

        for (SessionDto sessionDto : sessionDtoListPayed) {
            payed += getPayedSession(sessionDto);
        }

        setPayment(deptToday.get(), deptMonth, monthPay, remainingPay, payed, studentStatisticsResponseDto);
        return studentStatisticsResponseDto;
    }

    public PastStudentStatisticsResponseDto studentStatisticsMonthYear(Integer studentId, Month month, Year year) {
        int monthPayS=0,remainingPayS=0,payedS=0;
        PastStudentStatisticsResponseDto pastStudentStatisticsResponseDto = new PastStudentStatisticsResponseDto();
        List<SessionDto> sessionDtoListMonthPayS = studentService.monthPayS(studentId,month,year);
        List<SessionDto> sessionDtoListRemainingPayS = studentService.remainingPayS(studentId,month,year);
        List<SessionDto> sessionDtoListPayedS = studentService.payedS(studentId,month,year);

        for (SessionDto sessionDto : sessionDtoListMonthPayS) {
            monthPayS += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListRemainingPayS) {
            remainingPayS += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : sessionDtoListPayedS) {
            payedS += getPayedSession(sessionDto);
        }

        setPaymentS(monthPayS, remainingPayS, payedS, pastStudentStatisticsResponseDto);
        return pastStudentStatisticsResponseDto;
    }

    public StudentsStatisticsResponseDto studentsStatistics() {
        //todo REFACTORING !!!!
        int debts=0,payed=0,income=0;
        StudentsStatisticsResponseDto studentsStatisticsResponseDto = new StudentsStatisticsResponseDto();
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
            income += getPayedSession(sessionDto);
        }
        setPaymentAllStudents(debts, payed, income, studentsStatisticsResponseDto);
        return studentsStatisticsResponseDto;
    }

    public StudentsStatisticsResponseDto studentsStatisticsMonthYear(Month month, Year year) {
        int debts=0,payed=0,totalMonthIncome=0;
        StudentsStatisticsResponseDto studentsStatisticsResponseDto = new StudentsStatisticsResponseDto();
        List<SessionDto> debtsTotalMonth = studentService.studentsDebtsTotaMonth(month,year);
        List<SessionDto> payedTotalMonth = studentService.studentsPayedTotalMonth(month,year);
        List<SessionDto> monthIncomeTotalMonth = studentService.studentsMonthIncomeTotalMonth(month,year);

        for (SessionDto sessionDto : debtsTotalMonth) {
            debts += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : payedTotalMonth) {
            payed += getPayedSession(sessionDto);
        }
        for (SessionDto sessionDto : monthIncomeTotalMonth) {
            totalMonthIncome += getPayedSession(sessionDto);
        }
        setPaymentAllStudents(debts, payed, totalMonthIncome, studentsStatisticsResponseDto);
        return studentsStatisticsResponseDto;
    }

    private int getPayedSession(SessionDto sessionDto) {
        return sessionDto.getDuration() * sessionDto.getPricePerHour() / 60;
    }

    private void setPayment(int deptToday, int deptMonth, int monthPay, int remainingPay, int payed, StudentStatisticsResponseDto studentStatisticsResponseDto) {
        studentStatisticsResponseDto.setDeptToday(deptToday);
        studentStatisticsResponseDto.setDeptMonth(deptMonth);
        studentStatisticsResponseDto.setMonthPay(monthPay);
        studentStatisticsResponseDto.setRemainingPay(remainingPay);
        studentStatisticsResponseDto.setPayed(payed);
    }

    private void setPaymentS(int monthPayS, int remainingPayS, int payedS, PastStudentStatisticsResponseDto pastStudentStatisticsResponseDto) {
        pastStudentStatisticsResponseDto.setMonthPay(monthPayS);
        pastStudentStatisticsResponseDto.setRemainingPay(remainingPayS);
        pastStudentStatisticsResponseDto.setPayed(payedS);
    }

    private void setPaymentAllStudents(int debts, int payed, int totalMonthIncome, StudentsStatisticsResponseDto studentsStatisticsResponseDto) {
        studentsStatisticsResponseDto.setDebts(debts);
        studentsStatisticsResponseDto.setPayed(payed);
        studentsStatisticsResponseDto.setTotalMonthIncome(totalMonthIncome);
    }

    /*    public StatisticsOverAllDto statisticsOverAll(Month month, Year year) {
        List<Session> sessionList = sessionRepository.findAll();
        StatisticsOverAllDto statisticsOverAllDto = new StatisticsOverAllDto();
        ArrayList<WeekHours> weekHours = new ArrayList<WeekHours>();
        float monthHOurs = 0;
        LocalDate start = LocalDate.of(year.getValue(), month.getValue(), 1);
        LocalDate end = LocalDate.of(year.getValue(), month.getValue(), month.length(((year.getValue() % 4 == 0) && (year.getValue() % 100 != 0)) || (year.getValue() % 400 == 0)));
        Integer[] dayOfWeeks = {start.plusWeeks(0).getDayOfWeek().getValue(), start.plusWeeks(1).getDayOfWeek().getValue(), start.plusWeeks(2).getDayOfWeek().getValue(), start.plusWeeks(3).getDayOfWeek().getValue(),start.plusWeeks(4).getDayOfWeek().getValue()};
        System.out.println(start.getDayOfWeek());
        weekHours.add(new WeekHours(start.plusWeeks(0).toString()+ " " + start.plusDays(6), 0));
        weekHours.add(new WeekHours(start.plusWeeks(1).toString()+ " " + start.plusDays(13), 0));
        weekHours.add(new WeekHours(start.plusWeeks(2).toString()+ " " + start.plusDays(20), 0));
        weekHours.add(new WeekHours(start.plusWeeks(3).toString()+ " " + start.plusDays(27), 0));
        weekHours.add(new WeekHours(start.plusWeeks(4).toString()+ " " + end, 0));
        for (Session session : sessionList) {
            if (session.getLocalDate().getMonth().equals(month) && session.getLocalDate().getYear() == year.getValue()) {
                monthHOurs += (float) session.getDuration() / 60;
                for (int i = 0; i < dayOfWeeks.length-1; i++) {
                    if(session.getLocalDate().getDayOfMonth()>= dayOfWeeks[i] && session.getLocalDate().getDayOfMonth() < dayOfWeeks[++i]) {
                        weekHours.get(i).setHours(weekHours.get(i).getHours()+session.getPricePerHour()/60);
                    }
                }
            }
        }
        statisticsOverAllDto.setMonthHours(monthHOurs);
        statisticsOverAllDto.setWeekHours(weekHours);
        return statisticsOverAllDto;
    }*/

}
