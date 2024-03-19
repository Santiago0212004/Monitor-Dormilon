package main;

import model.Monitor;
import model.Estudiante;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/*
Authors
Juan Yustes A00380718
Santiago Barraza A00375190
Luisa Castaño A00380290
*/

public class Main {

    //Se crea la cola de las sillas de espera para los estudiantes entrantes.
    public static Queue<Integer> nextSilla = new LinkedList<>();
    public static void main(String[] args) throws InterruptedException {
        //Se establece un numero n de estudiantes
        final int NUM_ESTUDIANTES = 10; // Número de estudiantes
        final int NUM_SILLAS = 3; // Número de sillas disponibles en el corredor

        //Se establece un semáforo para el monitor con un unico permiso inicialmente
        Semaphore monitor = new Semaphore(1);

        //Se establece un semáforo para controlar el número de sillas disponibles en el corredor
        Semaphore sillas = new Semaphore(NUM_SILLAS);

        //Se crea una instancia de monitor y se incia
        Monitor m = new Monitor(monitor, sillas);
        m.start();

        //Se crean instancias de estudiantes
        //de acuerdo al valor establecido anteriormente en NUM_ESTUDIANTE y se inician
        for (int i = 1; i <= NUM_ESTUDIANTES; i++) {
            new Estudiante(i, monitor, sillas).start();
            //La lógica funciona bien si no se pone, descomentar si requiere verlo de forma más comprensible
            Thread.sleep(10);
        }
    }
}
