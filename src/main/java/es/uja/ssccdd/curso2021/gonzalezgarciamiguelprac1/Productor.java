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

import es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.*;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MAX_RACHA;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MIN_PRODUCIR;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MIN_RACHA;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato.A;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.VARIACION_TIEMPO;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.aleatorio;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel González García
 */
public class Productor implements Callable<ArrayList<Dato>> {

    private final TipoDato tipoDato;
    private final ArrayList<Dato> racha;
    private final BufferSelectivo buffer;
    private final ReentrantLock mutexBuffer;
    private final Semaphore emptySemBufferA;
    private final Semaphore fillSemBufferA;
    private final Semaphore emptySemBufferB;
    private final Semaphore fillSemBufferB;
    private final CyclicBarrier barrier;

    /**
     * Constructor
     *
     * @param tipoDato
     * @param buffer
     * @param mutexBuffer
     * @param emptySemBufferA
     * @param fillSemBufferA
     * @param emptySemBufferB
     * @param fillSemBufferB
     * @param barrier
     */
    public Productor(TipoDato tipoDato, BufferSelectivo buffer, ReentrantLock mutexBuffer, Semaphore emptySemBufferA, Semaphore fillSemBufferA, Semaphore emptySemBufferB, Semaphore fillSemBufferB, CyclicBarrier barrier) {
        this.tipoDato = tipoDato;
        this.racha = new ArrayList<>();
        this.buffer = buffer;
        this.mutexBuffer = mutexBuffer;
        this.emptySemBufferA = emptySemBufferA;
        this.fillSemBufferA = fillSemBufferA;
        this.emptySemBufferB = emptySemBufferB;
        this.fillSemBufferB = fillSemBufferB;
        this.barrier = barrier;
    }

    @Override
    public ArrayList<Dato> call() throws Exception {
        int numRacha = MIN_RACHA + aleatorio.nextInt(MAX_RACHA - MIN_RACHA);
        for (int i = 0; i < numRacha; i++) {
            try {
                TimeUnit.SECONDS.wait(MIN_PRODUCIR + aleatorio.nextInt(VARIACION_TIEMPO));
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (i == numRacha - 1) {
                racha.add(new Dato(tipoDato, true));
            } else {
                racha.add(new Dato(tipoDato, false));
            }

            if (tipoDato == A) {
                try {
                    fillSemBufferA.acquire();
                    mutexBuffer.lock();
                    buffer.add(racha.get(i));
                    mutexBuffer.unlock();
                    emptySemBufferA.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    fillSemBufferB.acquire();
                    mutexBuffer.lock();
                    buffer.add(racha.get(i));
                    mutexBuffer.unlock();
                    emptySemBufferB.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            barrier.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return racha;
    }
}
