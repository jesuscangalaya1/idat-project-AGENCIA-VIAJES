package edu.idat.pe.project.persistence.repositories;

import edu.idat.pe.project.persistence.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
    List<PurchaseEntity> findByUsuario_NombreUsuario(String nombreUsuario);

}
