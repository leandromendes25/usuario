package com.leandromendes25.usuario.infrastructure.repository;

import com.leandromendes25.usuario.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
