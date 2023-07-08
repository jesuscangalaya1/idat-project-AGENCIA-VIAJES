package edu.idat.pe.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private int id;
    private String nombre;
    private String nombreUsuario;
    private String email;
    Set<RolResponse> roles = new HashSet<>();

}
