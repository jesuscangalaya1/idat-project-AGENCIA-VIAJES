package edu.idat.pe.project.service.impl;

import edu.idat.pe.project.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import edu.idat.pe.project.dto.request.ItineraryRequest;
import edu.idat.pe.project.dto.response.ItineraryResponse;
import edu.idat.pe.project.dto.response.LocationResponse;
import edu.idat.pe.project.dto.response.OriginResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.persistence.entities.ItineraryEntity;
import edu.idat.pe.project.persistence.entities.LocationEntity;
import edu.idat.pe.project.persistence.entities.OriginEntity;
import edu.idat.pe.project.persistence.repositories.ItineraryRepository;
import edu.idat.pe.project.persistence.repositories.LocationRepository;
import edu.idat.pe.project.persistence.repositories.OriginRepository;
import edu.idat.pe.project.reports.imports.HelperImportExcel;
import edu.idat.pe.project.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final LocationRepository locationRepository;
    private final OriginRepository originRepository;

    @Transactional
    @Override
    public void save(MultipartFile file) {
        try {
            List<ItineraryRequest> itineraries = HelperImportExcel.convertExcelToListOfItinerary(file.getInputStream());

            List<ItineraryEntity> itineraryEntities = new ArrayList<>();

            for (ItineraryRequest itineraryRequest : itineraries) {
                ItineraryEntity itineraryEntity = new ItineraryEntity();
                itineraryEntity.setDepartureDate(itineraryRequest.getDepartureDate());
                itineraryEntity.setArrivalDate(itineraryRequest.getArrivalDate());
                itineraryEntity.setHour(itineraryRequest.getHour());

                // Obtener el objeto LocationEntity correspondiente al locationId
                Optional<LocationEntity> locationEntityOptional = locationRepository.findById(itineraryRequest.getLocationId());
                if (locationEntityOptional.isEmpty()) {
                    // Manejar la situación si no se encuentra la ubicación por el ID
                    throw new IllegalArgumentException("LocationEntity not found for id: " + itineraryRequest.getLocationId());
                }
                itineraryEntity.setLocation(locationEntityOptional.get());

                // Obtener el objeto OriginEntity correspondiente al originId
                Optional<OriginEntity> originEntityOptional = originRepository.findById(itineraryRequest.getOriginId());
                if (originEntityOptional.isEmpty()) {
                    // Manejar la situación si no se encuentra el origen por el ID
                    throw new IllegalArgumentException("OriginEntity not found for id: " + itineraryRequest.getOriginId());
                }
                itineraryEntity.setOrigin(originEntityOptional.get());

                itineraryEntities.add(itineraryEntity);
            }

            itineraryRepository.saveAll(itineraryEntities);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PageableResponse<ItineraryResponse> pageableItineraries(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<ItineraryEntity> products = itineraryRepository.findAllByDeletedFalse(pageable);

        List<ItineraryEntity> listProducts = products.getContent();
        List<ItineraryResponse> contenido = listProducts.stream()
                .map(itinerary -> {
                    ItineraryResponse response = new ItineraryResponse();
                    response.setId(itinerary.getId());
                    response.setDepartureDate(itinerary.getDepartureDate().toString());
                    response.setArrivalDate(itinerary.getArrivalDate().toString());
                    response.setHour(itinerary.getHour());
                    response.setOrigin(mapOrigin(itinerary.getOrigin())); // Mapea el origen a OriginResponse
                    response.setLocation(mapLocation(itinerary.getLocation())); // Mapea la ubicación a LocationResponse
                    return response;
                })
                .toList();

        if (contenido.isEmpty()) {
            throw new BusinessException("P-204", HttpStatus.NO_CONTENT, "Lista Vaciá de Productos");
        }

        PageableResponse<ItineraryResponse> pageItineraryResponse = new PageableResponse<>();
        pageItineraryResponse.setContent(contenido);
        pageItineraryResponse.setPageNumber(products.getNumber());
        pageItineraryResponse.setPageSize(products.getSize());
        pageItineraryResponse.setTotalElements(products.getTotalElements());
        pageItineraryResponse.setTotalPages(products.getTotalPages());
        pageItineraryResponse.setLast(products.isLast());
        return pageItineraryResponse;
    }

    @Override
    public List<ItineraryResponse> listItineraries() {
        //List<ItineraryEntity> itineraries = itineraryRepository.findAllByDeletedFalse(); // Ejemplo: obtén los itinerarios desde el repositorio
/*
        List<ItineraryResponse> itineraryResponses = itineraries.stream()
                .map(itinerary -> {
                    ItineraryResponse response = new ItineraryResponse();
                    response.setId(itinerary.getId());
                    response.setDepartureDate(itinerary.getDepartureDate().toString());
                    response.setArrivalDate(itinerary.getArrivalDate().toString());
                    response.setHour(itinerary.getHour());
                    response.setOrigin(mapOrigin(itinerary.getOrigin())); // Mapea el origen a OriginResponse
                    response.setLocation(mapLocation(itinerary.getLocation())); // Mapea la ubicación a LocationResponse
                    return response;
                })
                .collect(Collectors.toList());*/

        return null;
    }

    private OriginResponse mapOrigin(OriginEntity origin) {
        OriginResponse originResponse = new OriginResponse();
        originResponse.setId(origin.getId());
        originResponse.setCity(origin.getCity());
        originResponse.setCountry(origin.getCountry());
        originResponse.setAirport(origin.getAirport());
        return originResponse;
    }

    private LocationResponse mapLocation(LocationEntity location) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(location.getId());
        locationResponse.setCity(location.getCity());
        locationResponse.setCountry(location.getCountry());
        locationResponse.setAirport(location.getAirport());
        return locationResponse;
    }






}
