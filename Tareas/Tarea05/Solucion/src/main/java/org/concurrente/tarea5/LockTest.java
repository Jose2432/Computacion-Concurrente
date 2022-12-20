package org.concurrente.tarea5;

public class LockTest {
    static final int THREADS = 2; // 2, 3, 7, 15, 21, 30, 50
    static final int ITERATIONS = 50;
    static final int MAX_VALUE = 1000000;
    static final int WORK_SIZE = MAX_VALUE / THREADS;
    static final int REMAINING_WORK = MAX_VALUE % THREADS;
    static int APROVATION = 0;
    volatile Counter counter;
    Thread[] threads;

    void performTest(Lock lock) throws InterruptedException {
        for(int i = 0; i < ITERATIONS; i++) {
            counter = new Counter(lock);
            threads = new Thread[THREADS];

            for(int j = 0, remainingWork = REMAINING_WORK; j < threads.length; j++, remainingWork--) {
                final int size = WORK_SIZE + (remainingWork>0 ? 1 : 0);
                threads[j] = new Thread(() -> incrementCounter(size));
            }

            for(Thread t : threads) {
                t.start();
            }

            for(Thread t : threads) {
                t.join();
            }

            assertEquals(MAX_VALUE, counter.getValue());
        }

        if(APROVATION/ITERATIONS > .65){
            System.out.println("Prueba superada \nAprovacion del: "+ APROVATION/ITERATIONS *100+"%");
        }else{
            System.out.println("Prueba no superada");
        }
    }

    void incrementCounter(final int iterations) {
        for(int i = 0; i < iterations; i++) {
            counter.getAndIncrement();
        }
    }

    void assertEquals(int value, int expected){
        if(value != expected){
            System.out.println("Los valores no concuerdan");
        }else{
            ++APROVATION;
        }
    }

    public static void main(String[] args) throws InterruptedException{
        LockTest lt = new LockTest();

        /*
        * Descomentar el test a ejecutar
        */
        
        //lt.performTest(new TASLock());
        //lt.performTest(new TTASLock());
        //lt.performTest(new BackoffLock());
        //lt.performTest(new CLHLock());
        //lt.performTest(new MCSLock());
        //lt.performTest(new ALock(THREADS));
    }
}

class Counter {
    volatile int value;
    volatile Lock lock;

    Counter(Lock lock) {
        this.value = 0;
        this.lock = lock;
    }
    int getAndIncrement() {
        this.lock.lock();
        int result = this.value++;
        
        this.lock.unlock();
        return result;
    }

    int getValue() {
        return value;
    }
}
