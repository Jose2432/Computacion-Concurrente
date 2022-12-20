package org.concurrente.tarea5;

public class TAS2Lock implements Lock{
    volatile boolean state = false;

    @Override
    public void lock() {
        while(getAndSet(true));    
    }

    @Override
    public void unlock() {
        set(false); 
    }
    
    public boolean getAndSet(Boolean state) {
        boolean result = this.state;
        this.state = state;
        return result;
    }

    public void set(boolean state) {
        this.state = state;
    }

}
