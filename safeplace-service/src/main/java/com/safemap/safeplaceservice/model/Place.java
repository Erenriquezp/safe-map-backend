package com.safemap.safeplaceservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "places")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    private String id;

    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private double averageRating;  // computed client‑side or via aggregation
}
