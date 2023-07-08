package edu.idat.pe.project.persistence.repositories;

import edu.idat.pe.project.dto.response.CountryResponse;
import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.persistence.mapper.CountryMapper;
import edu.idat.pe.project.persistence.mapper.FlightMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CountrysRepository {

    private final JdbcTemplate jdbcTemplate;

    public long getCountOfFlights(String origen, String destino, String fechaIda, String fechaVuelta) {
        String countQuery = """
        SELECT COUNT(*) FROM vuelo v
        JOIN ITINERARIO i ON v.itinerario_id = i.id AND i.deleted = false
        JOIN DESTINO o ON i.origen_id = o.id AND o.deleted = false
        JOIN DESTINO d ON i.destino_id = d.id AND d.deleted = false
        WHERE v.deleted = false
        AND (LOWER(o.ciudad) = LOWER(?) OR LOWER(o.pais) = LOWER(?))
        AND (LOWER(d.ciudad) = LOWER(?) OR LOWER(d.pais) = LOWER(?))
        AND i.fecha_ida BETWEEN ? AND ?
        """;

        return jdbcTemplate.queryForObject(countQuery, Long.class, origen, destino, origen, destino, fechaIda, fechaVuelta);
    }


    public List<FlightResponse> findByOriginAndLocation(Integer offset, Integer pageSize, String origen,
                                                        String destino, String fechaIda, String fechaVuelta) {
        String query = """
                SELECT v.id, v.capacidad, v.duracion, v.precio, v.imagen, v.hora_salida,
                       i.id AS itinerario_id, i.fecha_ida, i.fecha_salida, i.hora,
                       o.id AS origen_id, o.ciudad AS origen_ciudad, o.pais AS origen_pais, o.aeropuerto AS origen_aeropuerto,
                       d.id AS destino_id, d.ciudad AS destino_ciudad, d.pais AS destino_pais, d.aeropuerto AS destino_aeropuerto
                FROM vuelo v
                JOIN ITINERARIO i ON v.itinerario_id = i.id AND i.deleted = false
                JOIN DESTINO o ON i.origen_id = o.id AND o.deleted = false
                JOIN DESTINO d ON i.destino_id = d.id AND d.deleted = false
                WHERE v.deleted = false
                  AND (LOWER(o.ciudad) = LOWER(?) OR LOWER(o.pais) = LOWER(?))
                  AND (LOWER(d.ciudad) = LOWER(?) OR LOWER(d.pais) = LOWER(?))
                  AND i.fecha_ida BETWEEN ? AND ?
  
                          """;


        return jdbcTemplate.query(query, new Object[]{origen, destino, origen, destino, fechaIda, fechaVuelta}, new FlightMapper());
    }



    public List<CountryResponse> getAllOrigins() {
        String query = "SELECT DISTINCT id, ciudad, pais, aeropuerto FROM origen";
        return jdbcTemplate.query(query, new CountryMapper());
    }

    public List<CountryResponse> getAllLocations() {
        String query = "SELECT DISTINCT id, ciudad, pais, aeropuerto FROM destino";
        return jdbcTemplate.query(query, new CountryMapper());
    }


    public List<CountryResponse> searchOriginsAndDestinations(String searchTerm) {
        String query = "SELECT id, CONCAT(ciudad, ', ', pais) AS location, aeropuerto " +
                       "FROM origen " +
                       "WHERE LOWER(ciudad) LIKE LOWER(?) OR LOWER(pais) LIKE LOWER(?) " +
                       "UNION " +
                       "SELECT id, CONCAT(ciudad, ', ', pais) AS location, aeropuerto " +
                       "FROM destino " +
                       "WHERE LOWER(ciudad) LIKE LOWER(?) OR LOWER(pais) LIKE LOWER(?)";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String location = rs.getString("location");
            String airport = rs.getString("aeropuerto");
            String[] locationParts = location.split(", ");
            String city = locationParts[0];
            String country = locationParts[1];
            return new CountryResponse(id, city, country, airport);
        }, "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%");
    }




}
