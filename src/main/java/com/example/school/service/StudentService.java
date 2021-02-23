package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Student;
import com.example.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public Student findStudentById(Integer id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        }
        throw new ResourceNotFoundException("Student with ID " + id +" was not found");
    }


    public Student removeStudent(Integer id) {
        Student student=findStudentById(id);
        studentRepository.delete(student);
        return student;
    }

    public Student updateStudent(Student student){
        Student newStudent =findStudentById(student.getId());

        newStudent.setAge(student.getAge());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setEmail(student.getEmail());
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setGender(student.getGender());
        return studentRepository.save(newStudent);
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }


}
