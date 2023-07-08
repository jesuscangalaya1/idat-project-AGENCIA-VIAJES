package edu.idat.pe.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {

    private int capacity;

    @DateTimeFormat(pattern = "HH:mm")
    private String duration;

    private Double price;

    private String image;

    @DateTimeFormat(pattern = "HH:mm")
    private String departureTime;

    private Long itineraryId;
}
