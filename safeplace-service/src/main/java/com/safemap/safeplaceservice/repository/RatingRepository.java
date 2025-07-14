package com.safemap.safeplaceservice.repository;

import com.safemap.safeplaceservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByPlaceId(String placeId);
    boolean existsByUserIdAndPlaceId(String userId, String placeId);
}
