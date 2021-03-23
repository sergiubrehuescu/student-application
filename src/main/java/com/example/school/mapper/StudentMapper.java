package com.example.school.mapper;

import com.example.school.dto.Student.StudentDto;
import com.example.school.model.Student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//sesiune,data,ora,elev,profesor,limbajprogramare(pret/h),timp diferit sesiune,
//adau elev,sesiune pe elev,
//nu a platit arata cu rosu

@Component
public class StudentMapper {

    @Autowired
    private LiveStreamMapper liveStreamMapper;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentContactDetailsMapper studentContactDetailsMapper;

    public StudentDto mapToDto(Student student){
        return new StudentDto(student.getId(),student.getAge(),student.getDateOfBirth(),student.getGender());
    }
}
