package com.anthony.unpbank.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthony.unpbank.model.Transacao;
import com.anthony.unpbank.model.Usuario;
import com.anthony.unpbank.repository.TransacaoRepository;
import com.anthony.unpbank.repository.UsuarioRepository;

@Service
public class BancoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;
    
    public void criarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    public Usuario autenticar(String nome, String senha) {
        Usuario usuario = usuarioRepository.findByNome(nome);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }
    public void depositar(Usuario usuario, Double valor) {
        Transacao transacao = new Transacao();
        transacao.setValor(valor);
        transacao.setTipo("DEPOSITO");
        transacao.setUsuario(usuario);
        transacaoRepository.save(transacao);
    }
    public void sacar(Usuario usuario, Double valor) {
        Transacao transacao = new Transacao();
        transacao.setValor(valor);
        transacao.setTipo("SAQUE");
        transacao.setUsuario(usuario);
        transacaoRepository.save(transacao);
    }
    public List<Transacao> extrato(Usuario usuario) {
        return transacaoRepository.findByUsuario(usuario);
    }
    public Double calcularSaldo(Usuario usuario) {
        List<Transacao> transacoes = extrato(usuario);
        double saldo = 0.0;
        for (Transacao transacao : transacoes) {
            if ("DEPOSITO".equals(transacao.getTipo())) {
                saldo += transacao.getValor();
            } else if ("SAQUE".equals(transacao.getTipo())) {
                saldo -= transacao.getValor();
            }
        }
        return saldo;
    }
}