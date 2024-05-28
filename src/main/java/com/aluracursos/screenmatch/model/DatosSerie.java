package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //va ignorar los campos que no hemos mapeado dentro de esta clase
public record DatosSerie(
                         @JsonAlias("Title") String titulo, //lee los datos de la APi y los transforma a objetos java
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("ImdbRating") String evaluacion) {
}
