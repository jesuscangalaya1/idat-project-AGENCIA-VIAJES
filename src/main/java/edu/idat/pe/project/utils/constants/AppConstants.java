package edu.idat.pe.project.utils.constants;

import java.util.List;

public final class AppConstants {

    private AppConstants(){}

    // CLIENT ERRORS
    public static final String BAD_REQUEST = "P-400";
    public static final String BAD_REQUEST_FLIGHT = "El Id del FLIGHT no existe ";
    public static final String BAD_REQUEST_CATEGORY = "El Id de la Categoria no existe ";

    //MESSAGE CONTROLLER
    public static final String SUCCESS = "SUCCESS";
    public static final String MESSAGE_ID_FLIGHT = "FLIGHT ID: ";
    public static final String MESSAGE_ID_CATEGORY = "CATEGORY ID: ";


    // =============================================================================================
    // CONSTANTES DE PAGINATION
    // =============================================================================================
    public static final String NUMERO_DE_PAGINA_POR_DEFECTO = "0";
    public static final String MEDIDA_DE_PAGINA_POR_DEFECTO = "10";
    public static final String ORDENAR_POR_DEFECTO = "id";
    public static final String ORDENAR_DIRECCION_POR_DEFECTO = "asc";

    // =============================================================================================
    // TIPOS DE FORMATOS DE ARCHIVOS
    // =============================================================================================
    /** FORMATO_ARCHIVOS */
    public static final String FORMAT_EXCEL = ".xlsx";
    public static final String FORMAT_PDF = ".pdf";
    public static final String FORMATO_EXCEL_ABREVIATURA = "EXCEL";
    public static final String FORMATO_PDF_ABREVIATURA = "PDF";
    public static final List<String> ARRAY_FORMATO = List.of(FORMATO_EXCEL_ABREVIATURA,FORMATO_PDF_ABREVIATURA);

    /** ERROR_REPORTE */
    public static final String ERROR_REPORTE = "Ocurrió un error al generar el reporte";

    // =============================================================================================
    // TIPOS DE FORMATOS DE ARCHIVOS
    // =============================================================================================
    /** FORMATO_ARCHIVOS */
    public static final String SHEET_FLIGHT = "Lista De Vuelos";
    public static final String VC_EMTY = "-";
    // =============================================================================================
    // NOMBRE DE REPORTES Y COLUMNAS DE 'PRODUCTOS'
    // =============================================================================================
    public static final String REPORT_NAME_FLIGHT_PAGINABLE = "report-vuelos";
    // Constantes para las columnas de la hoja de cálculo
    public static final String COL_FLIGHT_ID = "ID";
    public static final String COL_FLIGHT_CAPACITY = "CAP";
    public static final String COL_FLIGHT_DURATION = "DUR";
    public static final String COL_FLIGHT_PRICE = "PRECIO";
    public static final String COL_FLIGHT_DEPARTURE_TIME = "HOR_SAL";
    public static final String COL_ITINERARIO_FECHA_IDA = "FEC_IDA";
    public static final String COL_ITINERARIO_FECHA_SALIDA = "FEC_SAL";
    public static final String COL_ORIGEN_CIUDAD = "CIU_ORIG";
    public static final String COL_ORIGEN_PAIS = "PAIS_ORIG";
    public static final String COL_ORIGEN_AEROPUERTO = "AER_ORIG";
    public static final String COL_DESTINO_CIUDAD = "CIU_DEST";
    public static final String COL_DESTINO_PAIS = "PAIS_DEST";
    public static final String COL_DESTINO_AEROPUERTO = "AER_DEST";


}

