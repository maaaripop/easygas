/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author a20080480
 */
public class Cliente {
    int idCliente;
    Date fechaRegistro;
    Nodo idNodo;
    String estado;
    PersonaJuridica idPersonaJuridica;
    private PersonaNatural idPersonaNatural;
    private boolean esPersonaNatural;
    
    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    public Cliente(){
    
    
    
    }
    public Cliente(Integer idCliente, Date fechaRegistro, String estado) {
        this.idCliente = idCliente;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }
    
    
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Nodo getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(Nodo idNodo) {
        this.idNodo = idNodo;
    }

    public PersonaJuridica getIdPersonaJuridica() {
        return idPersonaJuridica;
    }

    public void setIdPersonaJuridica(PersonaJuridica idPersonaJuridica) {
        this.idPersonaJuridica = idPersonaJuridica;
    }

    public PersonaNatural getIdPersona() {
        return getIdPersonaNatural();
    }

    public void setIdPersona(PersonaNatural idPersonaNatural) {
        this.setIdPersonaNatural(idPersonaNatural);
    }

    /**
     * @return the esPersonaNatural
     */
    public boolean isEsPersonaNatural() {
        return esPersonaNatural;
    }

    /**
     * @param esPersonaNatural the esPersonaNatural to set
     */
    public void setEsPersonaNatural(boolean esPersonaNatural) {
        this.esPersonaNatural = esPersonaNatural;
    }

    /**
     * @return the idPersonaNatural
     */
    public PersonaNatural getIdPersonaNatural() {
        return idPersonaNatural;
    }

    /**
     * @param idPersonaNatural the idPersonaNatural to set
     */
    public void setIdPersonaNatural(PersonaNatural idPersonaNatural) {
        this.idPersonaNatural = idPersonaNatural;
    }
}
