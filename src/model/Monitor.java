package model;

import main.Main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Monitor extends Thread{
    private Semaphore monitor;
    private Semaphore sillas;

    public Monitor(Semaphore monitor, Semaphore sillas) {
        this.monitor = monitor;
        this.sillas = sillas;
    }

    public void run() {
        Random rnd = new Random();

        while (true) {
            try {
                while (sillas.availablePermits()<3) {
                    monitor.acquire();
                    System.out.println("Estudiante "+ Main.nextSilla.poll() + " es atendido por el monitor (estaba en silla)");
                    sillas.release();
                    Thread.sleep(rnd.nextInt(2000));
                    monitor.release();
                }
                System.out.println("Monitor dormido...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
