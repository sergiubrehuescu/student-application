package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Student.StudentContactDetails;
import com.example.school.repo.StudentContactDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentContactDetailsService {

    @Autowired
    private StudentContactDetailsRepository studentContactDetailsRepository;

    public StudentContactDetails findStudentDetailsById(Integer id) {
        return studentContactDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student contact with id " + id + " was not found"));
    }
}