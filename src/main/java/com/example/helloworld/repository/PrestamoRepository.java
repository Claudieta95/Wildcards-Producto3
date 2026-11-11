
package com.example.helloworld.repository;

import com.example.helloworld.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    boolean existsByLibro_Id(Long libroId);

    List<Prestamo> findByUsuario_Id(Long usuarioId);
}
