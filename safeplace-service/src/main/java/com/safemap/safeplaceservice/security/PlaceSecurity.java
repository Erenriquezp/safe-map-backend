package com.safemap.safeplaceservice.security;

import com.safemap.safeplaceservice.repository.PlaceRepository;
import org.springframework.stereotype.Component;

@Component("placeSecurity")
public class PlaceSecurity {

    private final PlaceRepository repo;

    public PlaceSecurity(PlaceRepository repo) {
        this.repo = repo;
    }

    public boolean isOwner(String placeId, String userId) {
        return repo.findById(placeId)
                .map(place -> place.getCreatedBy().equals(userId))
                .orElse(false);
    }
}
