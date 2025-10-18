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

/**
 *
 * @author Miguel González García
 */
public class Dato {

    private final TipoDato tipoDato;
    private final boolean finRacha;

    public Dato(TipoDato tipoDato, boolean finRacha) {
        this.tipoDato = tipoDato;
        this.finRacha = finRacha;
    }

    /**
     * 
     * @return el tipo de dato
     */
    public TipoDato getTipoDato() {
        return tipoDato;
    }

    /**
     * 
     * @return true si el dato es el último de la racha que lo generó y false si no lo es
     */
    public boolean isFinRacha() {
        return finRacha;
    }
    
}
