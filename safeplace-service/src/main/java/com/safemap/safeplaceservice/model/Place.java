package com.safemap.safeplaceservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "places")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    private String id;

    private String name;          // Not null
    private String description;
    private double latitude;      // Not null
    private double longitude;     // Not null
    private double averageRating; // Calculado, no editable manualmente

    private String createdBy;     // FK → userId (obtenido del JWT)

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
