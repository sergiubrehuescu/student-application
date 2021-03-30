package com.example.school.service;

import com.example.school.controller.LiveStreamController;
import com.example.school.dto.LiveStream.*;
import com.example.school.dto.Student.StudentDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.LiveStream.LiveStream;
import com.example.school.model.Student.Student;
import com.example.school.repo.LiveStreamRepository;
import com.example.school.service.Student.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiveStreamService {

    @Autowired
    private LiveStreamRepository liveStreamRepository;

    @Autowired
    private StudentService studentService;

    ModelMapper mapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(LiveStreamController.class);

    public CreateLiveStreamResponseDto createLiveStream(CreateLiveStreamRequestDto createLiveStreamRequestDto) {
        logger.info("Trecut prin LiveStreamController createLiveStream");
        LiveStream liveStream = new LiveStream();
        liveStream.setLocation(createLiveStreamRequestDto.getLocation());
        liveStream.setLanguageType(createLiveStreamRequestDto.getLanguageType());
        LiveStream savedLiveStream = liveStreamRepository.save(liveStream);
        CreateLiveStreamResponseDto createLiveStreamResponseDto = mapper.map(createLiveStreamRequestDto,CreateLiveStreamResponseDto.class);
        createLiveStreamResponseDto.setId(savedLiveStream.getId());
        return createLiveStreamResponseDto;
    }

    public RegisterStudentToLiveStreamResponseDto registerToLiveStream(RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto) {
        LiveStream liveStream =findLiveStreamById(registerStudentToLiveStreamRequestDto.getLiveStreamId());
        Student student = studentService.findStudentById(registerStudentToLiveStreamRequestDto.getStudentId());
        checkRegisterLiveStream(liveStream, student);

        return liveStreamRegisterConfirmationDto(mapper.map(liveStream,LiveStreamDto.class),mapper.map(student,StudentDto.class));
    }

    public RegisterStudentToLiveStreamResponseDto unregisterToLiveStream(RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto){
        LiveStream liveStream =findLiveStreamById(registerStudentToLiveStreamRequestDto.getLiveStreamId());
        Student student = studentService.findStudentById(registerStudentToLiveStreamRequestDto.getStudentId());
        checkUnregisterLiveStream(liveStream, student);

        return liveStreamRegisterConfirmationDto(mapper.map(liveStream,LiveStreamDto.class),mapper.map(student,StudentDto.class));
    }

    public LiveStream updateLiveStream(LiveStream liveStream) {
        LiveStream newLiveStream = findLiveStreamById(liveStream.getId());
        setUpdateLiveStream(liveStream, newLiveStream);
        return liveStreamRepository.save(newLiveStream);
    }

    public LiveStreamDto deleteLiveStream(Integer id) {
        LiveStream liveStream = findLiveStreamById(id);
        List<Student> students = liveStream.getStudentList();

        students.stream().forEach(student -> student.getLiveStreamList().remove(liveStream));

        liveStreamRepository.delete(liveStream);
        LiveStreamDto liveStreamDto =mapper.map(liveStream,LiveStreamDto.class);
        return liveStreamDto;
    }

    public LiveStreamDto getLiveStream(Integer id) {
        LiveStream liveStream = findLiveStreamById(id);
        LiveStreamDto liveStreamDto=mapper.map(liveStream,LiveStreamDto.class);
        return liveStreamDto;
    }

    private LiveStream findLiveStreamById(Integer id) {
        Optional<LiveStream> liveStream = liveStreamRepository.findById(id);
        if(liveStream.isPresent()){
            return liveStream.get();
        }
        throw new ResourceNotFoundException("LiveStream with id " + id + " was not found");

        //todo return liveStreamRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("LiveStream with id " + id + " was not found"));
    }

    private RegisterStudentToLiveStreamResponseDto liveStreamRegisterConfirmationDto(LiveStreamDto liveStreamDto, StudentDto studentDto) {
        RegisterStudentToLiveStreamResponseDto registerStudentToLiveStreamResponseDto = new RegisterStudentToLiveStreamResponseDto();
        registerStudentToLiveStreamResponseDto.setLiveStreamDto(liveStreamDto);
        registerStudentToLiveStreamResponseDto.setStudentDto(studentDto);

        return registerStudentToLiveStreamResponseDto;
    }

    private void setUpdateLiveStream(LiveStream liveStream, LiveStream newLiveStream) {
        newLiveStream.setLanguageType(liveStream.getLanguageType());
        newLiveStream.setCreatedAt(liveStream.getCreatedAt());
        newLiveStream.setLocation(liveStream.getLocation());
    }

    private void checkUnregisterLiveStream(LiveStream liveStream, Student student) {
        if(liveStream.getStudentList().contains(student)){
            liveStream.getStudentList().remove(student);
            student.getLiveStreamList().remove(liveStream);
            liveStreamRepository.save(liveStream);
        }
        else throw new ResourceNotFoundException("Student with id" + student.getId() + "is not part of the LiveStream anyway");
    }
    private void checkRegisterLiveStream(LiveStream liveStream, Student student) {
        if(!liveStream.getStudentList().contains(student)){
            liveStream.getStudentList().add(student);
            student.getLiveStreamList().add(liveStream);
            liveStreamRepository.save(liveStream);
        }
        else throw new ResourceNotFoundException("Student with id " + student.getId() + " is already part of the LiveStream");
        //todo custom exception NOT ALLOWED
    }


}
