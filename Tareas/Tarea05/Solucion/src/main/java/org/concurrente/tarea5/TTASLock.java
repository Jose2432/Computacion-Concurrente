package org.concurrente.tarea5;

import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Clase la cual implementa la prueba TTSALock
 */
public class TTASLock implements Lock{
    //Declaramos una variable booleana atomica con el valor de false
    private AtomicBoolean state = new AtomicBoolean(false);

    /*
    * Bloquea el hilo dada una condicion 
    */
    @Override
    public void lock() {
        while(true){ //Mientras que la condicion sea verdadera, entramos al ciclo
            while(state.get()); //Mientras que es estado tenga algo que regresar, entramos al ciclo
            if(!state.getAndSet(true)){ //Si el estado ya no tiene algo que devolver entonces salimos del bloqueo
                return;
            }
        }
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
