
package com.example.helloworld.controller;

import com.example.helloworld.model.Usuario;
import com.example.helloworld.repository.LibroRepository;
import com.example.helloworld.repository.PrestamoRepository;
import com.example.helloworld.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZonaUsuarioController {

    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;

    public ZonaUsuarioController(LibroRepository libroRepository,
                                 PrestamoRepository prestamoRepository,
                                 UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
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
    public String listarPrestamos(Model model, Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        "Usuario autenticado no encontrado"
                ));

        model.addAttribute(
                "prestamos",
                prestamoRepository.findByUsuario_Id(usuario.getId())
        );

        return "user/prestamos";
    }
}
