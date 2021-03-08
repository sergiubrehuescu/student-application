package com.example.school.dto.LiveStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentToLiveStreamRequestDto {

    private int studentId;
    private int liveStreamId;
}
