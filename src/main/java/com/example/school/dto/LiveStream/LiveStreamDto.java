package com.example.school.dto.LiveStream;

import com.example.school.dto.Student.StudentDto;
import com.example.school.enums.LanguageType;
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
    private LocalDate expireRegisterDate;
    private String topicLiveStream;
    private List<StudentDto> studentList = new ArrayList<>();

    public LiveStreamDto(String location, LocalDate createdAt, LanguageType languageType,LocalDate expireRegisterDate,String topicLiveStream) {
        this.location = location;
        this.createdAt = createdAt;
        this.languageType = languageType;
        this.expireRegisterDate=expireRegisterDate;
        this.topicLiveStream=topicLiveStream;
    }
}
