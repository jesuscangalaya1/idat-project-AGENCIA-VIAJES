package edu.idat.pe.project.dto.response;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {

    private Long id;
    private int capacity;

    @DateTimeFormat(pattern = "HH:mm")
    private String duration;

    private Double price;

    private String image;

    @DateTimeFormat(pattern = "HH:mm")
    private String departureTime;

    private ItineraryResponse itinerary;
}
