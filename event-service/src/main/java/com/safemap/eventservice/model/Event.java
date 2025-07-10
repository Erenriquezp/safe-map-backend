package com.safemap.eventservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    private String id;

    private String placeId;
    private LocalDateTime dateTime;
    private String type;          // e.g. "robbery", "assault", "fire"
    private String description;
}
