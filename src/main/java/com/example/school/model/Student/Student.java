package com.example.school.model.Student;

import com.example.school.annotations.CNP.MyCNP;
import com.example.school.enums.Gender;
import com.example.school.model.LiveStream;
import com.example.school.model.Session.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private Integer age;

    private LocalDate dateOfBirth;

    private Gender gender;

    @MyCNP
    private String CNP;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_contact_details_id")
    private StudentContactDetails studentContactDetails;

    //Sa se propage sesiunea in baza de date,
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Session> sessionList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "liveStream_students",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "livestream_id"))
    private List<LiveStream> liveStreamList = new ArrayList<>();

}

