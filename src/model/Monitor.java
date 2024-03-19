package model;

import main.Main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

//En esta clase monitor representa un hilo, para simular el comportamiento del monitor
public class Monitor extends Thread{

    // Semáforo que controla el acceso al monitor
    private Semaphore monitor;

    // Semáforo que controla el número de sillas disponibles
    private Semaphore sillas;

    //Constructor de la clase monitor
    public Monitor(Semaphore monitor, Semaphore sillas) {
        this.monitor = monitor;
        this.sillas = sillas;
    }

    //Representación del comportamiento del hilo del monitor
    public void run() {
        Random rnd = new Random();

        while (true) {
            try {
                while (sillas.availablePermits()<3) {
                    monitor.acquire(); //El monitor adquiere el permiso
                    //Se atiende al estudiante que estaba esperando en una silla
                    System.out.println("Estudiante "+ Main.nextSilla.poll() + " es atendido por el monitor (estaba en silla)");
                    sillas.release();//Se libera la silla ocupada en el corredor ya que un estudiante fue atendido
                    Thread.sleep(rnd.nextInt(2000)); // Al monitor se le asigna un tiempo aleatorio simulando la atención al estudiante
                    monitor.release(); // Se libera el permiso del monitor
                }
                System.out.println("Monitor dormido..."); // Como el monitor no tiene estudiantes que atender, se duerme
                Thread.sleep(1000); // El monitor espera antes de verificar nuevamente el estado de las sillas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
