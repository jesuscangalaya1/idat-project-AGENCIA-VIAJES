package edu.idat.pe.project.security.util;

import edu.idat.pe.project.security.entity.Rol;
import edu.idat.pe.project.security.enums.RolNombre;
import edu.idat.pe.project.security.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 *
 {
    "nombre": "admin",
    "nombreUsuario": "admin",
    "email": "jesuscangalaya2@gmail.com",
    "password": "admin",
    "roles": ["ADMIN"]
  }
 */

@Component
@RequiredArgsConstructor
public class CreateRoles implements CommandLineRunner {

    private final RolService rolService;

    @Override
    public void run(String... args) {


/*


         Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
         Rol rolUser = new Rol(RolNombre.ROLE_USER);
         rolService.save(rolAdmin);
         rolService.save(rolUser);


*/



    }
}
