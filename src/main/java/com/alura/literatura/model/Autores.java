package com.alura.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreAutor;
    private Integer anoNacimiento;
    private Integer anoMuerte;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    public  Autores(){}
    public Autores(String nombreAutor, Integer anoNacimiento, Integer anoMuerte, Libro libro){
        this.nombreAutor=nombreAutor;
        this.anoNacimiento = anoNacimiento;
        this.anoMuerte = anoMuerte;
        this.libro = libro;
    }


    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }
    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoMuerte() {
        return anoMuerte;
    }
    public void setAnoMuerte(Integer anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    @Override
    public String toString() {
        return "Autor: " + nombreAutor + ", Año de Nacimiento: " + anoNacimiento + ", Año de Muerte: " + anoMuerte;
    }
}
