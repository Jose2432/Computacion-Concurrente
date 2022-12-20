package com.concurrente;

public class FiltroClasico implements Lock {

    private volatile int[] level;
    private volatile int[] victim;

    public FiltroClasico(int threads) {
        level = new int[threads];
        victim = new int[threads];
        for (int i = 1; i < threads; i++) {
            level[i] = 0;
        }
    }

    /**
     * El hilo actual adquiere el candado. Esperara x cantidad de tiempo antes
     * de que otro hilo lo tome
     */
    public void lock() {
        int threadID = Integer.parseInt(Thread.currentThread().getName());
        for (int i = 1; i < level.length; i++) {
            level[threadID] = i;
            victim[i] = threadID;
            for (int j = 0; j < level.length; j++) {
                while (j != threadID && level[j] >= i && victim[i] == threadID)
                    ;
            }
        }
    }

    /**
     * El hilo actual deja el candado. Esto servirá para que otro Hilo lo
     * adquiera
     */
    public void unlock() {
        int threadID = Integer.parseInt(Thread.currentThread().getName());
        level[threadID] = 0;
    }
}
