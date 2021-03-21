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

import java.util.Random;

/**
 *
 * @author Miguel González García
 */
public interface Constantes {
    
    // - Generador de numeros aleatorio ----------------------------------------
    public static final Random aleatorio = new Random(System.currentTimeMillis());
    
    public enum TipoDato{
        A(0), B(1), AB(2);
        
        private final int valor;
        
        private TipoDato(int valor){
            this.valor = valor;
        }
        
    }

    public static final int MIN_RACHA = 1;
    public static final int MAX_RACHA = 3;
    public static final int MAX_DATOS_A = 50;
    public static final int MAX_DATOS_B = 50;
    public static final int MAX_DATOS = MAX_DATOS_A + MAX_DATOS_B;
    public static final int MIN_PRODUCIR = 2;
    public static final int MIN_COMSUMIR = 3;
    public static final int VARIACION_TIEMPO = 5;
    public static final int NUM_PRODUCTORES = 4;
    public static final int NUM_CONSUMIDORES = 3;
    
}
