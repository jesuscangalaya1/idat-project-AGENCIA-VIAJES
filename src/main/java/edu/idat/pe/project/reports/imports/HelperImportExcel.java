package edu.idat.pe.project.reports.imports;

import edu.idat.pe.project.dto.request.ItineraryRequest;
import edu.idat.pe.project.dto.response.ItineraryResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HelperImportExcel {

    //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        assert contentType != null;
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    }

    //convert excel to list of products
    public static List<ItineraryRequest> convertExcelToListOfItinerary(InputStream is) {
        List<ItineraryRequest> list = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("data");

            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                ItineraryRequest itinerary = new ItineraryRequest();

                for (Cell cell : row) {
                    int columnIndex = cell.getColumnIndex();

                    switch (columnIndex) {
                        case 0 -> itinerary.setDepartureDate(cell.getStringCellValue());
                        case 1 -> itinerary.setArrivalDate(cell.getStringCellValue());
                        case 2 -> itinerary.setHour(cell.getStringCellValue());
                        case 3 -> itinerary.setLocationId((long) cell.getNumericCellValue());
                        case 4 -> itinerary.setOriginId((long) cell.getNumericCellValue());
                        default -> {
                        }
                    }
                }

                list.add(itinerary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }






}
