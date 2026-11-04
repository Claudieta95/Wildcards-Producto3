package com.example.helloworld.controller;

import com.example.helloworld.repository.LibroRepository;
import com.example.helloworld.repository.PrestamoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZonaUsuarioController {

    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public ZonaUsuarioController(
            LibroRepository libroRepository,
            PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/user/inicio")
    public String inicio() {
        return "user/inicio";
    }

    @GetMapping("/user/libros")
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "user/libros";
    }

    @GetMapping("/user/prestamos")
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "user/prestamos";
    }
}
