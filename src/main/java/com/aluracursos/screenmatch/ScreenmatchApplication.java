package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.principal.EjemploStreams;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		//SpringApplication.run(ScreenmatchApplication.class, args);
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Ahora se crea una instancia de la clase principal
		Principal principal = new Principal();
		//principal.muestraElMenu();
		principal.muestraElMenu();

		//EjemploStreams ejemploStreams = new EjemploStreams();//Instanciamos la clase EjemploStreams
		//ejemploStreams.muestraEjemplo();//llamamos el método de muestraEjemplo


		/* eliminamos este código porque también ya lo pasamos a la clase principal
		//Crear una instancia de consumo API obtenida de ombd y nuestra apikey
		var consumoAPI = new ConsumoAPI();
		//obtiene datos generales de la API
		var json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones+&apikey=896cbd1c");
		System.out.println(json);
		//instancia que convierte datos
		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);
		//obtiene datos de un episodio de la API
		json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones+&Season=1&episode=1&apikey=896cbd1c");//primero se escriben las comillas y luego se pega el url
		//llamamos al conversor
		DatosEpisodio episodios = conversor.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodios);
        */

		//Este código lo pasamos a la clase Principal
		/*List<DatosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i < datos.totalTemporadas() ; i++) {
			json= consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones+&Season="+i + "&apikey=896cbd1c");
			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);
		 */
	}
}
