package com.example.school.repo;

import com.example.school.model.Student.StudentContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentContactDetailsRepository extends JpaRepository<StudentContactDetails, Integer> {
}
