package edu.idat.pe.project.persistence.repositories;

import edu.idat.pe.project.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Integer> {
}
