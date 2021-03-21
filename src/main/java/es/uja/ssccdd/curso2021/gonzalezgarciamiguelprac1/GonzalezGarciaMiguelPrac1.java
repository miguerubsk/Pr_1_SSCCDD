/*
 * Copyright (C) 2021 Miguel González García
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1;

import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MAX_DATOS_A;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MAX_DATOS_B;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.NUM_CONSUMIDORES;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.NUM_PRODUCTORES;
import es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato.A;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato.AB;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato.B;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.aleatorio;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel González García
 */
public class GonzalezGarciaMiguelPrac1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferSelectivo buffer = new BufferSelectivo();
        ReentrantLock mainMutex = new ReentrantLock();
        ReentrantLock mutexBuffer = new ReentrantLock();
        Semaphore emptySemBufferA = new Semaphore(0);
        Semaphore fillSemBufferA = new Semaphore(MAX_DATOS_A);
        Semaphore emptySemBufferB = new Semaphore(0);
        Semaphore fillSemBufferB = new Semaphore(MAX_DATOS_B);
        
        System.out.println("Hilo principal ha iniciado la ejecución\n");
        
        Finalizador finalizador = new Finalizador();
        CyclicBarrier barrier = new CyclicBarrier(NUM_PRODUCTORES, finalizador);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_PRODUCTORES+NUM_CONSUMIDORES);
        ArrayList<Callable<List<Dato>>> tareas = new ArrayList<Callable<List<Dato>>>();
        
        for (int i = 0; i < NUM_PRODUCTORES; i++) {
            int tipoDato = aleatorio.nextInt(2);
            Productor productor = null;
            switch(tipoDato){
                case 0:
                    productor = new Productor(A, buffer, mutexBuffer, emptySemBufferA, fillSemBufferA, emptySemBufferB, fillSemBufferB, barrier);
                    break;
                case 1:
                    productor = new Productor(B, buffer, mutexBuffer, emptySemBufferA, fillSemBufferA, emptySemBufferB, fillSemBufferB, barrier);
                    break;
            }
            tareas.add(productor);
        }
        
        for (int i = 0; i < NUM_CONSUMIDORES; i++) {
            int tipoDato = aleatorio.nextInt(3);
            Consumidor consumidor = null;
            switch(tipoDato){
                case 0:
                    consumidor = new Consumidor(A, buffer, mainMutex, mutexBuffer, emptySemBufferA, fillSemBufferA, emptySemBufferB, fillSemBufferB);
                    break;
                case 1:
                    consumidor = new Consumidor(B, buffer, mainMutex, mutexBuffer, emptySemBufferA, fillSemBufferA, emptySemBufferB, fillSemBufferB);
                    break;
                case 2:
                    consumidor = new Consumidor(AB, buffer, mainMutex, mutexBuffer, emptySemBufferA, fillSemBufferA, emptySemBufferB, fillSemBufferB);
            }
            tareas.add(consumidor);
        }
        List<Future<List<Dato>>> resultados = null;
        try {
            resultados = executor.invokeAll(tareas);
        } catch (InterruptedException ex) {
            Logger.getLogger(GonzalezGarciaMiguelPrac1.class.getName()).log(Level.SEVERE, null, ex);
        }
        executor.shutdown();
        
        System.out.println("Hilo principal: Todos los hilos han terminado");
    }

}
