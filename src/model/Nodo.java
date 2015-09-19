/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author a20080480
 */
public class Nodo {
    int idNodo;
    int coordX;
    int coordY;
    String habilitado;
    public Nodo(Integer idNodo) {
        this.idNodo = idNodo;
    }

    public Nodo(int idNodo, int coordX, int coordY) {
        this.idNodo = idNodo;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(int idNodo) {
        this.idNodo = idNodo;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }
}
