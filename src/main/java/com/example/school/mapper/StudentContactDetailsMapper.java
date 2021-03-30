package com.example.school.mapper;

import com.example.school.dto.Student.StudentContactDetailsDto;
import com.example.school.dto.Student.StudentDto;
import com.example.school.model.Student.StudentContactDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentContactDetailsMapper {

    @Autowired
    StudentMapper studentMapper;

    ModelMapper mapper = new ModelMapper();

    public StudentContactDetailsDto mapToDto(StudentContactDetails studentContactDetails){

        return new StudentContactDetailsDto(studentContactDetails.getId(),studentContactDetails.getFirstName(),studentContactDetails.getLastName(),studentContactDetails.getPhoneNumber(),studentContactDetails.getEmail(),mapper.map(studentContactDetails.getStudent(), StudentDto.class));/*studentMapper.mapToDto(studentContactDetails.getStudent()));*/
    }

    public StudentContactDetailsDto mapToDtoWithoutStudent(StudentContactDetails studentContactDetails){

        return new StudentContactDetailsDto(studentContactDetails.getId(),studentContactDetails.getFirstName(),studentContactDetails.getLastName(),studentContactDetails.getPhoneNumber(),studentContactDetails.getEmail());
    }
}
