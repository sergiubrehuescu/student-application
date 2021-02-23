package com.example.school.mapper;

import com.example.school.dto.StudentDto;
import com.example.school.model.Student;
import org.springframework.stereotype.Component;

//sesiune,data,ora,elev,profesor,limbajprogramare(pret/h),timp diferit sesiune,
//adau elev,sesiune pe elev,
//nu a platit arata cu rosu

@Component
public class StudentMapper {

    public StudentDto mapToDto(Student student){
        return new StudentDto(student.getId(),student.getFirstName(),student.getLastName(),student.getAge(),student.getDateOfBirth(),student.getEmail(),student.getGender());
    }
}
