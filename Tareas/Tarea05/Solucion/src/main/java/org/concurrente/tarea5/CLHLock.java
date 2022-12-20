package org.concurrente.tarea5;

import java.util.concurrent.atomic.AtomicReference;

/*
 * Clase que implementa la prueba CLHLock
 */
public class CLHLock implements Lock {
    //Creamos una variable la cual es una referencia atomica de un nodo llamada tail
    AtomicReference<QNode> tail;

    //Creamos una variable la cual es un hilo local de un nodo llamada myPred
    ThreadLocal<QNode> myPred;

    //Creamos una variable la cual es un hilo local de un nodo llamada myNode
    ThreadLocal<QNode> myNode;

    /*
    * Bloquea la prueba CLHLock
    */
    public CLHLock() {
        tail = new AtomicReference<QNode>(null); //Instanciamos tail 
        myNode = new ThreadLocal<QNode>() { //Instanciamos myNode 
            /*
            * Crea un nodo inicial
            * @Return el nodo inicial
            */
            protected QNode initialValue() {
                return new QNode(); //Regresamos el nodo inicial
            }
        };

        myPred = new ThreadLocal<QNode>() { //Incianciamos myPred con un nodo
            /*
            * Crea un nodo inicial
            * @Return el nodo inicial
            */
            protected QNode initialValue() {
                return null; //Regresamos null 
            }
        };
    }

    /*
    * Clase privada del QNode
    */
    private class QNode {
        private volatile Boolean locked; //Creamos una variable booleana volatil llamada locked
    }

    /*
    * Bloquea el hilo en ejecucion
    */
	@Override
	public void lock() {
        QNode qNode = myNode.get(); //Creamos un objeto QNode con el valor de myNode
        qNode.locked = true; //Bloqueamos QNode
        QNode pred = tail.getAndSet(qNode); //Creamos un objeto QNode con el valor de tail
        myPred.set(pred); //Asignamos el valor al QNode
        while (pred.locked) {} //Mientras el objeto QNode este bloqueado, permanecemos en el metodo
	}

    /*
    * Desbloquea el hilo en ejecucion
    */
	@Override
	public void unlock() {
        QNode qNode = myNode.get(); //Creamos un objeto QNode
        qNode.locked = false; //Bloqueamos QNode
        myNode.set(myPred.get()); //A la variable myNode le asignamos el valor de lo que contiene myPred
	}
}
