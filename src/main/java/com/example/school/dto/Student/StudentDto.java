package com.example.school.dto.Student;

import com.example.school.dto.DrivingLicense.DrivingLicenseDto;
import com.example.school.dto.Session.SessionDto;
import com.example.school.enums.Gender;
import com.example.school.model.Student.StudentContactDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Integer id;
    private StudentContactDetails studentContactDetails;
    private Integer age;
    private LocalDate dateOfBirth;
    private Gender gender;
    private DrivingLicenseDto drivingLicenseDto;
    private List<SessionDto> sessions =new ArrayList<SessionDto>();


    public StudentDto(Integer id, StudentContactDetails studentContactDetails, Integer age, LocalDate dateOfBirth, Gender gender) {
        this.id=id;
        this.studentContactDetails=studentContactDetails;
        this.age=age;
        this.dateOfBirth=dateOfBirth;
        this.gender=gender;
    }
}
