package com.anthony.unpbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthony.unpbank.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
}
