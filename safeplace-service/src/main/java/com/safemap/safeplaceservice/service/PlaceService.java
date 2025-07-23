package com.safemap.safeplaceservice.service;

import com.safemap.safeplaceservice.dto.PlaceResponseDto;
import com.safemap.safeplaceservice.dto.ScheduleDto;
import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository repository;
    private final WebClient.Builder webClientBuilder;

    public PlaceService(PlaceRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public Optional<PlaceResponseDto> findWithSchedules(String id) {
        return repository.findById(id)
                .map(place -> {
                    List<ScheduleDto> horarios = fetchSchedules(place.getId());
                    return PlaceResponseDto.builder()
                            .id(place.getId())
                            .name(place.getName())
                            .description(place.getDescription())
                            .latitude(place.getLatitude())
                            .longitude(place.getLongitude())
                            .averageRating(place.getAverageRating())
                            .horarios(horarios)
                            .build();
                });
    }

    private List<ScheduleDto> fetchSchedules(String placeId) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("lb://schedule-service/schedules/place/{placeId}", placeId)
                    .retrieve()
                    .bodyToFlux(ScheduleDto.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return List.of(); // en caso de error, no rompe la respuesta
        }
    }

    // CREATE
    public Place create(Place place) {
        place.setCreatedAt(Instant.now());
        place.setUpdatedAt(Instant.now());
        place.setAverageRating(0.0); // default
        return repository.save(place);
    }

    // READ ALL
    public List<Place> findAll() {
        return repository.findAll();
    }

    // READ BY ID
    public Optional<Place> findById(String id) {
        return repository.findById(id);
    }

    // UPDATE
    public Optional<Place> update(String id, Place updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setLatitude(updated.getLatitude());
            existing.setLongitude(updated.getLongitude());
            existing.setUpdatedAt(Instant.now());
            return repository.save(existing);
        });
    }

    // DELETE
    public void delete(String id) {
        repository.deleteById(id);
    }

    // (Opcional) Filtrar por usuario creador
    public List<Place> findByCreator(String userId) {
        return repository.findAll().stream()
                .filter(p -> userId.equals(p.getCreatedBy()))
                .toList();
    }

    public List<Place> findSafePlaces(double minRating) {
        return repository.findByAverageRatingGreaterThanEqual(minRating);
    }

}
