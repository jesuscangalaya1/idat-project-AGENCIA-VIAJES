package edu.idat.pe.project.service.impl;

import edu.idat.pe.project.dto.request.PurchaseRequest;
import edu.idat.pe.project.dto.response.FlightResponse;
import edu.idat.pe.project.dto.response.PurchaseResponse;
import edu.idat.pe.project.exceptions.ResourceNotFoundException;
import edu.idat.pe.project.persistence.entities.FlightEntity;
import edu.idat.pe.project.persistence.entities.PurchaseEntity;
import edu.idat.pe.project.persistence.repositories.FlightRepository;
import edu.idat.pe.project.persistence.repositories.PurchaseRepository;
import edu.idat.pe.project.security.entity.Usuario;
import edu.idat.pe.project.security.service.UsuarioService;
import edu.idat.pe.project.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UsuarioService usuarioService;
    private final FlightRepository flightRepository;

    @Transactional
    @CacheEvict(value = "compras", allEntries = true)
    @Override
    public void purchaseFlight(PurchaseRequest purchaseRequest) {
        // Crear una instancia de PurchaseEntity utilizando los datos de PurchaseRequest
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setAmount(purchaseRequest.getAmount());
        purchase.setPrice(purchaseRequest.getPrice());
        // Calcular el total multiplicando la cantidad por el precio
        double total = purchaseRequest.getAmount() * purchaseRequest.getPrice();
        purchase.setTotal(total);
        purchase.setPurchaseDate(LocalDate.now());


        // Obtener el nombre de usuario del usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();

        // Obtener el objeto Usuario utilizando el nombre de usuario
        Optional<Usuario> optionalUsuario = usuarioService.getByNombreUsuario(nombreUsuario);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Establecer el usuario asociado a la compra utilizando el objeto Usuario completo
            purchase.setUsuario(usuario);

            // Obtener el producto asociado a la compra utilizando el ID proporcionado
            FlightResponse optionalProduct = flightRepository.getByIdFlight(purchaseRequest.getFlightId());
            if (optionalProduct != null) {
                FlightEntity product = mapFlightResponseToEntity(optionalProduct);

                // Agregar el producto a la lista de productos asociados a la compra
                purchase.getFlights().add(product);

                // Guardar la compra en la base de datos
                purchaseRepository.save(purchase);
            } else {
                throw new ResourceNotFoundException("El vuelo no existe");
            }
        }
    }

    private FlightEntity mapFlightResponseToEntity(FlightResponse flightResponse) {
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setId(flightResponse.getId());
        flightEntity.setCapacity(flightResponse.getCapacity());
        flightEntity.setDuration(flightResponse.getDuration());
        flightEntity.setPrice(flightResponse.getPrice());
        flightEntity.setImage(flightResponse.getImage());
        flightEntity.setDepartureTime(flightResponse.getDepartureTime());
        // Mapear otros atributos según corresponda

        return flightEntity;
    }

    @Cacheable(value = "vuelo")
    @Transactional(readOnly = true)
    @Override
    public List<PurchaseResponse> getCustomerPurchases() {
        // Obtener el nombre de usuario del usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();

        // Obtener las compras asociadas al nombre de usuario desde el repositorio
        List<PurchaseEntity> purchases = purchaseRepository.findByUsuario_NombreUsuario(nombreUsuario);

        if (purchases.isEmpty()) {
            throw new ResourceNotFoundException("El usuario no tiene compras registradas");
        }

        // Mapear las compras a PurchaseResponse
        List<PurchaseResponse> purchaseResponses = new ArrayList<>();
        for (PurchaseEntity purchaseEntity : purchases) {
            PurchaseResponse purchaseResponse = new PurchaseResponse();
            purchaseResponse.setId(purchaseEntity.getId());
            purchaseResponse.setAmount(purchaseEntity.getAmount());
            purchaseResponse.setPrice(purchaseEntity.getPrice());
            purchaseResponse.setTotal(purchaseEntity.getTotal());
            purchaseEntity.setPurchaseDate(LocalDate.now()); // Establecer la fecha de compra como la fecha actual


            List<FlightResponse> flightResponses = new ArrayList<>();
            for (FlightEntity flightEntity : purchaseEntity.getFlights()) {
                FlightResponse flightResponse = new FlightResponse();
                flightResponse.setId(flightEntity.getId());
                flightResponse.setCapacity(flightEntity.getCapacity());
                flightResponse.setDuration(flightEntity.getDuration());
                flightResponse.setPrice(flightEntity.getPrice());
                flightResponse.setImage(flightEntity.getImage());
                flightResponse.setDepartureTime(flightEntity.getDepartureTime());
                // Otros atributos del vuelo que desees incluir en la respuesta

                flightResponses.add(flightResponse);
            }

            purchaseResponse.setFlights(flightResponses);
            purchaseResponses.add(purchaseResponse);
        }
        return purchaseResponses;
    }


}
