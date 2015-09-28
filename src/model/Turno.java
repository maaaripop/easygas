/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alulab14
 */
public class Turno {
    int idTurno;
    Date horaInicio;
    Date horaFin;
    private double costo;
    private ArrayList<Camion> lcamiones;
    public Turno() {
    }

    public Turno(Integer idTurno) {
        this.idTurno = idTurno;
        this.costo=0;
    }
   
    public Turno(Integer idTurno, Date horaInicio, Date horaFin) {
        this.idTurno = idTurno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.costo=0;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the lcamiones
     */
    public ArrayList<Camion> getLcamiones() {
        return lcamiones;
    }

    /**
     * @param lcamiones the lcamiones to set
     */
    public void setLcamiones(ArrayList<Camion> lcamiones) {
        this.lcamiones = lcamiones;
    }

}
