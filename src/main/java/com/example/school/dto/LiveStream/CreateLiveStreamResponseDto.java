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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLiveStreamResponseDto {

    private int id;
    private String location;
    private LocalDate createdAt=LocalDate.now();
    private LocalDate expireRegisterDate;
    private String topicLiveStream;
    private LanguageType languageType;
    List<StudentDto> studentDtoList = new ArrayList<>();

    public CreateLiveStreamResponseDto(LanguageType languageType, String location,LocalDate expireRegisterDate,String topicLiveStream) {
        this.languageType=languageType;
        this.location=location;
        this.expireRegisterDate=expireRegisterDate;
        this.topicLiveStream=topicLiveStream;
    }
}
