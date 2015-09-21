/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author alulab14
 */
public class Arista {
    // todos los nodos destino tienen pedido
    private Nodo nodoOrigen;
    private Nodo nodoDestino;
    private double cantGLP;

    /**
     * @return the nodoOrigen
     */
    public Nodo getNodoOrigen() {
        return nodoOrigen;
    }

    /**
     * @param nodoOrigen the nodoOrigen to set
     */
    public void setNodoOrigen(Nodo nodoOrigen) {
        this.nodoOrigen = nodoOrigen;
    }

    /**
     * @return the nodoDestino
     */
    public Nodo getNodoDestino() {
        return nodoDestino;
    }

    /**
     * @param nodoDestino the nodoDestino to set
     */
    public void setNodoDestino(Nodo nodoDestino) {
        this.nodoDestino = nodoDestino;
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
