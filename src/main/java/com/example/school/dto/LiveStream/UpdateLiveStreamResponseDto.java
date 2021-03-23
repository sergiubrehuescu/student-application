package com.example.school.dto.LiveStream;

import com.example.school.enums.LanguageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLiveStreamResponseDto {

    private String location;
    private LanguageType languageType; // si id //

}
