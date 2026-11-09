package com.example.helloworld.service;

import com.example.helloworld.dto.RegistroDTO;
import com.example.helloworld.model.Usuario;
import com.example.helloworld.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrarUsuario(RegistroDTO datos) {

        // Comprobar que todos los campos estén rellenados
        if (datos.getNombre() == null || datos.getNombre().isBlank()
                || datos.getApellidos() == null || datos.getApellidos().isBlank()
                || datos.getEmail() == null || datos.getEmail().isBlank()
                || datos.getPassword() == null || datos.getPassword().isBlank()) {

            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        // Comprobar la longitud de la contraseña
        if (datos.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        // Comprobar el formato del correo electrónico
        if (!datos.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido");
        }

        // Comprobar si el correo electrónico ya está registrado
        if (usuarioRepository.findByEmail(datos.getEmail()).isPresent()) {throw new IllegalArgumentException("El correo electrónico ya está registrado");}

        // Crear un nuevo usuario
        Usuario usuario = new Usuario(
                datos.getNombre(),
                datos.getApellidos(),
                datos.getEmail()
        );

        // Cifrar la contraseña y asignar el rol USER
        usuario.setPassword(passwordEncoder.encode(datos.getPassword()));
        usuario.setRol("USER");

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);
    }
}