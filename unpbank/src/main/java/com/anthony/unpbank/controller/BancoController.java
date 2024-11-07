package com.anthony.unpbank.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.anthony.unpbank.model.Transacao;
import com.anthony.unpbank.model.Usuario;
import com.anthony.unpbank.service.BancoService;

@Controller
@SessionAttributes("usuarioLogado")
@RequestMapping("/banco")
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @ModelAttribute("usuarioLogado")
    public Usuario UsuarioLogado() {
        return null; // Inicializa como nulo
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna a página de login
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String nome, @RequestParam String senha,
                             @ModelAttribute("usuarioLogado") Usuario usuarioLogado,
                             Model model) {
        Usuario usuario = bancoService.autenticar(nome, senha);
        if (usuario != null) {
            model.addAttribute("usuarioLogado", usuario); // Armazena na sessão
            return "redirect:/banco/home"; // Redireciona para a página inicial
        }
        model.addAttribute("erro", "Usuário ou senha inválidos");
        return "login"; // Retorna à página de login em caso de erro
    }

    @GetMapping("/home")
public String home(@ModelAttribute("usuarioLogado") Usuario usuarioLogado, Model model) {
    if (usuarioLogado == null) {
        return "redirect:/banco/login"; // Redireciona se não estiver logado
    }
    
    Double saldo = bancoService.calcularSaldo(usuarioLogado);
    model.addAttribute("saldo", saldo); // Adiciona o saldo ao modelo
    return "home"; // Retorna a página inicial do sistema
}

    @PostMapping("/depositar")
    public String depositar(@ModelAttribute("usuarioLogado") Usuario usuarioLogado, @RequestParam Double valor) {
        if (usuarioLogado != null && valor > 0) {
            bancoService.depositar(usuarioLogado, valor);
        }
        return "redirect:/banco/home"; // Redireciona após depósito
    }

    @PostMapping("/sacar")
    public String sacar(@ModelAttribute("usuarioLogado") Usuario usuarioLogado, @RequestParam Double valor) {
        if (usuarioLogado != null && valor > 0) {
            bancoService.sacar(usuarioLogado, valor);
        }
        return "redirect:/banco/home"; // Redireciona após saque
    }

    @GetMapping("/extrato")
    public String extrato(@ModelAttribute("usuarioLogado") Usuario usuarioLogado, Model model) {
        if (usuarioLogado == null) {
            return "redirect:/banco/login"; // Redireciona se não estiver logado
        }
        List<Transacao> transacoes = bancoService.extrato(usuarioLogado);
        model.addAttribute("transacoes", transacoes);
        return "extrato"; // Retorna a página de extrato
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("usuarioLogado") Usuario usuarioLogado) {
        // Limpa o usuário logado
        return "redirect:/banco/login"; // Redireciona para a página de login
    }
}