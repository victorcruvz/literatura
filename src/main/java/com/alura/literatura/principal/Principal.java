package com.alura.literatura.principal;

import com.alura.literatura.dto.LibroDto;
import com.alura.literatura.model.Autores;
import com.alura.literatura.model.DatosLibro;
import com.alura.literatura.model.DatosResultado;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import com.sun.jdi.DoubleValue;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private Optional<Libro> libroBuscado;

    public Principal(LibroRepository repository){this.repositorio = repository;}

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion!=0){
            var menu = """
                    \n
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresPorAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

    }

    private void buscarLibroPorTitulo() {
        System.out.println("\nEscribe el nombre del libro que quieres buscar");
        var nombreLibro = teclado.nextLine();
        //Consumir la Api
        var json = consumoApi.obtenerDatos(URL_BASE
            +"search="+nombreLibro.replace(" ","%20").toLowerCase());
        var datos = conversor.obtenerDatos(json,DatosResultado.class);//Convertir el resultado json a un dato

        List<DatosLibro> libro = datos.resultado().stream().limit(1).collect(Collectors.toList());//Filtar los datos al primer resultado
        DatosLibro libroEncontrado = libro.get(0);//Convertir a un tipo DatosLibro
        Libro libros = new Libro(libroEncontrado);//Pasar a tipo de dato Libro

        List<Libro> libroPorNombre = repositorio.findByTituloContainsIgnoreCase(libros.getTitulo());

        if (libroPorNombre.isEmpty()){
            System.out.println("\n******** Libro Guardado ********\n");
            repositorio.save(libros);

        } else {
            Libro libroQuery = libroPorNombre.get(0);
            System.out.println("\nEste libro ya esta registrado: " + libroQuery.getTitulo());
        }
    }

    private void listarLibros() {
        List<Libro> todosLibros = repositorio.findAllWithAutores();

        if (todosLibros.isEmpty()){
            System.out.println("No se encontraron libros registrados");
        }else {
            todosLibros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Autores> todosAutores = repositorio.findAllAutores();

        if (todosAutores.isEmpty()){
            System.out.println("No se encontraron Autores registrados");
        }else {
            todosAutores.forEach(System.out::println);
        }
    }

    private void listarAutoresPorAno() {
        System.out.println("\nIngresa el año para ver que Autores conciden en ese año");
        var anoAutor = Integer.parseInt(teclado.nextLine());

        List<Autores> todosLosAuores = repositorio.findAutoresByAno(anoAutor);

        if (todosLosAuores.isEmpty()){
            System.out.println("No se encontraron Autores que coincidieran el año: "+anoAutor);
        }else {
            System.out.println("Autores en el año:\n");
            todosLosAuores.forEach(System.out::println);
        }
    }

    @Transactional(readOnly = true)
    private void listarLibrosPorIdioma() {
        List<Libro> todosLibros = repositorio.findAllWithAutores();
        List<String> idiomas = new ArrayList<>();


        //Buscamos en la base de datos los idiomas registrados
        for (Libro libro : todosLibros) {
            String idiomaLibro = libro.getIdioma().toString();
            if (!idiomas.contains(idiomaLibro)) {
                idiomas.add(idiomaLibro);
//                System.out.println("Se ha guardado el idioma: " + idiomaLibro);
            }
        }

        for (String idiomaGuardado : idiomas) {
            var idiomaUsar = idiomaGuardado;
//            System.out.println(idiomaUsar);
            System.out.println("Libros en idioma "+idiomaUsar);
            for (Libro libro : todosLibros) {
                if (idiomaUsar.equals(libro.getIdioma().toString())){
                    System.out.println("Titulo: "+libro.getTitulo()+" ___ Autor: "+libro.getAutores());
                }
            }
        }

//        var cantidadDeIdiomas = idiomas.size();
//        System.out.println("Tamaño del arreglo: "+cantidadDeIdiomas);

//        for (String idiomaGuardado : idiomas) {
//            List<Libro> porIdiomas = repositorio.findByIdioma(idiomaGuardado);
//            System.out.println(":::::::::::::::::::"+idiomaGuardado);
//            porIdiomas.forEach(System.out::println);
//        }












    }
}
