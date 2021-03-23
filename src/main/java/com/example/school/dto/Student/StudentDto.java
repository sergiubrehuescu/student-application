package com.example.school.dto.Student;

import com.example.school.dto.LiveStream.LiveStreamDto;
import com.example.school.dto.Session.SessionDto;
import com.example.school.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Integer id;
    private StudentContactDetailsDto studentContactDetailsDto;
    private Integer age;
    private LocalDate dateOfBirth;
    private Gender gender;
    private List<SessionDto> sessions =new ArrayList<SessionDto>();
    private List<LiveStreamDto> liveStreams = new ArrayList<>();


    public StudentDto(Integer id, Integer age, LocalDate dateOfBirth, Gender gender) {
        this.id=id;
        this.age=age;
        this.dateOfBirth=dateOfBirth;
        this.gender=gender;
    }
}
