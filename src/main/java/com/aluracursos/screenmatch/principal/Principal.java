package com.aluracursos.screenmatch.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado =new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    //declaramos como constantes las dos partes de las que se compone la URL
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=896cbd1c";
    private ConvierteDatos conversor = new ConvierteDatos();


    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar: ");
         var nombreSerie = teclado.nextLine();
        //traemos los datos de consumo API de ScreenmatchAplication
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+")+ API_KEY);
        //treamos los datos de conversión de ScreenmatchAplication
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i < datos.totalTemporadas() ; i++) {
            json= consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+") + "&Season="+i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);
        //Mostrar solo el titulo de los episodios para las temporadas
       /* for (int i = 0; i < datos.totalTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }*/
        //función lambda  *************************************************
   //   temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

       //Convertir todas las informaciones a una lista del tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t-> t.episodios().stream())
                .collect(Collectors.toList()); //Crea una lista mutable donde se puede agregar información
               // .toList(); // se crea una lista inmutable y ya no se puede agregar más elementos

                // top 5 episodios
        System.out.println("Top 5 episodios");
        datosEpisodios.stream()
               // .filter(e->!e.evaluacion().equalsIgnoreCase("N/A"))
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e-> System.out.println("primer filtro (N/A)" + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("segunda ordenación (M>m)"+e))
                .map(e-> e.titulo().toUpperCase())
                .peek(e-> System.out.println("Tercer filtro mayúscula (m>M)" + e))
                .limit(5)
                //.forEach(System.out::println);
                .forEach(System.out::println);

//Convirtiendo los datos a una lista del tipo episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d-> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);
        //Busqueda de episodios a partir de x año
        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios:");
        var fecha = teclado.nextLine();
        teclado.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(Integer.parseInt(fecha), 1, 1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println("Temporada" + e.getTemporada() + "Episodio" + e.getTitulo() + "Fecha de Lanzamiento" + e.getFechaDeLanzamiento().format(dtf)
                        ));

        //Busca episodio por un pedazo del titulo
        System.out.println("Por favor escriba el titulo del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().contains(pedazoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("Episodio encontrado");
          //  System.out.println("Los datos son: " + episodioBuscado.get().getTitulo());// este traería sólo el titulo del espisodio
            System.out.println("Los datos son: " + episodioBuscado.get());//  Este traería todos los datos del episodio
        }else {
            System.out.println("Episodio no encontrado");
        }

        //Mapa de datos por temporada
        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluación()> 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,Collectors.averagingDouble(Episodio::getEvaluación)));
        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluación()> 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluación));
        System.out.println("Media de las evaluaciones:" + est.getAverage());
        System.out.println("Episodio mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluado: " + est.getMin());
    }
}
