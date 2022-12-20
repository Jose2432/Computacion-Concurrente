package org.practica2.ejercicios.sumas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumaConcurrente implements Runnable {
    private int n;
    private int total = 0;
    private int hilos;
    private int tamaño;
    private static int[] matriz;

    public SumaConcurrente(int n, int hilos) {
        this.n = n;
        this.hilos = hilos;
        this.total = 0;
        this.tamaño = (int) Math.ceil(n * 1.0 / hilos);
        SumaConcurrente.matriz = new int[hilos];
    }

    @Override
    public void run() {
        suma(Integer.parseInt(Thread.currentThread().getName()));
    }

    private void suma(int i) {
        int a = i * tamaño + 1;
        int b = (i + 1) * tamaño > n ? n : (i + 1) * tamaño;
        SumaConcurrente.matriz[i] = suma(a, b);
    }

    public static int suma(int a, int b) {
        int total = 0;

        for (int i = a; i <= b; i++) {
            total += i;
        }

        return total;
    }

    public void calculaSuma() {
        if (hilos > n) {
            return;
        }

        SumaConcurrente suma = new SumaConcurrente(n, hilos);
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

        this.total = Arrays.stream(matriz).sum();
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

    public void imprimirResultado() {
        System.out.printf("La suma de los primeros %d con %d hilos es %d\n", n, hilos, total);
    }
}
