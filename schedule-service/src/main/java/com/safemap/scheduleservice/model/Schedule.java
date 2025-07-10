package com.safemap.scheduleservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Document(collection = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;

    private String placeId;          // FK to Place.id
    private DayOfWeek dayOfWeek;     // Enum MONDAY…SUNDAY
    private LocalTime startTime;
    private LocalTime endTime;
}
