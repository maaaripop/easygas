/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author a20080480
 */
public class TipoCamion {
    int idTipoCamion;
    int capacidadDiesel;
    private double capacidadGLP;
    private int taraTon;
    
    public TipoCamion(int idTipoCamion) {
        this.idTipoCamion = idTipoCamion;
    }

    public TipoCamion(int idTipoCamion, int capacidadDiesel, double capacidadGLP,int taraTon) {
        this.idTipoCamion = idTipoCamion;
        this.capacidadDiesel = capacidadDiesel;
        this.capacidadGLP = capacidadGLP;
        this.taraTon = taraTon;
    }

    public int getIdTipoCamion() {
        return idTipoCamion;
    }

    public void setIdTipoCamion(int idTipoCamion) {
        this.idTipoCamion = idTipoCamion;
    }

    public int getCapacidadDiesel() {
        return capacidadDiesel;
    }

    public void setCapacidadDiesel(int capacidadDiesel) {
        this.capacidadDiesel = capacidadDiesel;
    }

    public double getCapacidadGLP() {
        return capacidadGLP;
    }

    public void setCapacidadGLP(int capacidadGLP) {
        this.capacidadGLP = capacidadGLP;
    }

    /**
     * @return the taraTon
     */
    public int getTaraTon() {
        return taraTon;
    }

    /**
     * @param taraTon the taraTon to set
     */
    public void setTaraTon(int taraTon) {
        this.taraTon = taraTon;
    }
    
   
}
