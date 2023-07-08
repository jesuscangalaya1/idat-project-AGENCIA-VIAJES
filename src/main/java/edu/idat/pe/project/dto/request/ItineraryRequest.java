package edu.idat.pe.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryRequest {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String departureDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String arrivalDate;

    private String hour;

    private Long locationId;
    private Long originId;
}
