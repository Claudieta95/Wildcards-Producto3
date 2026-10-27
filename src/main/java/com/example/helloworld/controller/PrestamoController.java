
package com.example.helloworld.controller;

import com.example.helloworld.repository.PrestamoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrestamoController {

    private final PrestamoRepository prestamoRepository;

    public PrestamoController(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/prestamos")
    public String listarPrestamos(Model model) {

        model.addAttribute("prestamos", prestamoRepository.findAll());

        return "prestamos";
    }
}
