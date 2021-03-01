package com.example.school.dto;

import com.example.school.enums.Gender;
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
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate dateOfBirth;
    private String email;
    private Gender gender;
    private List<SessionDto> sessions =new ArrayList<SessionDto>();

    public StudentDto(Integer id, String firstName, String lastName, Integer age, LocalDate dateOfBirth, String email, Gender gender) {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.age=age;
        this.dateOfBirth=dateOfBirth;
        this.email=email;
        this.gender=gender;
    }
}
