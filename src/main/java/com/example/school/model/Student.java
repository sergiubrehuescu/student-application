package com.example.school.model;

import com.example.school.annotations.LastName;
import com.example.school.annotations.MyCNP;
import com.example.school.annotations.MyEmail;
import com.example.school.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull(message=  "{validation.firstName.NotNull}")
    @Size(min = 1,max= 30 ,message = "Range is out of 1 - 30 characters")
    private String firstName;
    @NotNull(message=  "{validation.lastName.NotNull}")
    @LastName
    private String lastName;
    private Integer age;
    private LocalDate dateOfBirth;
    @NotNull(message=  "{validation.email.NotNull}")
    @MyEmail
    private String email;
    private Gender gender;
    @MyCNP
    private String CNP;

    //Sa se propage sesiunea in baza de date,
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Session> sessionList = new ArrayList<>();

}

