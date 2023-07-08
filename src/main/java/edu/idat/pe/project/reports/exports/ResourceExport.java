package edu.idat.pe.project.reports.exports;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ResourceExport {

    File generateExcel(List<String> sheets, Map<String, List<String>> colsBySheet,
                       Map<String, List<Map<String, String>>> valuesBySheet, String fileName) throws Exception;

    File generatePdf(List<String> tables, Map<String, List<String>> colsByTables,
                     Map<String, List<Map<String, String>>> valuesByTable, String fileName) throws Exception;
}
