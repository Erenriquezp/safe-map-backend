package com.safemap.safeplaceservice.service;

import com.safemap.safeplaceservice.model.Rating;
import com.safemap.safeplaceservice.repository.PlaceRepository;
import com.safemap.safeplaceservice.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepo;
    private final PlaceRepository placeRepo;

    public RatingService(RatingRepository ratingRepo, PlaceRepository placeRepo) {
        this.ratingRepo = ratingRepo;
        this.placeRepo = placeRepo;
    }

    public Rating addRating(Rating rating) {
        // 1. Validar score
        if (rating.getScore() < 1 || rating.getScore() > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        // 2. Evitar duplicado del mismo usuario al mismo lugar
        if (ratingRepo.existsByUserIdAndPlaceId(rating.getUserId(), rating.getPlaceId())) {
            throw new IllegalArgumentException("User already rated this place");
        }

        // 3. Guardar rating
        Rating saved = ratingRepo.save(rating);

        // 4. Actualizar promedio en el lugar
        updateAverageRating(rating.getPlaceId());

        return saved;
    }

    public List<Rating> getRatingsByPlace(String placeId) {
        return ratingRepo.findByPlaceId(placeId);
    }

    private void updateAverageRating(String placeId) {
        List<Rating> ratings = ratingRepo.findByPlaceId(placeId);
        if (ratings.isEmpty()) return;

        double avg = ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        placeRepo.findById(placeId).ifPresent(place -> {
            place.setAverageRating(avg);
            placeRepo.save(place);
        });
    }
}
