package org.practica2.ejercicios.matrices;

public class MatrizSecuencial extends Matrices {

    public MatrizSecuencial(int n) {
        super(n);
    }

    public void mult() {
        multiplicacion(matriz, matriz, matriz.length);
    }

    public void multiplicacion(int[][] a, int[][] b, int r1) {
        int[][] producto = new int[r1][a[0].length];

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    producto[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        this.resultado = producto;
    }
}
