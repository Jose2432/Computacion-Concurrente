package org.practica2.ejercicios.matrices;

import java.util.ArrayList;
import java.util.List;

public class MatrizConcurrente extends Matrices implements Runnable {

    private int n;
    private int hilos;

    public MatrizConcurrente(int n, int hilos) {
        super(n);
        this.n = n;
        this.hilos = hilos;
    }

    @Override
    public void run() {
        multiplicacion(matriz, matriz, matriz.length, Integer.parseInt(Thread.currentThread().getName()));
    }

    public void mult() {
        if (hilos > n) {
            return;
        }

        MatrizConcurrente suma = new MatrizConcurrente(n, hilos);
        List<Thread> listaHilos = new ArrayList<Thread>();

        for (int i = 0; i < hilos; i++) {
            Thread thread = new Thread(suma, String.valueOf(i));
            listaHilos.add(thread);
            thread.start();

            if (listaHilos.size() == hilos) {
                joinThreads(listaHilos);
                listaHilos.clear();
            }
        }

        joinThreads(listaHilos);
    }

    private void joinThreads(List<Thread> threads) {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ie) {
                System.out.println("Error " + ie.getMessage());
            }
        }
    }

    public void multiplicacion(int[][] a, int[][] b, int r1, int i) {
        int[][] producto = new int[r1][a[0].length];

        // for (int i = 0; i < r1; i++) {
        for (int j = 0; j < b[0].length; j++) {
            for (int k = 0; k < a[0].length; k++) {
                producto[i][j] += a[i][k] * b[k][j];
            }
        }
        // }

        this.resultado = producto;
    }
}
