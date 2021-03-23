package com.example.school.service.Student;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.StudentContactDetailsMapper;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.repo.StudentContactDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentContactDetailsService {

    @Autowired
    private StudentContactDetailsRepository studentContactDetailsRepository;

    @Autowired
    private StudentContactDetailsMapper studentContactDetailsMapper;

    @Autowired
    private StudentContactDetailsService studentContactDetailsService;

    public StudentContactDetails findStudentDetailsById(Integer id) {
        Optional<StudentContactDetails> studentContactDetails = studentContactDetailsRepository.findById(id);
        if(studentContactDetails.isPresent()){
            return studentContactDetails.get();
        }
        throw new ResourceNotFoundException("studentContactDetails with ID " + id +" was not found");
    }
}