package com.example.school.controller;

import com.example.school.dto.LiveStream.*;
import com.example.school.model.LiveStream.LiveStream;
import com.example.school.service.LiveStreamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("livestream")
public class LiveStreamController {

    @Autowired
    private LiveStreamService liveStreamServive;

    ModelMapper mapper = new ModelMapper();

    @PostMapping
    private CreateLiveStreamResponseDto createLiveStream(@RequestBody CreateLiveStreamRequestDto createLiveStreamRequestDto){
         return liveStreamServive.createLiveStream(createLiveStreamRequestDto);
    }

    @DeleteMapping("/{id}")
    private LiveStreamDto deleteLiveStream(@PathVariable Integer id){
        return liveStreamServive.deleteLiveStream(id);
    }

    @PutMapping
    private LiveStreamDto updateLiveStream(@RequestBody LiveStream liveStream){
        liveStreamServive.updateLiveStream(liveStream);
        return mapper.map(liveStream,LiveStreamDto.class);
    }

    @GetMapping("/{id}")
    private LiveStreamDto getLiveStream(@PathVariable Integer id){
        return liveStreamServive.getLiveStream(id);
    }

    @PostMapping("registerToLiveStream")
    private RegisterStudentToLiveStreamResponseDto registerToLiveStream(@RequestBody RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto){
        return liveStreamServive.registerToLiveStream(registerStudentToLiveStreamRequestDto);
    }

    @PostMapping("unregisterToLiveStream")
    private RegisterStudentToLiveStreamResponseDto unregisterToLiveStream(@RequestBody RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto){
        return liveStreamServive.unregisterToLiveStream(registerStudentToLiveStreamRequestDto);
    }

}
