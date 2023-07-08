package edu.idat.pe.project.service.impl;

import edu.idat.pe.project.dto.response.CountryResponse;
import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.persistence.repositories.CountrysRepository;
import edu.idat.pe.project.service.CountrysService;
import edu.idat.pe.project.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountrysServiceImpl implements CountrysService {

    private final CountrysRepository countrysRepository;

    @Cacheable(value = {"itinerario", "vuelo", "origen", "destino"})
    @Transactional(readOnly = true)
    @Override
    public PageableResponse<FlightResponse> getFlights(Integer pageNumber, Integer pageSize, String origen, String destino, String fechaIda, String fechaVuelta) {
        PaginationUtils.validatePaginationParameters(pageNumber, pageSize);

        // Obtener el total de elementos
        long totalElements = countrysRepository.getCountOfFlights(origen,  destino,  fechaIda,  fechaVuelta);

        // Calcular información de paginación
        int offset = PaginationUtils.calculateOffset(pageNumber, pageSize);

        // Obtener los productos paginados
        List<FlightResponse> flights = countrysRepository.findByOriginAndLocation(offset, pageSize, origen,  destino,  fechaIda,  fechaVuelta);

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


    @Cacheable(value = "origen")
    @Transactional(readOnly = true)
    @Override
    public List<CountryResponse> getAllOrigins() {
        return countrysRepository.getAllOrigins();
    }

    @Cacheable(value = "destino")
    @Transactional(readOnly = true)
    @Override
    public List<CountryResponse> getAllLocations() {
        return countrysRepository.getAllLocations();
    }

    @Cacheable(value ={"origen", "destino"})
    @Transactional(readOnly = true)
    @Override
    public List<CountryResponse> searchOriginsAndDestinations(String searchTerm) {
        return countrysRepository.searchOriginsAndDestinations(searchTerm);
    }


}
