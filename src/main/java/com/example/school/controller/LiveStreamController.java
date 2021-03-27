package com.example.school.controller;

import com.example.school.dto.LiveStream.*;
import com.example.school.mapper.LiveStreamMapper;
import com.example.school.model.LiveStream.LiveStream;
import com.example.school.service.LiveStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("livestream")
public class LiveStreamController {

    @Autowired
    private LiveStreamService liveStreamServive;

    @Autowired
    private LiveStreamMapper liveStreamMapper;

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
        return liveStreamMapper.mapToLiveStreamDto(liveStream);
    }

    @GetMapping("/{id}")
    private LiveStreamDto getLiveStream(@PathVariable Integer id){
        return liveStreamServive.getLiveStream(id);
    }

    @PostMapping("registerToLiveStream")
    private RegisterStudentToLiveStreamResponseDto registerToLiveStream(@RequestBody RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto){
        return liveStreamServive.registerToLiveStream(registerStudentToLiveStreamRequestDto);
    }

    @PostMapping("unregisterToLiveStream") //todo QQQQQQQQQQQQ
    private RegisterStudentToLiveStreamResponseDto unregisterToLiveStream(@RequestBody RegisterStudentToLiveStreamRequestDto registerStudentToLiveStreamRequestDto){
        return liveStreamServive.unregisterToLiveStream(registerStudentToLiveStreamRequestDto);
    }

}
