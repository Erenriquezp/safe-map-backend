package com.safemap.eventservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    private String id;

    private String placeId;       // FK → LugarSeguro
    private String reportedBy;    // FK → Usuario (desde JWT)
    private LocalDateTime dateTime;

    private String title;
    private String type;          // POSITIVE, NEGATIVE, NEUTRAL
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;
}
