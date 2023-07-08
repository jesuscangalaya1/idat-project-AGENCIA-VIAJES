package edu.idat.pe.project.service.impl;

import edu.idat.pe.project.dto.response.PageableResponse;
import edu.idat.pe.project.dto.response.RolResponse;
import edu.idat.pe.project.dto.response.UserResponse;
import edu.idat.pe.project.exceptions.BusinessException;
import edu.idat.pe.project.persistence.repositories.UserRepository;
import edu.idat.pe.project.security.entity.Rol;
import edu.idat.pe.project.security.entity.Usuario;
import edu.idat.pe.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public PageableResponse<UserResponse> pageableUsers(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Usuario> products = userRepository.findAll(pageable);

        List<Usuario> listProducts = products.getContent();
        List<UserResponse> contenido = listProducts.stream()
                .map(itinerary -> {
                    UserResponse response = new UserResponse();
                    response.setId(itinerary.getId());
                    response.setNombre(itinerary.getNombre());
                    response.setNombreUsuario(itinerary.getNombreUsuario());
                    response.setEmail(itinerary.getEmail());
                    Set<RolResponse> rolResponses = itinerary.getRoles().stream()
                            .map(this::mapRol)
                            .collect(Collectors.toSet());
                    response.setRoles(rolResponses);
                    return response;
                })
                .toList();

        if (contenido.isEmpty()) {
            throw new BusinessException("P-204", HttpStatus.NO_CONTENT, "Lista Vaciá ");
        }

        PageableResponse<UserResponse> pageItineraryResponse = new PageableResponse<>();
        pageItineraryResponse.setContent(contenido);
        pageItineraryResponse.setPageNumber(products.getNumber());
        pageItineraryResponse.setPageSize(products.getSize());
        pageItineraryResponse.setTotalElements(products.getTotalElements());
        pageItineraryResponse.setTotalPages(products.getTotalPages());
        pageItineraryResponse.setLast(products.isLast());
        return pageItineraryResponse;
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("p-400", HttpStatus.NOT_FOUND, "id: " + id + "no existe"));


        // Eliminar los roles asociados al usuario
        usuario.getRoles().clear();

        userRepository.delete(usuario);
    }


    //MAP DTOS ...

    private RolResponse mapRol(Rol rol) {
        RolResponse rolResponse = new RolResponse();
        rolResponse.setId(rol.getId());
        rolResponse.setRolNombre(rol.getRolNombre().name());
        return rolResponse;
    }

}
