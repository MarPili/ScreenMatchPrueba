package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    //Instancia que leera o mapeara los datos de la API
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);//va retornar un objeto de tipo objectmaper, va leer el valor y transformará el json en esa clase
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
