package org.practica2.ejercicios.sumas;

public class SumaSecuencial {
    private int n;
    private int total;

    public SumaSecuencial(int n) {
        this.n = n;
    }

    public void calculaSuma() {
        int total = 0;

        for (int i = 1; i <= n; i++) {
            total += i;
        }

        this.total = total;
    }

    public void imprimirResultado() {
        System.out.printf("La suma de los primeros %d es %d\n", n, total);
    }
}
