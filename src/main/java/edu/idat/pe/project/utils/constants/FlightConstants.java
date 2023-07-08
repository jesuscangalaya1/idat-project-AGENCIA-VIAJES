package edu.idat.pe.project.utils.constants;

public class FlightConstants {

    private FlightConstants() {
    }

    public static String UPDATED_FLIGHT = """
            UPDATE vuelo
            SET capacidad = ?,
                duracion = ?,
                precio = ?,
                imagen = ?,
                hora_salida = ?,
                itinerario_id = ?
            WHERE id = ?;
                        
            """;

    public static String GET_BY_ID_FLIGHT =
            """
                    SELECT f.id, f.capacidad, f.duracion, f.precio, f.imagen, f.hora_salida,
                           i.id AS itinerario_id, i.fecha_ida, i.fecha_salida, i.hora,
                           o.id AS origen_id, o.ciudad AS origen_ciudad, o.pais AS origen_pais, o.aeropuerto AS origen_aeropuerto,
                           l.id AS destino_id, l.ciudad AS destino_ciudad, l.pais AS destino_pais, l.aeropuerto AS destino_aeropuerto
                    FROM vuelo f
                    JOIN itinerario i ON f.itinerario_id = i.id AND i.deleted = false
                    JOIN origen o ON i.origen_id = o.id AND o.deleted = false
                    JOIN destino l ON i.destino_id = l.id AND l.deleted = false
                    WHERE f.id = ? AND f.deleted = false
                    """;

    public static String GET_COUNT_OF_FLIGHT =
            """
                    SELECT COUNT(*) FROM vuelo f
                    JOIN itinerario i ON f.itinerario_id = i.id
                    JOIN origen o ON i.origen_id = o.id
                    JOIN destino d ON i.destino_id = d.id
                    WHERE f.deleted = false AND i.deleted = false
                    AND o.deleted = false AND d.deleted = false
                    """;

    public static String CREATE_FLIGHT = "INSERT INTO vuelo (capacidad, duracion, precio, imagen, hora_salida, itinerario_id) " +
                                         "VALUES (?, ?, ?, ?, ?, ?)";



    public static String DELETED_BY_ID_FLIGHT_SQL = """
            UPDATE vuelo SET deleted = true WHERE id = ?;
                        
            """;
}
