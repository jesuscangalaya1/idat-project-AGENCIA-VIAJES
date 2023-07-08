package edu.idat.pe.project.persistence.repositories;

import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.exceptions.BusinessException;
import edu.idat.pe.project.persistence.mapper.FlightMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static edu.idat.pe.project.utils.constants.FlightConstants.*;


@Repository
@RequiredArgsConstructor
public class FlightRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ItineraryRepository itineraryRepository;

    public long getCountOfFlights(Integer priceMin, Integer priceMax) {
        String countQuery = GET_COUNT_OF_FLIGHT;

        if (priceMin != null && priceMax != null && priceMin > 0 && priceMax > 0) {
            countQuery += " AND f.precio BETWEEN " + priceMin + " AND " + priceMax;
        }

        return jdbcTemplate.queryForObject(countQuery, Long.class);
    }


    public List<FlightResponse> listFlights(Integer offset, Integer pageSize, Integer priceMin,
                                            Integer priceMax, String departureDate) {
        String selectQuery = """
                SELECT f.id, f.capacidad, f.duracion, f.precio, f.imagen, f.hora_salida,
                       i.id AS itinerario_id, i.fecha_ida, i.fecha_salida, i.hora,
                       o.id AS origen_id, o.ciudad AS origen_ciudad, o.pais AS origen_pais, o.aeropuerto AS origen_aeropuerto,
                       l.id AS destino_id, l.ciudad AS destino_ciudad, l.pais AS destino_pais, l.aeropuerto AS destino_aeropuerto
                FROM vuelo f
                JOIN itinerario i ON f.itinerario_id = i.id AND i.deleted = false
                JOIN origen o ON i.origen_id = o.id AND o.deleted = false
                JOIN destino l ON i.destino_id = l.id AND l.deleted = false
                WHERE f.deleted = false
                """;
        List<Object> parameters = new ArrayList<>();

        if (priceMin != null && priceMax != null && priceMin > 0 && priceMax > 0) {
            selectQuery += " AND f.precio BETWEEN ? AND ?";
            parameters.add(priceMin);
            parameters.add(priceMax);
        }

        if (departureDate != null && !departureDate.isEmpty()) {
            selectQuery += " AND i.fecha_salida = ?";
            parameters.add(departureDate);
        }


        if (priceMin == null && priceMax == null && (departureDate == null || departureDate.isEmpty())) {
            // Ordenar por ID si no se especifican criterios de precio o fecha
            selectQuery += " ORDER BY f.id ASC";
        } else if (priceMin != null || priceMax != null) {
            // Ordenar por precio si se especifican criterios de precio
            selectQuery += " ORDER BY f.precio ASC";
        } else {
            // Ordenar por fecha si se especifica criterio de fecha
            selectQuery += " ORDER BY i.fecha_salida ASC";
        }

        selectQuery += " LIMIT ? OFFSET ?;";
        parameters.add(pageSize);
        parameters.add(offset);

        return jdbcTemplate.query(selectQuery, parameters.toArray(), new FlightMapper());
    }


    public String getFlightImageById(Long id) {
        String query = "SELECT imagen FROM vuelo WHERE id = ?";
        return jdbcTemplate.queryForObject(query, String.class, id);
    }

    public FlightResponse createflight(int capacity, String duration, Double price,
                                       String image, String departureTime, Long itineraryId) {

        Long generatedId = executeInsertStatement(capacity, duration, price, image, departureTime, itineraryId);
        return getByIdFlight(generatedId);
    }

    public FlightResponse updateFlight(Long id, int capacity, String duration, Double price,
                                       String image, String departureTime, Long itineraryId) {
        getByIdFlight(id);
        if (!itineraryRepository.existsById(itineraryId)) {
            throw new BusinessException("P-400", HttpStatus.BAD_REQUEST, "ID: " + itineraryId + " del itinerario inválido");
        }

        jdbcTemplate.update(UPDATED_FLIGHT,
                capacity, duration, price, image, departureTime, itineraryId, id);

        return getByIdFlight(id);
    }


    public FlightResponse getByIdFlight(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException("P-400", HttpStatus.BAD_REQUEST, "Id de vuelo inválido");
        }

        List<FlightResponse> flights = jdbcTemplate.query(GET_BY_ID_FLIGHT, new FlightMapper(), id);
        if (flights.isEmpty()) {
            throw new BusinessException("P-404", HttpStatus.NOT_FOUND, "vuelo no encontrado");
        }
        return flights.get(0);
    }

    public void deleteByIdFlight(Long id) {
        // Verificar si el vuelo existe
        getByIdFlight(id);

        int rowsAffected = jdbcTemplate.update(DELETED_BY_ID_FLIGHT_SQL, id);
        if (rowsAffected == 0) {
            throw new BusinessException("P-404", HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
    }



    // Separando de CreateFlight para tener un codigo bien estructurado.
    private Long executeInsertStatement(int capacity, String duration, Double price,
                                        String image, String departureTime, Long itineraryId) {
        if (!itineraryRepository.existsById(itineraryId)) {
            throw new BusinessException("P-400", HttpStatus.BAD_REQUEST, "ID: " + itineraryId + "  del itinerario inválido");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_FLIGHT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, capacity);
            ps.setString(2, duration);
            ps.setDouble(3, price);
            ps.setString(4, String.valueOf(image));
            ps.setString(5, departureTime);
            ps.setLong(6, itineraryId);
            return ps;
        }, keyHolder);

        return (keyHolder.getKey() != null) ? keyHolder.getKey().longValue() : null;
    }


}
