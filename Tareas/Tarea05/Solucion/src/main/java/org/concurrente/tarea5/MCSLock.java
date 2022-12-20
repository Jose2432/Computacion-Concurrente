package org.concurrente.tarea5;

import java.util.concurrent.atomic.AtomicReference;

class QNode{
    volatile boolean locked = false;//Declaración e inicialización de la variable locked que es volatile
    QNode next = null;//Declaración e inicialización de la variable next 
}

public class MCSLock implements Lock {
    AtomicReference<QNode> tail;//Declaración de la variable tail de tipo AtomicReference<QNode>
    ThreadLocal<QNode> myNode;//Declaración de la variable myNode de tipo ThreadLocal<QNode>

    public MCSLock(){
        tail = new AtomicReference<QNode>(null);//creación de un objeto de la clase AtomicReference<QNode> que recibe en le constructor un null
        myNode = new ThreadLocal<QNode>(){ //Incianciamos myNode con un nodo
              /*
            * Crea un nodo inicial
            * @Return el nodo inicial
            */
            protected QNode initialValue(){
                return new QNode();//Regresamos el nodo inicial
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();//Creamos un objeto QNode con el valor de myNode
        QNode pred = tail.getAndSet(qnode);//Creamos un objeto QNode con el valor de tail
        if(pred != null){//cheacamos que pred sea diferente de null
            qnode.locked = true;//le damos el valor a qnode.locked de true
            pred.next = qnode;//le damos a pred.next el valor que tenga qnode 
            while(qnode.locked) Thread.yield();//Mientras el objeto qnode este bloqueado, permanecemos en el while donde se ejecutará Thread.yield()
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();//Creamos un objeto QNode
        if(qnode.next == null){//cheacamos que qnode.next  sea diferente de null
            if(tail.compareAndSet(qnode,null)) return;//se checa en el condicional lo que nos de tail.compareAndSet(qnode,null) y si es verdadero no regresamos nada 
            while(qnode.next == null) Thread.yield();//Mientras el objeto qnode.next sea igual a null , permanecemos en el while donde  se ejecutará Thread.yield()
        }
        qnode.next.locked = false;//le damos el valor a qnode.next.locked de false
        qnode.next = null;//le damos el valor a qnode.next de null
    }
}
