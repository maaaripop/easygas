/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author a20080480
 */

public class Nodo {
    int idNodo;
    int coordX;
    int coordY;
    private Pedido pedido;
    String habilitado;
    private Date horaLlegada;
    private double cantGLP;
    private int cantPrioridades;
    public Nodo(Integer idNodo) {
        this.idNodo = idNodo;
    }

    public Nodo(int idNodo, int coordX, int coordY) {
        this.idNodo = idNodo;
        this.coordX = coordX;
        this.coordY = coordY;
        this.cantPrioridades=0;
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

    /**
     * @return the pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the horaLlegada
     */
    public Date getHoraLlegada() {
        return horaLlegada;
    }

    /**
     * @param horaLlegada the horaLlegada to set
     */
    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    /**
     * @return the cantPrioridades
     */
    public int getCantPrioridades() {
        return cantPrioridades;
    }

    /**
     * @param cantPrioridades the cantPrioridades to set
     */
    public void setCantPrioridades(int cantPrioridades) {
        this.cantPrioridades = cantPrioridades;
    }

    /**
     * @return the cantGLP
     */
    public double getCantGLP() {
        return cantGLP;
    }

    /**
     * @param cantGLP the cantGLP to set
     */
    public void setCantGLP(double cantGLP) {
        this.cantGLP = cantGLP;
    }
}
