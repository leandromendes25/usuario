package com.leandromendes25.usuario.infrastructure.repository;

import com.leandromendes25.usuario.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
