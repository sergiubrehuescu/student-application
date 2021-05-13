package com.example.school.dto.LiveStream;

import com.example.school.enums.LanguageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLiveStreamRequestDto {

    private String location;
    private LanguageType languageType;
    private LocalDate expireRegisterDate;
    private String topicLiveStream;
}
