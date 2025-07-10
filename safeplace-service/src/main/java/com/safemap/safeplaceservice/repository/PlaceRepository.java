package com.safemap.safeplaceservice.repository;


import com.safemap.safeplaceservice.model.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {
    // Optional custom queries, e.g. find by name or geo radius later
}
