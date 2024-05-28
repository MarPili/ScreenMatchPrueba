package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//para ignorar los otros atributos
//mapeo de propiedades
public record DatosEpisodio(
        @JsonAlias("Title")String titulo,
        @JsonAlias("Episode")String numeroEpisodio,
        @JsonAlias("ImdbRating")String evaluacion,
        @JsonAlias("Released")String fechaDeLanzamiento
) {
}
