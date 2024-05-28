package com.aluracursos.screenmatch.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
     List<String> nombres = Arrays.asList("Brenda", "Luis", "Maria Fernanda", "Erick", "Genesys");
     nombres.stream() //Permite hacer operaciones encadenadas
             .sorted() //ordena la lista en forma alfabética
             .limit(2) //limita el numero de elementos de la lista
             .filter(n -> n.startsWith("B")) //Encuentra el nombre de una persona por la primera letra
             .map(n-> n.toUpperCase()) //Convierte ese nombre en letra mayuscula
             .forEach(System.out::println);//imprime la lista
    }
}
