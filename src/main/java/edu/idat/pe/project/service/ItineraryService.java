package edu.idat.pe.project.service;

import edu.idat.pe.project.dto.response.ItineraryResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItineraryService {

    void save(MultipartFile file);

    PageableResponse<ItineraryResponse> pageableItineraries(int numeroDePagina, int medidaDePagina,
                                                            String ordenarPor, String sortDir);


    List<ItineraryResponse> listItineraries();
}
