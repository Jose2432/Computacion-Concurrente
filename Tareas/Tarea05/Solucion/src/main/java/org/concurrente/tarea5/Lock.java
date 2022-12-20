package org.concurrente.tarea5;

/**
 * Interfaz para un candado
 */
public interface Lock {

    /**
     * Metodo para bloquear
     */
    public void lock();

    /**
     * Metodo para desbloquear
     */
    public void unlock();

}
