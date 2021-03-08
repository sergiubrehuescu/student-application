package com.example.school.model;

import com.example.school.enums.LanguageType;
import com.example.school.model.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livestreams")
public class LiveStream {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String location;
    private LocalDate createdAt=LocalDate.now();
    private LanguageType languageType;

    @ManyToMany(mappedBy = "liveStreamList")
    List<Student> studentList = new ArrayList<>();

}
