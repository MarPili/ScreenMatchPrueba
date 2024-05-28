package com.aluracursos.screenmatch.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class <T> clase); // <T> T indica que estamos trabajando con datos genericos
}
