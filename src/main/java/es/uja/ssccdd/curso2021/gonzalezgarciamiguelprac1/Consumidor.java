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

import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato.*;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.VARIACION_TIEMPO;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MIN_COMSUMIR;
import es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.*;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.aleatorio;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel González García
 */
public class Consumidor implements Callable<ArrayList<Dato>> {

    private static Boolean terminadosProductores = false;

    private final TipoDato tipoDato;
    private final ArrayList<Dato> racha;
    private final BufferSelectivo buffer;
    private final ReentrantLock mutexBuffer;
    private final Semaphore emptySemBufferA;
    private final Semaphore fillSemBufferA;
    private final Semaphore emptySemBufferB;
    private final Semaphore fillSemBufferB;

    public Consumidor(TipoDato tipoDato, BufferSelectivo buffer, ReentrantLock mutexBuffer, Semaphore emptySemBufferA, Semaphore fillSemBufferA, Semaphore emptySemBufferB, Semaphore fillSemBufferB) {
        this.tipoDato = tipoDato;
        this.buffer = buffer;
        this.racha = new ArrayList<Dato>();
        this.mutexBuffer = mutexBuffer;
        this.emptySemBufferA = emptySemBufferA;
        this.fillSemBufferA = fillSemBufferA;
        this.emptySemBufferB = emptySemBufferB;
        this.fillSemBufferB = fillSemBufferB;
    }

    @Override
    public ArrayList<Dato> call() throws Exception {

        return racha;
    }

    /**
     * @brief extrae una racha de datos del buffer
     */
    private boolean extraerDato() {
        if (tipoDato != AB) {
            racha.add(buffer.get(tipoDato));
        } else {
            racha.add(buffer.get());
        }
        return racha.get(racha.size() - 1).isFinRacha();
    }

    /**
     * @brief Consume la racha de datos que ha extraido
     */
    private void consumirDatos() {
        for (int i = 0; i < racha.size(); i++) {
            try {
                TimeUnit.SECONDS.wait(MIN_COMSUMIR + aleatorio.nextInt(VARIACION_TIEMPO));
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
            racha.remove(i);
        }
        if (!racha.isEmpty()) {
            racha.clear();
        }
    }

    public static void switchTerminadosProductores() {
        terminadosProductores = !terminadosProductores;
    }
}
