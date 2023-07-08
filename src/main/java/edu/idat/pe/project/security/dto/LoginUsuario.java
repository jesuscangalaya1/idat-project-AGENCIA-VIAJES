package edu.idat.pe.project.security.dto;

import javax.validation.constraints.NotBlank;

public class LoginUsuario {

    @NotBlank(message = "nombre de usuario/email obligatorio")
    private String nombreUsuario;
    @NotBlank(message = "contraseña obligatoria")
    private String password;




    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
