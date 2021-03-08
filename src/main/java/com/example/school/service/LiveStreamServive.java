package com.example.school.service;

import com.example.school.controller.LiveStreamController;
import com.example.school.dto.LiveStream.CreateLiveStreamRequestDto;
import com.example.school.dto.LiveStream.CreateLiveStreamResponseDto;
import com.example.school.dto.LiveStream.LiveStreamDto;
import com.example.school.dto.LiveStream.RegisterStudentToLiveStreamRequestDto;
import com.example.school.mapper.LiveStreamMapper;
import com.example.school.model.LiveStream;
import com.example.school.model.Student.Student;
import com.example.school.repo.LiveStreamRepository;
import com.example.school.repo.StudentRepository;
import com.example.school.service.Student.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
public class LiveStreamServive {

    @Autowired
    private LiveStreamRepository liveStreamRepository;

    @Autowired
    private LiveStreamMapper liveStreamMapper;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    private Logger logger = LoggerFactory.getLogger(LiveStreamController.class);

    public CreateLiveStreamResponseDto createLiveStream(CreateLiveStreamRequestDto createLiveStreamRequestDto) {
        logger.info("Trecut prin LiveStreamController createLiveStream");
        LiveStream liveStream = new LiveStream();
        liveStream.setLocation(createLiveStreamRequestDto.getLocation());
        liveStream.setLanguageType(createLiveStreamRequestDto.getLanguageType());
        liveStreamRepository.save(liveStream);
        CreateLiveStreamResponseDto createLiveStreamResponseDto = liveStreamMapper.mapToLiveStreamDto2(createLiveStreamRequestDto);
        return createLiveStreamResponseDto;
    }

    public String registerToLiveStream(RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto) {
        LiveStream liveStream =findLiveStreamById(registerStudentToLiveStreamRequestDto.getLiveStreamId());
        Student student = studentService.findStudentById(registerStudentToLiveStreamRequestDto.getStudentId());
        liveStream.getStudentList().add(student);
        studentRepository.findById(registerStudentToLiveStreamRequestDto.getStudentId()).get().getLiveStreamList().add(liveStream);
        liveStreamRepository.save(liveStream);
        return student.getStudentContactDetails().getFirstName() + " " + student.getStudentContactDetails().getLastName() + "s-a inregistrat la evenimentul in limbajul "
                + liveStream.getLanguageType() + " din " + liveStream.getLocation();
    }

    public LiveStream updateLiveStream(LiveStream liveStream) {
        LiveStream newLiveStream = findLiveStreamById(liveStream.getId());
        newLiveStream.setLanguageType(liveStream.getLanguageType());
        newLiveStream.setCreatedAt(liveStream.getCreatedAt());
        newLiveStream.setLocation(liveStream.getLocation());
        return liveStreamRepository.save(newLiveStream);
    }

    public LiveStreamDto deleteLiveStream(Integer id) {
        LiveStream liveStream = findLiveStreamById(id);
        List<Student> students = findLiveStreamById(id).getStudentList();
        for (int i = 0; i < students.size(); i++) {
            students.get(i).getLiveStreamList().remove(liveStream);
        }
        liveStreamRepository.delete(liveStream);
        LiveStreamDto liveStreamDto = liveStreamMapper.mapToLiveStreamDto(liveStream);
        return liveStreamDto;
    }

    public LiveStreamDto getLiveStream(Integer id) {
        LiveStream liveStream = findLiveStreamById(id);
        LiveStreamDto liveStreamDto=liveStreamMapper.mapToLiveStreamDto(liveStream);
        return liveStreamDto;
    }

    private LiveStream findLiveStreamById(Integer id) {
        Optional<LiveStream> liveStream = liveStreamRepository.findById(id);
        if(liveStream.isPresent()){
            return liveStream.get();
        }
        throw new ResolutionException("LiveStream with id " + id + " was not found");
    }

}
