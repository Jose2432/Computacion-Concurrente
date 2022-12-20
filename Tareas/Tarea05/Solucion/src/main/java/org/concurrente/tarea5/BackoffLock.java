package org.concurrente.tarea5;

import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);//creación de referencia y objeto de la clase AtomicBoolean que recibe en le constructor un boolean
    private static final int MIN_DELAY = 10;//Declaración e inicialización de la variable MIN_DELAY 
    private static final int MAX_DELAY = 10;//Declaración e inicialización de la variable MAX_DELAY

	@Override
	public void lock() {//sobreescritura de un método lock de la  interfaz Lock
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);//creación de referencia y objeto de la clase Backoff que recibe en le constructor 2 varibles de tipo int

        while (true) {//Bloqueamos mientras que el estado sea true
            while (state.get()) {};//espera cocupada hasta que el valor deje de ser true
            if (!state.getAndSet(true)) {//condición sea diferente de false
                return;//ruptura del ciclo while
            } else {
                try {
					backoff.backoff();//lamada del metodo de la clase Backoff
				} catch (InterruptedException e) { }//condición que se ejecuta si sucede un erro en el try
            }
        }
	}

	@Override
	public void unlock() {//sobreescritura de un método lock de la  interfaz Lock
        state.set(false);//Desbloqueamos dandole el valor false al estado
	}


}
