package com.example.helloworld.controller;

import com.example.helloworld.model.Libro;
import com.example.helloworld.repository.LibroRepository;
import com.example.helloworld.repository.PrestamoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibroController {

    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public LibroController(LibroRepository libroRepository,
                           PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/libros")
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "libros";
    }

    @GetMapping("/libros/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "formulario-libro";
    }

    @GetMapping("/libros/editar/{id}")
    public String editarLibro(@PathVariable Long id, Model model) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Libro no encontrado: " + id));

        model.addAttribute("libro", libro);
        return "formulario-libro";
    }

    @PostMapping("/libros/guardar")
    public String guardarLibro(@ModelAttribute Libro libro) {
        libroRepository.save(libro);
        return "redirect:/libros";
    }

    @PostMapping("/libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {

        if (prestamoRepository.existsByLibro_Id(id)) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se puede eliminar el libro porque tiene préstamos asociados."
            );
            return "redirect:/libros";
        }

        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Libro eliminado correctamente."
            );
        }

        return "redirect:/libros";
    }
}