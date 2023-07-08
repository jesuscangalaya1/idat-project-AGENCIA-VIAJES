package edu.idat.pe.project.service.impl;

import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.exceptions.BusinessException;
import edu.idat.pe.project.persistence.entities.FlightEntity;
import edu.idat.pe.project.persistence.repositories.FlightRepository;
import edu.idat.pe.project.reports.exports.ResourceExport;
import edu.idat.pe.project.service.FlightService;
import edu.idat.pe.project.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static edu.idat.pe.project.utils.ExportDataUtils.*;
import static edu.idat.pe.project.utils.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final ResourceExport resourceExport;
    private Path rootLocation;

    @Value("${media.location}")
    private String mediaLocation;

    @PostConstruct
    @Override
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);

    }

    @Transactional
    @CacheEvict(value = "vuelo", allEntries = true)
    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteByIdFlight(id);
    }

    @Transactional
    @CacheEvict(value = "vuelo", allEntries = true)
    @Override
    public FlightResponse createFlight(int capacity, String duration, Double price,
                                       MultipartFile image, String departureTime, Long itineraryId) {
        try {
            if (image.isEmpty()) {
                throw new BusinessException("Failed to store", HttpStatus.NOT_FOUND, "");
            }

            String filename = image.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Obtener la URL de la imagen **PONER EL "FILENAME" DE LA IMAGEN**
            return flightRepository.createflight(capacity, duration, price,
                    filename, departureTime, itineraryId);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Transactional
    @CacheEvict(value = "vuelo", allEntries = true)
    @Override
    public FlightResponse updateFlight(Long id, int capacity, String duration, Double price,
                                       MultipartFile image, String departureTime, Long itineraryId) {
        try {
            if (image.isEmpty()) {
                throw new BusinessException("Failed to store", HttpStatus.NOT_FOUND, "");
            }

            String filename = image.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Obtener la URL de la imagen **PONER EL "FILENAME" DE LA IMAGEN**
            return flightRepository.updateFlight(id, capacity, duration, price,
                    filename, departureTime, itineraryId);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public Resource loadAsResource(Long id) {
        try {
            // Obtener el nombre de archivo de la imagen desde la base de datos
            String filename = flightRepository.getFlightImageById(id);
            if (filename == null) {
                throw new BusinessException("Flight not found with ID:  " + id, HttpStatus.NOT_FOUND, "");
            }

            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessException("No se pudo leer el archivo: ", HttpStatus.BAD_REQUEST, filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }
    }

    @Cacheable(value = "vuelo")
    @Transactional(readOnly = true)
    @Override
    public PageableResponse<FlightResponse> listFlights(Integer pageNumber, Integer pageSize, Integer priceMin,
                                                        Integer priceMax, String departureDate) {
        // Validar pageNumber y pageSize utilizando PaginationUtils
        PaginationUtils.validatePaginationParameters(pageNumber, pageSize);

        // Obtener el total de elementos
        long totalElements = flightRepository.getCountOfFlights(priceMin, priceMax);

        // Calcular información de paginación
        int offset = PaginationUtils.calculateOffset(pageNumber, pageSize);

        // Obtener los productos paginados
        List<FlightResponse> flights = flightRepository.listFlights(offset, pageSize, priceMin, priceMax, departureDate);

        // Validar si la lista de productos está vacía
        PaginationUtils.validatePageContent(flights);

        // Validar si el número de página está dentro del rango válido
        int totalPages = PaginationUtils.calculateTotalPages(totalElements, pageSize);
        PaginationUtils.validatePageNumber(pageNumber, totalPages);

        // Validar el tamaño de página máximo
        int maxPageSize = 100; // Establecer el tamaño de página máximo permitido
        PaginationUtils.validatePageSize(pageSize, maxPageSize);

        // Configurar los resultados en la respuesta
        return PageableResponse.<FlightResponse>builder()
                .content(flights)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .last(pageNumber == totalPages)
                .build();

    }

    @Cacheable(value = "vuelo")
    @Transactional(readOnly = true)
    @Override
    public FlightResponse getByIdFligth(Long id) {
        return flightRepository.getByIdFlight(id);
    }


    //Implementation Patron de diseño(SOLID)
/*   Principio de responsabilidad única (SRP): El método exportDataExcel se encarga únicamente de orquestar la exportación de los datos,
     mientras que la lógica relacionada con la validación de formato,
     creación de columnas y valores, se ha separado en métodos auxiliares con responsabilidades más específicas.
    */
    @Transactional
    @Override
    public File exportDataExcel(List<FlightResponse> flightResponses, String formato) throws Exception {
        validateFormato(formato);

        List<String> sheets = Collections.singletonList(SHEET_FLIGHT);

        Map<String, List<String>> colsBySheet = createColumnsBySheetMap();
        Map<String, List<Map<String, String>>> valuesBySheet = createValuesBySheetMap(flightResponses);

        String reportName = REPORT_NAME_FLIGHT_PAGINABLE;
        if (formato.equals(FORMATO_EXCEL_ABREVIATURA)) {
            return resourceExport.generateExcel(sheets, colsBySheet, valuesBySheet, reportName);
        } else {
            return resourceExport.generatePdf(sheets, colsBySheet, valuesBySheet, reportName);
        }
    }

}
