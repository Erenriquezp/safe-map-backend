package com.safemap.safeplaceservice.config;

import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.model.Rating;
import com.safemap.safeplaceservice.repository.PlaceRepository;
import com.safemap.safeplaceservice.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PlaceRepository placeRepository;
    private final RatingRepository ratingRepository;

    @Bean
    CommandLineRunner initSafePlaces() {
        return args -> {
            if (placeRepository.count() > 0) return;

            List<Place> places = List.of(
                    Place.builder().name("Teleférico Quito").description("Una atracción que ofrece vistas panorámicas de Quito.").latitude(-0.1764).longitude(-78.5057).averageRating(4.6).build(),
                    Place.builder().name("Plaza Grande").description("La plaza principal de Quito, rodeada de edificios históricos.").latitude(-0.2195).longitude(-78.5121).averageRating(4.6).build(),
                    Place.builder().name("Museo Templo del Sol Pintor Cristobal Ortega Maila").description("Museo de arte y cultura en un entorno natural.").latitude(-0.0197).longitude(-78.4485).averageRating(4.6).build(),
                    Place.builder().name("Parque Itchimbía").description("Parque con vistas panorámicas de la ciudad y un centro de convenciones.").latitude(-0.2114).longitude(-78.4975).averageRating(4.6).build(),
                    Place.builder().name("Mitad del Mundo").description("Monumento y complejo cultural que marca la línea ecuatorial.").latitude(0.0000).longitude(-78.4550).averageRating(4.7).build(),
                    Place.builder().name("El Mirador De Guapulo").description("Un mirador que ofrece vistas impresionantes del valle de Guapulo.").latitude(-0.2078).longitude(-78.4750).averageRating(4.6).build(),
                    Place.builder().name("Wikiri Sapoparque").description("Un centro de exhibición de anfibios y reptiles.").latitude(-0.2800).longitude(-78.5500).averageRating(4.9).build(),
                    Place.builder().name("Laguna del parque La Carolina").description("Una laguna recreativa dentro de uno de los parques más grandes de Quito.").latitude(-0.1878).longitude(-78.4870).averageRating(4.7).build()
            );

            // Luego de guardar los lugares, insertamos ratings
            ratingRepository.saveAll(List.of(
                    Rating.builder().placeId(places.get(0).getId()).userId("init-user").score(4).comment("¡Excelente lugar para disfrutar de vistas increíbles!").build(),
                    Rating.builder().placeId(places.get(1).getId()).userId("init-user").score(4).comment("Un lugar hermoso y lleno de historia, perfecto para pasear.").build(),
                    Rating.builder().placeId(places.get(2).getId()).userId("init-user").score(5).comment("Una experiencia artística única en un entorno impresionante.").build(),
                    Rating.builder().placeId(places.get(3).getId()).userId("init-user").score(5).comment("Ideal para disfrutar de la naturaleza y vistas espectaculares de Quito.").build(),
                    Rating.builder().placeId(places.get(4).getId()).userId("init-user").score(4).comment("¡Una visita obligada para estar en ambos hemisferios a la vez!").build(),
                    Rating.builder().placeId(places.get(5).getId()).userId("init-user").score(5).comment("Las vistas son simplemente espectaculares, especialmente al atardecer.").build(),
                    Rating.builder().placeId(places.get(6).getId()).userId("init-user").score(4).comment("Un lugar fascinante para aprender sobre la vida silvestre local.").build(),
                    Rating.builder().placeId(places.get(7).getId()).userId("init-user").score(4).comment("Un lugar tranquilo y agradable para disfrutar de un paseo en bote o relajarse.").build()
            ));

            System.out.println("Datos iniciales de lugares y calificaciones insertados.");
        };
    }
}

