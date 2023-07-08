package edu.idat.pe.project.persistence.repositories;

import edu.idat.pe.project.persistence.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
