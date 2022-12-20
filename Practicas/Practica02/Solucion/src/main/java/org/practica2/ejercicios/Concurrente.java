package org.practica2.ejercicios;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.practica2.ejercicios.matrices.MatrizConcurrente;
import org.practica2.ejercicios.matrices.MatrizSecuencial;
import org.practica2.ejercicios.sumas.SumaConcurrente;
import org.practica2.ejercicios.sumas.SumaSecuencial;

public class Concurrente {
    private static enum Prueba {

        MATRIZ("Multiplicación de matrices concurrente", 0),
        NUMEROS("Suma de los primeros numeros naturales concurrente", 0),
        SOPAS("Sopa de letras concurrente", 0);

        private String nombre;
        private int tamaño;

        private Prueba(String nombre, int tamaño) {
            this.nombre = nombre;
            this.tamaño = tamaño;
        }
    }

    public static void main(String[] args) {
        long tiempoInicial, tiempoTotal;
        HashMap<Prueba, List<Long>> map = new HashMap<Prueba, List<Long>>();

        for (int hilos : List.of(1, 5, 10, 100)) {
            for (int tamaño : List.of(10, 100, 1000)) {
                for (int i = 0; i < 20; i++) {
                    Prueba matriz = Prueba.MATRIZ;
                    matriz.tamaño = tamaño;
                    MatrizConcurrente matrizCon = new MatrizConcurrente(tamaño, hilos);
                    tiempoInicial = System.nanoTime();
                    matrizCon.mult();
                    tiempoTotal = System.nanoTime() - tiempoInicial;
                    map.computeIfAbsent(matriz, k -> new ArrayList<>()).add(tiempoTotal);

                    Prueba numeros = Prueba.NUMEROS;
                    numeros.tamaño = tamaño;
                    SumaConcurrente suma = new SumaConcurrente(tamaño, hilos);
                    tiempoInicial = System.nanoTime();
                    suma.calculaSuma();
                    tiempoTotal = System.nanoTime() - tiempoInicial;
                    map.computeIfAbsent(numeros, k -> new ArrayList<>()).add(tiempoTotal);

                    Prueba sopas = Prueba.SOPAS;
                    sopas.tamaño = tamaño;
                    tiempoInicial = System.nanoTime();
                    // Aquí va la ejecución del ejercicio
                    tiempoTotal = System.nanoTime() - tiempoInicial;
                    map.computeIfAbsent(sopas, k -> new ArrayList<>()).add(tiempoTotal);
                }

                map.forEach((key, value) -> {
                    long suma = value.stream().mapToLong(Long::longValue).sum();
                    System.out.printf("En promedio, la prueba %s con hilos = %d " +
                                      "y n = %d tomó %2.9f segundos\n", key.nombre,
                                      hilos, key.tamaño, (suma / (1000000000.0 * 20)),
                                      NumberFormat.getIntegerInstance().format(tamaño));
                });
                System.out.println();
            }
        }
    }
}
