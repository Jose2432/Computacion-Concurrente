package org.concurrente.tarea5;

import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Clase que implementa la prueba TASLock
 */
public class TASLock implements Lock{
    //Declaramos una variable booleana atomica con el valor de false
    AtomicBoolean state = new AtomicBoolean(false);

    /*
    * Bloquea el hilo en ejecucion
    */
    @Override
    public void lock() {
        //Bloqueamos mientras que el estado sea true
        while(state.getAndSet(true));
    }

    /*
    * Desbloquea el hilo en ejecucion
    */
    @Override
    public void unlock() {
        //Desbloqueamos dandole el valor false al estado
        state.set(false);
    }
}
