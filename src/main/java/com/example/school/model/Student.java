package com.example.school.model;

import com.example.school.annotations.LastName;
import com.example.school.annotations.MyEmail;
import com.example.school.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "The firstName of the Student must not be empty")
    @Size(min = 1,max= 30 ,message = "Range is out of 1 - 30 characters")
    private String firstName;
    @NotNull(message=  "{validation.lastName.NotNull}")
    @LastName
    private String lastName;
    //@Size(min = 0,max= 130 ,message = "Name of the person must be between 0 -130")
    private Integer age;
    private LocalDate dateOfBirth;
    @NotNull(message=  "{validation.email.NotNull}")
    @MyEmail
    private String email;
    //@EnumAnnotation(genderType = Gender.MALE,message = "The Gender is not MALE or FEMALE")
    private Gender gender;
    private String CNP;

    @OneToMany(mappedBy = "student")
    private List<Session> sessionList = new ArrayList<>();

}

