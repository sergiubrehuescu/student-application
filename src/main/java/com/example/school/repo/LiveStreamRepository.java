package com.example.school.repo;

import com.example.school.model.LiveStream.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveStreamRepository extends JpaRepository<LiveStream,Integer> {
}
