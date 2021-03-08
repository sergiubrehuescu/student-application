package com.example.school.mapper;

import com.example.school.dto.LiveStream.CreateLiveStreamRequestDto;
import com.example.school.dto.LiveStream.CreateLiveStreamResponseDto;
import com.example.school.dto.LiveStream.LiveStreamDto;
import com.example.school.model.LiveStream;
import org.springframework.stereotype.Component;

@Component
public class LiveStreamMapper {

    public LiveStreamDto mapToLiveStreamDto(LiveStream liveStream){
        return new LiveStreamDto(liveStream.getLocation(), liveStream.getCreatedAt(),liveStream.getLanguageType());
    }

    public CreateLiveStreamResponseDto mapToLiveStreamDto2(CreateLiveStreamRequestDto createLiveStreamRequestDto){
        return new CreateLiveStreamResponseDto(createLiveStreamRequestDto.getLanguageType(),createLiveStreamRequestDto.getLocation());
    }

}
