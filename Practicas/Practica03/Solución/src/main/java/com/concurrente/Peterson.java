package com.concurrente;

public class Peterson implements Lock{
  private static  volatile int turno ;
  private static volatile boolean[] bandera = {false,false};

  @Override
  public void lock(){
    if(Thread.currentThread().getName().equals("0")){
      bandera[0] = true;
      turno = 1;
      while(bandera[1] && turno ==1);
    }else{
      bandera[1] = true;
      turno = 0;
      while(bandera[0] && turno == 0);
    }
  }
  
  @Override
  public void unlock(){
    if(Thread.currentThread().getName().equals("0")){
      bandera[0] = false;
    }else{
      bandera[1] = false;
    }
  }
}