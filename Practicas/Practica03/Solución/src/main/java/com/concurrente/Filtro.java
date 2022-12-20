package com.concurrente;

public class Filtro implements Semaforo {

    private volatile int[] level;
    private volatile int[] lastToEnter;
	private int hilos;
	private int hilosMaximos;

	public Filtro(int hilos, int permitidos) {
        this.hilos = hilos;
        this.hilosMaximos = permitidos;

        level = new int[hilos];
        lastToEnter = new int[hilos];

        for (int i = 0; i < level.length; i++) {
            level[i] = -1;
        }
	}

	@Override
	public int getPertitsOnCriticalSections() {
        return hilosMaximos;
	}

	@Override
	public void acquire() {
        int i = Integer.parseInt(Thread.currentThread().getName());

        for (int l = 0; l < (hilos - hilosMaximos); l++) {
            level[i] = l;
            lastToEnter[l] = i;

            for (int k = 0; k < hilos; k++) {
                while ((lastToEnter[l] == i) && (k != i) && (level[k] >= l));
            }
        }
	}

	@Override
	public void release() {
        int i = Integer.parseInt(Thread.currentThread().getName());
        level[i] = -1;
	}
}
