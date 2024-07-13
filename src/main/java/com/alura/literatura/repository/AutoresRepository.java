package com.alura.literatura.repository;

import com.alura.literatura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoresRepository extends JpaRepository<Autores, Long> {
}
