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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLiveStreamResponseDto {

    private int id;
    private String location;
    private LocalDate createdAt=LocalDate.now();
    private LanguageType languageType;
    List<Student> studentList = new ArrayList<>();

    public CreateLiveStreamResponseDto(LanguageType languageType, String location) {
        this.languageType=languageType;
        this.location=location;
    }
}
