package edu.idat.pe.project.controller;

import edu.idat.pe.project.dto.response.CountryResponse;
import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.dto.response.RestResponse;
import edu.idat.pe.project.persistence.repositories.CountrysRepository;
import edu.idat.pe.project.service.CountrysService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static edu.idat.pe.project.utils.constants.AppConstants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countrys")
@CrossOrigin("*")
public class CountryController {

    private final CountrysService countrysService;

    @GetMapping(value = "/params", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<PageableResponse<FlightResponse>> getFlights(@RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                     @RequestParam("origen") String origen,
                                                                     @RequestParam("destino") String destino,
                                                                     @RequestParam("fechaIda") String fechaIda,
                                                                     @RequestParam("fechaVuelta") String fechaVuelta) {
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "FLIGHTS SUCCESSFULLY READED",
                countrysService.getFlights(pageNumber, pageSize, origen, destino, fechaIda, fechaVuelta));
    }

    @GetMapping(value = "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<CountryResponse>> getAllLocations() {

        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "LOCATIONS SUCCESSFULLY READED",
                countrysService.getAllLocations());
    }

    @GetMapping(value = "/origins", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<CountryResponse>> getAllOrigins() {
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "ORIGINS SUCCESSFULLY READED",
                countrysService.getAllOrigins());
    }


    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<CountryResponse>> searchOriginsAndDestinations(@RequestParam("term") String searchTerm) {
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "SEARCH SUCCESSFULLY READED",
                countrysService.searchOriginsAndDestinations(searchTerm));
    }


}
