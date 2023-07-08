package edu.idat.pe.project.reports.helper;

import com.itextpdf.layout.properties.TextAlignment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelExportHelper {

    /** BOLD_FALSE */
    public static final Boolean BOLD_FALSE = false;

    /** BOLD_FALSE */
    public static final Boolean BOLD_TRUE = true;

    /**
     * Genera un excel basado en shhets
     *
     * @param sheets        Lista con los nombre de las hojas. Estos nombres serán
     *                      la key de los siguientes mapas.
     * @param colsBySheet   Mapa con las columnas de cada una de las hojas de la
     *                      lista. key=nombre de tabla.
     * @param valuesBySheet Mapa con una lista de mapas. Se obtienen los datos de
     *                      una hojas en el primer mapa a partir del nombre de la
     *                      hojas y en el segundo nivel, se obtienen los datos de
     *                      una columna a partir de su nombre
     * @param fichero       Ruta absoluta del fichero de salida donde se generara el
     *                      excel
     * @return fichero generado
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static File generateExcel(List<String> sheets, Map<String, List<String>> colsBySheet,
                                     Map<String, List<Map<String, String>>> valuesBySheet, String fichero)
            throws FileNotFoundException, IOException {

        Workbook workbook = new XSSFWorkbook();
        for (String sheetName : sheets) {
            createSheet(workbook, sheetName, colsBySheet.get(sheetName), valuesBySheet.get(sheetName));
        }
        File salida = new File(fichero);
        workbook.write(new FileOutputStream(salida));
        return salida;
    }

    private static void createSheet(Workbook workbook, String sheetName, List<String> colNames,
                                    List<Map<String, String>> values) {
        Sheet sheet = workbook.createSheet(sheetName);
        int rowIndex = 0;
        Row row = getOrCreateRow(sheet, 0);
        rowIndex++;

        CellStyle cellStyle = createHeaderCellStyle(workbook);
        int colIndex = 0;
        for (String columna : colNames) {
            getOrCreateCell(row, colIndex).setCellValue(columna);
            getOrCreateCell(row, colIndex).setCellStyle(cellStyle);
            colIndex++;
        }

        CellStyle variableCellStyle = createVariableCellStyle(workbook);

        for (Map<String, String> value : values) {
            colIndex = 0;
            row = getOrCreateRow(sheet, rowIndex);

            for (String columna : colNames) {
                if (value.containsKey(columna)) {
                    getOrCreateCell(row, colIndex).setCellValue(value.get(columna));
                } else {
                    getOrCreateCell(row, colIndex).setCellValue("");
                }
                getOrCreateCell(row, colIndex).setCellStyle(variableCellStyle);
                colIndex++;

            }
            rowIndex++;
        }
        for (int i = 0; i < colIndex; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    private static Cell getOrCreateCell(Row row, int colIdx) {
        Cell cell = row.getCell(colIdx);
        if (cell == null) {
            cell = row.createCell(colIdx);
        }

        return cell;
    }

    private static Row getOrCreateRow(Sheet sheet, int rowIdx) {
        Row row = sheet.getRow(rowIdx);
        if (row == null) {
            row = sheet.createRow(rowIdx);
        }

        return row;
    }

    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        return createCellStyle(workbook, IndexedColors.ORANGE, FillPatternType.SOLID_FOREGROUND, IndexedColors.WHITE,
                HorizontalAlignment.CENTER, BOLD_TRUE);
    }

    private static CellStyle createVariableCellStyle(Workbook workbook) {
        return createCellStyle(workbook, IndexedColors.WHITE, FillPatternType.NO_FILL, IndexedColors.BLACK,
                HorizontalAlignment.LEFT, BOLD_FALSE);
    }

    private static CellStyle createCellStyle(Workbook workbook, IndexedColors fillForegroundColor,
                                             FillPatternType fillPatternType, IndexedColors fontColor, HorizontalAlignment cellHorizontalAlignment,
                                             Boolean bold) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(fillForegroundColor.index);
        style.setFillPattern(fillPatternType);


        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setColor(fontColor.index);
        font.setBold(bold);
        style.setFont(font);
        style.setAlignment(cellHorizontalAlignment);
        style.setLocked(true);

        // Centrar el texto verticalmente
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Agregar borde a la celda
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

}

