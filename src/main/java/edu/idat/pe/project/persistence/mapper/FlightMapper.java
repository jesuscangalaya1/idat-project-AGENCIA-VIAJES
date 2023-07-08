package edu.idat.pe.project.persistence.mapper;

import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.ItineraryResponse;
import edu.idat.pe.project.dto.response.LocationResponse;
import edu.idat.pe.project.dto.response.OriginResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FlightMapper implements RowMapper<FlightResponse> {

    @Override
    public FlightResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(rs.getLong("id"));
        flightResponse.setCapacity(rs.getInt("capacidad"));
        flightResponse.setDuration(rs.getString("duracion"));
        flightResponse.setPrice(rs.getDouble("precio"));
        flightResponse.setImage(rs.getString("imagen"));
        flightResponse.setDepartureTime(rs.getString("hora_salida"));

        ItineraryResponse itineraryResponse = new ItineraryResponse();
        itineraryResponse.setId(rs.getLong("itinerario_id"));
        itineraryResponse.setDepartureDate(rs.getString("fecha_ida"));
        itineraryResponse.setArrivalDate(rs.getString("fecha_salida"));
        itineraryResponse.setHour(rs.getString("hora"));

        OriginResponse originResponse = new OriginResponse();
        originResponse.setId(rs.getLong("origen_id"));
        originResponse.setCity(rs.getString("origen_ciudad"));
        originResponse.setCountry(rs.getString("origen_pais"));
        originResponse.setAirport(rs.getString("origen_aeropuerto"));
        itineraryResponse.setOrigin(originResponse);

        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(rs.getLong("destino_id"));
        locationResponse.setCity(rs.getString("destino_ciudad"));
        locationResponse.setCountry(rs.getString("destino_pais"));
        locationResponse.setAirport(rs.getString("destino_aeropuerto"));
        itineraryResponse.setLocation(locationResponse);

        flightResponse.setItinerary(itineraryResponse);

        return flightResponse;

    }




}
