package edu.idat.pe.project.security.service;

import edu.idat.pe.project.security.entity.Usuario;
import edu.idat.pe.project.security.entity.UsuarioPrincipal;
import edu.idat.pe.project.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException("ese usuario no existe"));
        return UsuarioPrincipal.build(usuario);
    }
}
