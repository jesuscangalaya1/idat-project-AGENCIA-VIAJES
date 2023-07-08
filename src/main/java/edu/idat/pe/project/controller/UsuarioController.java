package edu.idat.pe.project.controller;

import edu.idat.pe.project.dto.response.ItineraryResponse;
import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.dto.response.RestResponse;
import edu.idat.pe.project.dto.response.UserResponse;
import edu.idat.pe.project.service.UserService;
import edu.idat.pe.project.utils.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static edu.idat.pe.project.utils.constants.AppConstants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UsuarioController {

    private final UserService userService;

    @GetMapping(value = "/pageable-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<PageableResponse<UserResponse>> pageableProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        return new  RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "USERS SUCCESSFULLY READED",
                userService.pageableUsers(numeroDePagina, medidaDePagina, ordenarPor, sortDir));

    }

    @DeleteMapping("/{id}")
    public RestResponse<String> deleteFlight(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new RestResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK),
                "USER ID: " + id + " SUCCESSFULLY DELETED",
                "null"); // Data null.
    }

}
