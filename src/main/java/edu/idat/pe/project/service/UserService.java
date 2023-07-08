package edu.idat.pe.project.service;

import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.dto.response.UserResponse;


public interface UserService {

    PageableResponse<UserResponse> pageableUsers(int numeroDePagina, int medidaDePagina,
                                                 String ordenarPor, String sortDir);

    void deleteUser(Integer id);
}
