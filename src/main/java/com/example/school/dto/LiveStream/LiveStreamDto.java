package com.example.school.dto.LiveStream;

import com.example.school.enums.LanguageType;
import com.example.school.model.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiveStreamDto {

    private int id;
    private String location;
    private LocalDate createdAt;
    private LanguageType languageType;
    List<Student> studentList = new ArrayList<>();

    public LiveStreamDto(String location, LocalDate createdAt, LanguageType languageType) {
        this.location = location;
        this.createdAt = createdAt;
        this.languageType = languageType;
    }
}
