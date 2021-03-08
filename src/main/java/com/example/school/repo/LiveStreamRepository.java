package com.example.school.repo;

import com.example.school.model.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LiveStreamRepository extends JpaRepository<LiveStream,Integer> {
}
