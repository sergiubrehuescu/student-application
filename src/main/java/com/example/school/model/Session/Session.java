package com.example.school.model.Session;

import com.example.school.model.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSession;
    private String languageProgramming;
    private Integer duration;
    private Integer pricePerHour;
    private boolean paid; //change to boolean
    private LocalDate localDate;
    private boolean recurrent;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student; //numele var trebuie sa corespunca cu ce avem in mappedBy


}
