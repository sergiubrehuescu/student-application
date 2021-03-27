package com.example.school.dto.LiveStream;

import com.example.school.dto.Student.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentToLiveStreamResponseDto {

    private StudentDto studentDto;
    private LiveStreamDto liveStreamDto;
}
