/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author alulab14
 */
public class Turno {
    int idTurno;
    Date horaInicio;
    Date horaFin;
    public Turno() {
    }

    public Turno(Integer idTurno) {
        this.idTurno = idTurno;
    }
   
    public Turno(Integer idTurno, Date horaInicio, Date horaFin) {
        this.idTurno = idTurno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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

}
