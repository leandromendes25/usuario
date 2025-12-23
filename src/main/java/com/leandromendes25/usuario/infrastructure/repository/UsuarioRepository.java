package com.leandromendes25.usuario.infrastructure.repository;

import com.leandromendes25.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
    @Transactional
    void deleteByEmail(String email);
}
