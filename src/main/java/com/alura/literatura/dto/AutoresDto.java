package com.alura.literatura.dto;


public record AutoresDto(
        String nombreAutor,
        Integer anoNacimiento,
        Integer anoMuerte
) {
}
