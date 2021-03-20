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

import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.TipoDato;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.VARIACION_TIEMPO;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.MIN_COMSUMIR;
import static es.uja.ssccdd.curso2021.gonzalezgarciamiguelprac1.Constantes.aleatorio;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel González García
 */
public class Consumidor implements Callable<ArrayList<Dato>> {
    private final TipoDato tipoDato;
    private final ArrayList<Dato> racha;
    private final BufferSelectivo buffer;

    public Consumidor(TipoDato tipoDato, BufferSelectivo buffer) {
        this.tipoDato = tipoDato;
        this.racha = new ArrayList<Dato>();
        this.buffer = buffer;
    }

    @Override
    public ArrayList<Dato> call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void extraerDatos(){
        
    }
    
    private void consumirDatos(){
        for (int i = 0; i < racha.size(); i++) {
            try {
                TimeUnit.SECONDS.wait(MIN_COMSUMIR + aleatorio.nextInt(VARIACION_TIEMPO));
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
            racha.remove(i);
        }
        if(!racha.isEmpty())
            racha.clear();
    }
    
}
