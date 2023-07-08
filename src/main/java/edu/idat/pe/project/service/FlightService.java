package edu.idat.pe.project.service;

import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FlightService {

    void init() throws IOException;
    void  deleteFlight(Long id);
    PageableResponse<FlightResponse> listFlights(Integer pageNumber, Integer pageSize, Integer priceMin,
                                                 Integer priceMax, String departureDate);
    FlightResponse createFlight(int capacity, String duration, Double price,
                                MultipartFile image, String departureTime, Long itineraryId);

    FlightResponse updateFlight(Long id, int capacity, String duration, Double price,
                                       MultipartFile image, String departureTime, Long itineraryId);

    Resource loadAsResource(Long id);

    FlightResponse getByIdFligth(Long id);

    File exportDataExcel(List<FlightResponse> flightResponses, String formato) throws Exception;
}
