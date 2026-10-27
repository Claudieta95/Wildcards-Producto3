package com.example.helloworld.repository;

import com.example.helloworld.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    boolean existsByLibro_Id(Long libroId);
}