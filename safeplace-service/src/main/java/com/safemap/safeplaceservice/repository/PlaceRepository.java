package com.safemap.safeplaceservice.repository;

import com.safemap.safeplaceservice.model.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {
    boolean existsByName(String name);  // Opcional: prevenir duplicados
    List<Place> findByAverageRatingGreaterThanEqual(double rating);
}

