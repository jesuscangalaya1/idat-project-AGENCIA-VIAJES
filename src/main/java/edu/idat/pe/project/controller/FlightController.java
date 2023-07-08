package edu.idat.pe.project.controller;

import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.dto.response.RestResponse;
import edu.idat.pe.project.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static edu.idat.pe.project.utils.constants.AppConstants.FORMATO_EXCEL_ABREVIATURA;
import static edu.idat.pe.project.utils.constants.AppConstants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
@CrossOrigin("*")
public class FlightController {

    private final FlightService flightService;
    private final HttpServletRequest request;


    @PostMapping(value = "/create-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<FlightResponse> createFlight(@RequestParam("capacity") int capacity,
                                                     @RequestParam("duration") String duration,
                                                     @RequestParam("price") Double price,
                                                     @RequestParam("file") MultipartFile image,
                                                     @RequestParam("departureTime") String departureTime,
                                                     @RequestParam("itineraryId") Long itineraryId) {
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.CREATED),
                "FLIGHT SUCCESSFULLY CREATED",
                flightService.createFlight(capacity, duration, price, image, departureTime, itineraryId));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<FlightResponse> updatedFlight(@PathVariable Long id,
                                                      @RequestParam("capacity") int capacity,
                                                      @RequestParam("duration") String duration,
                                                      @RequestParam("price") Double price,
                                                      @RequestParam("file") MultipartFile image,
                                                      @RequestParam("departureTime") String departureTime,
                                                      @RequestParam("itineraryId") Long itineraryId) {
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "FLIGHT SUCCESSFULLY UPDATED",
                flightService.updateFlight(id, capacity, duration, price, image, departureTime, itineraryId));
    }


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<PageableResponse<FlightResponse>> getAllFlights(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "precioMinimo", required = false) Integer priceMin,
            @RequestParam(value = "precioMaximo", required = false) Integer priceMax,
            @RequestParam(value = "fechaSalida", required = false) String departureTime) {

        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "FLIGHTS SUCCESSFULLY READED",
                flightService.listFlights(pageNumber, pageSize, priceMin, priceMax, departureTime));
    }


    @GetMapping(value = "/export-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> exportsFlights(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "precioMinimo", required = false) Integer priceMin,
            @RequestParam(value = "precioMaximo", required = false) Integer priceMax,
            @RequestParam(value = "fechaSalida", required = false) String departureTime,
            @RequestParam(defaultValue = FORMATO_EXCEL_ABREVIATURA) @NotBlank String format) throws Exception {

        PageableResponse<FlightResponse> flightPage = flightService.listFlights(pageNumber, pageSize, priceMin, priceMax, departureTime);
        List<FlightResponse> flights = flightPage.getContent();
        File file = flightService.exportDataExcel(flights, format);

        // Configurar las cabeceras de la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());

        // Crear la respuesta HTTP con el objeto File
        FileSystemResource fileResource = new FileSystemResource(file);
        return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
    }


    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) throws IOException {
        Resource file = flightService.loadAsResource(id);
        String contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<FlightResponse> getFlightById(@PathVariable Long id) {
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "FLIGHT ID: " + id + " SUCCESSFULLY READED",
                flightService.getByIdFligth(id));
    }


    @DeleteMapping("/{id}")
    public RestResponse<String> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "FLIGHT ID: " + id + " SUCCESSFULLY DELETED",
                "null"); // Data null.
    }


}
