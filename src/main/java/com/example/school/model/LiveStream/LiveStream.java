package com.example.school.model.LiveStream;

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
    private LocalDate createdAt=LocalDate.now(); //todo tema live stream , date , expires at()
    //todo with entity
    private LanguageType languageType;

    @ManyToMany(mappedBy = "liveStreamList")
    List<Student> studentList = new ArrayList<>();

}
