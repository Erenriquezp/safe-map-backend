package com.safemap.safeplaceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
