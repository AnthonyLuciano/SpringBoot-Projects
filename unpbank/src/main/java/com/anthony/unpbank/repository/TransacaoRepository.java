package com.anthony.unpbank.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthony.unpbank.model.Transacao;
import com.anthony.unpbank.model.Usuario;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByUsuario(Usuario usuario);
}
