package com.alura.literatura.repository;

import com.alura.literatura.model.Autores;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT l FROM Libro l JOIN FETCH l.autores WHERE l.titulo LIKE %:titulo%")
    List<Libro> findByTituloContainsIgnoreCase(@Param("titulo") String nombreLibro);

    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();

    @Query("SELECT a FROM Autores a")
    List<Autores> findAllAutores();

    @Query("SELECT a FROM Autores a WHERE :ano BETWEEN a.anoNacimiento AND a.anoMuerte")
    List<Autores> findAutoresByAno(@Param("ano") Integer ano);

    @Query("SELECT l FROM Libro l WHERE l.idioma IN :idiomas ORDER BY FIELD(l.idioma, :idiomas)")
    List<Libro> findAllByOrderByIdiomas(@Param("idiomas") List<String> idiomas);
//    @EntityGraph(attributePaths = {"autores", "idioma"})
//    List<Libro> findByIdioma(String idioma);
//    @Query("SELECT l FROM Libro l JOIN FETCH l.autores WHERE :idioma MEMBER OF l.idioma")
//    List<Libro> findLibrosByIdioma(@Param("idioma") String idioma);
//    @Query("SELECT l FROM Libro l JOIN FETCH l.autores WHERE l.idioma LIKE %:idioma%")
//    List<Libro> findLibrosByIdioma(@Param("idioma") String idioma);


}
