package edu.idat.pe.project.utils;

import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.ItineraryResponse;
import edu.idat.pe.project.dto.response.LocationResponse;
import edu.idat.pe.project.dto.response.OriginResponse;
import edu.idat.pe.project.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.*;

import static edu.idat.pe.project.utils.constants.AppConstants.*;

public final class ExportDataUtils {

    private ExportDataUtils(){}

    //Validated Format
    public static void validateFormato(String formato) {
        if (!ARRAY_FORMATO.contains(formato)) {
            throw new BusinessException(String.format("%s format not allowed", formato), HttpStatus.BAD_GATEWAY, "Bad");
        }
    }

    //Mapeando Columnas
    public static Map<String, List<String>> createColumnsBySheetMap() {
        List<String> cols = Arrays.asList(
                COL_FLIGHT_ID,
                COL_FLIGHT_CAPACITY,
                COL_FLIGHT_DURATION,
                COL_FLIGHT_PRICE,
                COL_FLIGHT_DEPARTURE_TIME,
                COL_ITINERARIO_FECHA_IDA,
                COL_ITINERARIO_FECHA_SALIDA,
                COL_ORIGEN_CIUDAD,
                COL_ORIGEN_PAIS,
                COL_ORIGEN_AEROPUERTO,
                COL_DESTINO_CIUDAD,
                COL_DESTINO_PAIS,
                COL_DESTINO_AEROPUERTO
        );

        Map<String, List<String>> colsBySheet = new HashMap<>();
        colsBySheet.put(SHEET_FLIGHT, cols);
        return colsBySheet;
    }

    public static Map<String, List<Map<String, String>>> createValuesBySheetMap(List<FlightResponse> flightResponses) {
        List<Map<String, String>> valoresHoja = new ArrayList<>();

        for (FlightResponse row : flightResponses) {
            Map<String, String> valuesHojaRow = new HashMap<>();
            valuesHojaRow.put(COL_FLIGHT_ID, getStringValue(row.getId()));
            valuesHojaRow.put(COL_FLIGHT_CAPACITY, getStringValue(row.getCapacity()));
            valuesHojaRow.put(COL_FLIGHT_DURATION, getStringValue(row.getDuration()));
            valuesHojaRow.put(COL_FLIGHT_PRICE, getStringValue(row.getPrice()));
            valuesHojaRow.put(COL_FLIGHT_DEPARTURE_TIME, getStringValue(row.getDepartureTime()));

            ItineraryResponse itinerary = row.getItinerary();
            valuesHojaRow.put(COL_ITINERARIO_FECHA_IDA, itinerary != null ? getStringValue(itinerary.getArrivalDate()) : "");
            valuesHojaRow.put(COL_ITINERARIO_FECHA_SALIDA, itinerary != null ? getStringValue(itinerary.getDepartureDate()) : "");

            OriginResponse origin = itinerary != null ? itinerary.getOrigin() : null;
            valuesHojaRow.put(COL_ORIGEN_CIUDAD, origin != null ? getStringValue(origin.getCity()) : "");
            valuesHojaRow.put(COL_ORIGEN_PAIS, origin != null ? getStringValue(origin.getCountry()) : "");
            valuesHojaRow.put(COL_ORIGEN_AEROPUERTO, origin != null ? getStringValue(origin.getAirport()) : "");

            LocationResponse destination = itinerary != null ? itinerary.getLocation() : null;
            valuesHojaRow.put(COL_DESTINO_CIUDAD, destination != null ? getStringValue(destination.getCity()) : "");
            valuesHojaRow.put(COL_DESTINO_PAIS, destination != null ? getStringValue(destination.getCountry()) : "");
            valuesHojaRow.put(COL_DESTINO_AEROPUERTO, destination != null ? getStringValue(destination.getAirport()) : "");

            valoresHoja.add(valuesHojaRow);
        }

        Map<String, List<Map<String, String>>> valuesBySheet = new HashMap<>();
        valuesBySheet.put(SHEET_FLIGHT, valoresHoja);
        return valuesBySheet;
    }

    private static String getStringValue(Object value) {
        return value != null ? value.toString() : VC_EMTY;
    }
}
