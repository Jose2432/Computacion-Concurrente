package org.practica2.ejercicios.matrices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Clase especifica para manipular los archivos de prueba de matrices
 */
public class Matrices {

    protected int[][] matriz;
    protected int[][] resultado;

    public Matrices(int n) {
        String path = String.format("matrices/mat%d", n);
        InputStream inputStream = getClass()
            .getClassLoader()
            .getResourceAsStream(path);
        String matriz = entrada(inputStream);
        this.matriz = creaMatriz(matriz);
    }

    private String entrada(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br =
             new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException ioe) {
            System.out.println("Error al leer el archivo");
        }
        return stringBuilder.toString();
    }

    private int[][] creaMatriz(String lineas) {
        return lineas.lines()
            .map(this::creaArreglo)
            .toArray(int[][]::new);
    }

    private int[] creaArreglo(String arreglo) {
        return Arrays.stream(arreglo.split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
    }

    public void print() {
        Arrays.stream(resultado)
            .map(Arrays::toString)
            .map(a -> String.join(" ", a)
                 .replaceAll(",|\\[|\\]", ""))
            .forEach(System.out::println);
    }
}
