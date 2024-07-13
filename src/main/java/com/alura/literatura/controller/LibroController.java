package com.alura.literatura.controller;

import com.alura.literatura.dto.LibroDto;
import com.alura.literatura.model.Libro;
import com.alura.literatura.service.LirboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LirboService libroService;

    @PostMapping("/registrar")
    public void registrarLibro(@RequestBody LibroDto libroDto){
        libroService.registrarLibro(libroDto);
    }
}
