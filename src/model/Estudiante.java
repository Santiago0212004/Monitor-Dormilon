package model;

import main.Main;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Clase que representa a un estudiante que puede interactuar con un monitor
 * y ocupar sillas en una sala de espera.
 */
public class Estudiante extends Thread{
    private int id; // Identificador único del estudiante
    private Semaphore monitor; // Semáforo que controla el acceso al monitor
    private Semaphore sillas; //Semáforo que controla el acceso a las sillas
    private boolean enSilla = false; // Indica si el estudiante está sentado en una silla

    //Constructor
    public Estudiante(int id, Semaphore monitor, Semaphore sillas) {
        this.id = id;
        this.monitor = monitor;
        this.sillas = sillas;
    }


    /**
     * Método que simula el comportamiento del estudiante.
     * El estudiante puede despertar al monitor, ser atendido por él,
     * sentarse en una silla a esperar o irse a programar si las sillas
     * están ocupadas.
     */
    public void run() {
        Random rnd = new Random();

        while (true) {
            try {
                // El estudiante despierta al monitor y es atendido por él
                if(monitor.availablePermits() == 1 && sillas.availablePermits()==3){
                    monitor.acquire();
                    System.out.println("Estudiante "+id+" despierta al monitor");
                    System.out.println("El estudiante "+id+" es atendido por el monitor");
                    Thread.sleep(rnd.nextInt(2000));
                    monitor.release();
                    break;
                }
                // El estudiante se sienta en una silla a esperar
                else if(monitor.availablePermits() == 0 && sillas.availablePermits()>0){
                    if(!enSilla){
                        sillas.acquire();
                        System.out.println("Estudiante "+id+" se sienta en una silla a esperar");
                        Main.nextSilla.add(id);
                        enSilla = true;
                        break;
                    }
                }
                // El estudiante se va a programar si las sillas están llenas
                else {
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

