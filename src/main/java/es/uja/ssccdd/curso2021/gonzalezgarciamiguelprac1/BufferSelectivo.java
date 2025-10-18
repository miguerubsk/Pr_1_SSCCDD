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
import java.util.LinkedList;

/**
 *
 * @author Miguel González García
 */
public class BufferSelectivo {

    private final LinkedList<Dato> buffer;
    private int numA, numB;

    /**
     * @brief Inicializa el buffer
     */
    public BufferSelectivo() {
        this.buffer = new LinkedList<>();
        this.numA = 0;
        this.numB = 0;
    }

    /**
     * @brief añade un dato a la cola del buffer
     * @param dato dato que se quiere insertar en el buffer
     * @return true si se ha insertado
     */
    public boolean add(Dato dato) {
        switch (dato.getTipoDato()) {
            case A:
                numA++;
                break;
            case B:
                numB++;
                break;
        }
        return this.buffer.offer(dato);
    }

    /**
     * @brief saca un dato de la cola
     * @return dato que se ha obtenido
     */
    public Dato get() {
        Dato aux = this.buffer.poll();
        switch (aux.getTipoDato()) {
            case A:
                numA--;
                break;
            case B:
                numB--;
                break;
        }
        return aux;
    }

    /**
     * @brief Devuelve el primer dato del tipo especificado
     * @param tipoDato tipo del dato que se busca
     * @return el dato o null si no ha encontrado
     */
    public Dato get(TipoDato tipoDato) {
        Dato aux = null;
        for (Dato dato : buffer) {
            if (dato.getTipoDato() == tipoDato) {
                aux = dato;
                buffer.remove(dato);
                break;
            }
        }
        if (aux != null) {
            switch (aux.getTipoDato()) {
                case A:
                    numA--;
                    break;
                case B:
                    numB--;
                    break;
            }
        }
        return aux;
    }

    /**
     * 
     * @return número de datos de tipo A en el buffer
     */
    public int getNumA() {
        return numA;
    }

    /**
     * 
     * @return número de datos de tipo B en el buffer
     */
    public int getNumB() {
        return numB;
    }
    
    /**
     * 
     * @return cantidad total de datos en el buffer
     */
    public int size(){
        return numA + numB;
    }
}
