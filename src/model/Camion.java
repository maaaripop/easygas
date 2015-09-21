/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import model.Constantes.EasyGas;

/**
 *
 * @author a20080480
 */
public class Camion {
   private int idCamion;
   private TipoCamion idTipoCamion;
   private Ruta ruta;
   private double cantGLPActual;
   private double cantDieselActual;
   private double kilometrosRecorridos;
   
   public void agregarRuta(ArrayList<Pedido> lpedidos){
       Ruta r = new Ruta();
       int cantPedidos=lpedidos.size();
       ArrayList<Arista> laristas= new ArrayList<Arista>();
       Arista a= new Arista();
       a.setNodoOrigen(EasyGas.central);
       for(int i=0;i<cantPedidos;i++){
           a.setNodoDestino(lpedidos.get(i).getIdCliente().getIdNodo());
           laristas.add(a);
           a.setNodoOrigen(lpedidos.get(i).getIdCliente().getIdNodo());
       }
       r.setLaristas(laristas);
       this.ruta=r;
   }
    /**
     * @return the idCamion
     */
    public int getIdCamion() {
        return idCamion;
    }
    public Camion(int idCamion,TipoCamion idTipoCamion){
        this.idCamion=idCamion;
        this.idTipoCamion=idTipoCamion;
        this.cantGLPActual=0;
        this.cantDieselActual=0;
        this.kilometrosRecorridos=0;
    
    }
    /**
     * @param idCamion the idCamion to set
     */
    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    /**
     * @return the idTipoCamion
     */
    public TipoCamion getIdTipoCamion() {
        return idTipoCamion;
    }

    /**
     * @param idTipoCamion the idTipoCamion to set
     */
    public void setIdTipoCamion(TipoCamion idTipoCamion) {
        this.idTipoCamion = idTipoCamion;
    }

    /**
     * @return the ruta
     */
    public Ruta getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the cantGLPActual
     */
    public double getCantGLPActual() {
        return cantGLPActual;
    }

    /**
     * @param cantGLPActual the cantGLPActual to set
     */
    public void setCantGLPActual(double cantGLPActual) {
        this.cantGLPActual = cantGLPActual;
    }

    /**
     * @return the cantDiesel
     */
    public double getcantDieselActual() {
        return getCantDieselActual();
    }

    /**
     * @param cantDiesel the cantDiesel to set
     */
    public void setcantDieselActual(double cantDieselActual) {
        this.setCantDieselActual(cantDieselActual);
    }

    /**
     * @return the cantDieselActual
     */
    public double getCantDieselActual() {
        return cantDieselActual;
    }

    /**
     * @param cantDieselActual the cantDieselActual to set
     */
    public void setCantDieselActual(double cantDieselActual) {
        this.cantDieselActual = cantDieselActual;
    }

    /**
     * @return the kilometrosRecorridos
     */
    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    /**
     * @param kilometrosRecorridos the kilometrosRecorridos to set
     */
    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }
}

  