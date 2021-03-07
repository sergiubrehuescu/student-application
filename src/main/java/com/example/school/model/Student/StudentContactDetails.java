package com.example.school.model.Student;

import com.example.school.annotations.Email.MyEmail;
import com.example.school.annotations.LastName.LastName;
import com.example.school.annotations.PhoneNumber.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="studentscontactdetails")
public class StudentContactDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message=  "{validation.firstName.NotNull}")
    @Size(min = 1,max= 30 ,message = "Range is out of 1 - 30 characters")
    private String firstName;

    @NotNull(message=  "{validation.lastName.NotNull}")
    @LastName
    private String lastName;

    @PhoneNumber
    private String phoneNumber;

    @NotNull(message=  "{validation.email.NotNull}")
    @MyEmail
    private String email;

    @OneToOne(mappedBy = "studentContactDetails")
    private Student student;
}
