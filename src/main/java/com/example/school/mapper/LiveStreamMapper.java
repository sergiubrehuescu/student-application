package com.example.school.mapper;

import com.example.school.dto.LiveStream.CreateLiveStreamRequestDto;
import com.example.school.dto.LiveStream.CreateLiveStreamResponseDto;
import com.example.school.dto.LiveStream.LiveStreamDto;
import com.example.school.model.LiveStream.LiveStream;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LiveStreamMapper {

    public LiveStreamDto mapToLiveStreamDto(LiveStream liveStream){
        return new LiveStreamDto(liveStream.getLocation(), liveStream.getCreatedAt(),liveStream.getLanguageType(),liveStream.getExpireRegisterDate(),liveStream.getTopicLiveStream());
    }

    public CreateLiveStreamResponseDto mapToLiveStreamDto2(CreateLiveStreamRequestDto createLiveStreamRequestDto){
        return new CreateLiveStreamResponseDto(createLiveStreamRequestDto.getLanguageType(),createLiveStreamRequestDto.getLocation(),createLiveStreamRequestDto.getExpireRegisterDate(),createLiveStreamRequestDto.getTopicLiveStream());
    }

    public List<LiveStreamDto> mapToLiveStreamListDto(List<LiveStream> liveStreamList) {
        List<LiveStreamDto> liveStreamDtos = new ArrayList<>();
        return liveStreamList.stream()
                .map(this::mapToLiveStreamDto)
                .collect(Collectors.toList());
    }

}
