package model;

import main.Main;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estudiante extends Thread{
    private int id;
    private Semaphore monitor;
    private Semaphore sillas;
    private boolean enSilla = false;


    public Estudiante(int id, Semaphore monitor, Semaphore sillas) {
        this.id = id;
        this.monitor = monitor;
        this.sillas = sillas;
    }

    public void run() {
        Random rnd = new Random();

        while (true) {
            try {
                if(monitor.availablePermits() == 1 && sillas.availablePermits()==3){
                    monitor.acquire();
                    System.out.println("Estudiante "+id+" despierta al monitor");
                    System.out.println("El estudiante "+id+" es atendido por el monitor");
                    Thread.sleep(rnd.nextInt(2000));
                    monitor.release();
                    break;
                } else if(monitor.availablePermits() == 0 && sillas.availablePermits()>0){
                    if(!enSilla){
                        sillas.acquire();
                        System.out.println("Estudiante "+id+" se sienta en una silla a esperar");
                        Main.nextSilla.add(id);
                        enSilla = true;
                        break;
                    }
                } else {
                    if(!enSilla){
                        System.out.println("Estudiante "+id+" se va a programar (las sillas estaban llenas)");
                        Thread.sleep(rnd.nextInt(20000));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
