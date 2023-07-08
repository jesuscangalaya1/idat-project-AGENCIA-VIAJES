package edu.idat.pe.project.utils;

import edu.idat.pe.project.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.List;

public final class PaginationUtils {

    private PaginationUtils(){}

    public static void validatePaginationParameters(int pageNumber, int pageSize) {
        if (pageNumber <= 0) {
            throw new BusinessException("C-400", HttpStatus.BAD_REQUEST, "El valor de pageNumber: " + pageNumber + " debe ser mayor que cero");
        }

        if (pageSize <= 0) {
            throw new BusinessException("C-400", HttpStatus.BAD_REQUEST, "El valor de pageSize: "+ pageSize+" debe ser mayor que cero");
        }
    }

    public static int calculateOffset(int pageNumber, int pageSize) {
        validatePaginationParameters(pageNumber, pageSize);
        return (pageNumber - 1) * pageSize;
    }

    public static int calculateTotalPages(long totalElements, int pageSize) {
        if (totalElements < 0 || pageSize <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    public static <T> void validatePageContent(List<T> content) {
        if (content == null || content.isEmpty()) {
            throw new BusinessException("C-204", HttpStatus.NO_CONTENT, "Lista vacía de elementos");
        }
    }

    public static void validatePageNumber(int pageNumber, int totalPages) {
        if (pageNumber > totalPages) {
            throw new BusinessException("C-400", HttpStatus.BAD_REQUEST, "Número de página fuera de rango");
        }
    }

    public static void validatePageSize(int pageSize, int maxPageSize) {
        if (pageSize > maxPageSize) {
            throw new BusinessException("C-400", HttpStatus.BAD_REQUEST, "Tamaño de página excede el límite máximo permitido");
        }
    }

}
