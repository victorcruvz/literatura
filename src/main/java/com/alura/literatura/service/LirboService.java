package com.alura.literatura.service;

import com.alura.literatura.dto.LibroDto;
import com.alura.literatura.model.Autores;
import com.alura.literatura.model.DatosAutores;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.AutoresRepository;
import com.alura.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LirboService {

    @Autowired
    private LibroRepository repository;
    @Autowired
    private AutoresRepository autorRepository;

    public List<LibroDto> ObtenerLibro() {return convierteDatos(repository.findAll());}

    @Transactional
    public void registrarLibro(LibroDto libroDto) {
        Libro libro = new Libro();
        libro.setTitulo(libroDto.titulo());
        libro.setIdioma(libroDto.idioma());
        libro.setNumeroDescargas(libroDto.numeroDescargas());

        // Convertir DatosAutores a Autor y asignarlos al libro
        Autores autor = new Autores();
        DatosAutores datosAutores = libroDto.autores();
        autor.setNombreAutor(datosAutores.nombreAutor());
        autor.setAnoNacimiento(datosAutores.anoNacimiento());
        autor.setAnoMuerte(datosAutores.anoMuerte());
        autor.setLibro(libro);

        libro.setAutores(List.of(autor));
        // Guardar el libro (esto también guardará los autores debido a CascadeType.ALL)
        repository.save(libro);
    }

    public List<LibroDto> convierteDatos(List<Libro> libro){
        return libro.stream()
                .map(s->new LibroDto(
                        s.getId(),
                        s.getTitulo(),
                        convierteAutores(s.getAutores()),
                        s.getIdioma(),
                        s.getNumeroDescargas()))
                .collect(Collectors.toList());
    }

    private DatosAutores convierteAutores(List<Autores> autores) {
        if (autores==null || autores.isEmpty()) {return null;}
        Autores autor = autores.get(0);
        return new DatosAutores(
                autor.getNombreAutor(),
                autor.getAnoNacimiento(),
                autor.getAnoMuerte());
    }
//    @Transactional(readOnly = true)
//    public List<Libro> listarLibrosPorIdioma(String idioma) {
//        return repository.findByIdioma(idioma);
//    }
}
