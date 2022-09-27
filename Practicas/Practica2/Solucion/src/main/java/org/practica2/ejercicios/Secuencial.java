package org.practica2.ejercicios;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.practica2.ejercicios.matrices.MatrizSecuencial;
import org.practica2.ejercicios.sumas.SumaSecuencial;

public class Secuencial {
    private static enum Prueba {

        MATRIZ("Multiplicación de matrices secuencial", 0),
        NUMEROS("Suma de los primeros numeros naturales secuencial", 0),
        SOPAS("Sopa de letras secuencial", 0);

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

        for (int tamaño : List.of(10, 100, 1000)) {
            for (int i = 0; i < 20; i++) {
                Prueba matriz = Prueba.MATRIZ;
                matriz.tamaño = tamaño;
                MatrizSecuencial matrizSec = new MatrizSecuencial(tamaño);
                tiempoInicial = System.nanoTime();
                matrizSec.mult();
                tiempoTotal = System.nanoTime() - tiempoInicial;
                map.computeIfAbsent(matriz, k -> new ArrayList<>()).add(tiempoTotal);

                Prueba numeros = Prueba.NUMEROS;
                numeros.tamaño = tamaño;
                SumaSecuencial suma = new SumaSecuencial(tamaño);
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
                System.out.printf("En promedio, la prueba %s con" +
                                  " n = %d tomó %2.9f segundos\n",
                                  key.nombre, key.tamaño, (suma / (1000000000.0 * 20)),
                                  NumberFormat.getIntegerInstance().format(tamaño));
            });
            System.out.println();
        }
    }
}
