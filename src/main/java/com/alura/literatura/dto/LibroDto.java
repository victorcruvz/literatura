package com.alura.literatura.dto;

import com.alura.literatura.model.DatosAutores;

import java.util.List;

public record LibroDto(
        Long id,
        String titulo,
        DatosAutores autores,
        List<String> idioma,
        Double numeroDescargas
) {
}
