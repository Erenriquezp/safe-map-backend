package com.safemap.scheduleservice.repository;

import com.safemap.scheduleservice.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    List<Schedule> findByPlaceId(String placeId);
}
