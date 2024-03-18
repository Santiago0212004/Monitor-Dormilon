package main;

import model.Monitor;
import model.Estudiante;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Main {
    public static Queue<Integer> nextSilla = new LinkedList<>();
    public static void main(String[] args) throws InterruptedException {
        final int NUM_ESTUDIANTES = 10; // Número de estudiantes
        final int NUM_SILLAS = 3; // Número de sillas disponibles en el corredor
        Semaphore monitor = new Semaphore(1);
        Semaphore sillas = new Semaphore(NUM_SILLAS);

        Monitor m = new Monitor(monitor, sillas);
        m.start();

        for (int i = 1; i <= NUM_ESTUDIANTES; i++) {
            new Estudiante(i, monitor, sillas).start();
            //La lógica funciona bien si no se pone, descomentar si requiere verlo de forma más comprensible
            Thread.sleep(10);
        }
    }
}
