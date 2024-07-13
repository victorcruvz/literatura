package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ElementCollection
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Autores> autores;
//    @ElementCollection
//    @CollectionTable(name = "idiomas", joinColumns = @JoinColumn(name = "libro_id"))
//    @Column(name = "idioma")
    private List<String> idioma;
    private Double numeroDescargas;

    public Libro(){}
    public Libro(DatosLibro datosLibro){
        this.id = datosLibro.id();
        this.titulo = datosLibro.titulo();
        this.autores = datosLibro.autores().stream()
                .map(da -> new Autores(da.nombreAutor(), da.anoNacimiento(), da.anoMuerte(), this))
                .collect(Collectors.toList());
        this.idioma = datosLibro.idioma();
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autores> getAutores() {
        return autores;
    }
    public void setAutores(List<Autores> autores) {
        autores.forEach(e->e.setLibro(this));
        this.autores = autores;
    }

    public List<String> getIdioma() {
        return idioma;
    }
    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }
    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Titulo='" + titulo + '\'' +
                " " + autores + '\'' +
                ", Idioma='" + idioma + '\'' +
                ", Numero de Descargas= " + numeroDescargas + '\'';
    }
}