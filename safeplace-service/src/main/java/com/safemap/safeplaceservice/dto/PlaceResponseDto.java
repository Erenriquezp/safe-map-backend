package com.safemap.safeplaceservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceResponseDto {
    private String id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private double averageRating;

    private List<ScheduleDto> horarios;
}
