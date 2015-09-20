/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author a20080480
 */
public class Pedido {
   
        int idPedido;
        Date fechaRegistro;
        Date fechaEntrega;
        Date horaSolicitada;
        double cantGLP;
        String estado;
        String prioridad;
        Nodo idNodo;
        private Cliente idCliente;
        
   

    public Pedido(int idPedido, Date fechaRegistro, Date horaSolicitada, double cantGLP, String estado, String prioridad,Cliente idCliente) {
        this.idPedido = idPedido;
        this.fechaRegistro = fechaRegistro;
        this.horaSolicitada = horaSolicitada;
        this.cantGLP = cantGLP;
        this.estado = estado;
        this.prioridad = prioridad;
        this.idCliente=idCliente;
    }
    
     public Nodo getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(Nodo idNodo) {
        this.idNodo = idNodo;
    }

  
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getHoraSolicitada() {
        return horaSolicitada;
    }

    public void setHoraSolicitada(Date horaSolicitada) {
        this.horaSolicitada = horaSolicitada;
    }

    public double getCantGLP() {
        return cantGLP;
    }

    public void setCantGLP(double cantGLP) {
        this.cantGLP = cantGLP;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the idCliente
     */
    public Cliente getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

   
   


    
}
