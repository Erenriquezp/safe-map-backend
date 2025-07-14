package com.safemap.safeplaceservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @Id
    private String id;

    private String placeId;    // FK → Place.id
    private String userId;     // FK → User.id
    private int score;         // 1 to 5
    private String comment;    // Optional

    @CreatedDate
    private Instant createdAt;
}

