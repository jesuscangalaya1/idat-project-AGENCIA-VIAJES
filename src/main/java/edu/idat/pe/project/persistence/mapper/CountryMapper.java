package edu.idat.pe.project.persistence.mapper;

import edu.idat.pe.project.dto.response.CountryResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<CountryResponse> {

    @Override
    public CountryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CountryResponse(
                rs.getLong("id"),
                rs.getString("ciudad"),
                rs.getString("pais"),
                rs.getString("aeropuerto")
        );
    }
}
