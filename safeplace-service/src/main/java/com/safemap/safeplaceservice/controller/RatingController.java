package com.safemap.safeplaceservice.controller;

import com.safemap.safeplaceservice.model.Rating;
import com.safemap.safeplaceservice.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/places/{placeId}/ratings")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    /**
     * Agregar una calificación a un lugar.
     */
    @PostMapping
    public ResponseEntity<?> addRating(@PathVariable String placeId,
                                       @RequestBody Rating ratingRequest,
                                       Principal principal) {
        try {
            Rating rating = Rating.builder()
                    .placeId(placeId)
                    //.userId(principal.getName())  // userId desde el JWT
                    .score(ratingRequest.getScore())
                    .comment(ratingRequest.getComment())
                    .build();

            Rating created = service.addRating(rating);
            return ResponseEntity.created(URI.create("/places/" + placeId + "/ratings/" + created.getId()))
                    .body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtener todas las calificaciones de un lugar.
     */
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(@PathVariable String placeId) {
        return ResponseEntity.ok(service.getRatingsByPlace(placeId));
    }
}
