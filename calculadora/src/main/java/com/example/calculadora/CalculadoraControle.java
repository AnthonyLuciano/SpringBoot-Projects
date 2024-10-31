package com.example.calculadora;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculadoraControle {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/calcular")
    public String calcular(@RequestParam double numero1, 
                           @RequestParam double numero2, 
                           @RequestParam String operacao, 
                           Model model) {
        double resultado = 0;

        switch (operacao) {
            case "adição":
                resultado = numero1 + numero2;
                break;
            case "subtração":
                resultado = numero1 - numero2;
                break;
            case "multiplicação":
                resultado = numero1 * numero2;
                break;
            case "divisão":
                if (numero2 != 0) {
                    resultado = numero1 / numero2;
                } else {
                    model.addAttribute("erro", "Divisão por zero não é permitida.");
                    return "resultado";
                }
                break;
        }

        model.addAttribute("resultado", resultado);
        return "resultado";
    }
}