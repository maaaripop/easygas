/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author a20080480
 */
public class Camion {
   private int idCamion;
   private TipoCamion idTipoCamion;

    /**
     * @return the idCamion
     */
    public int getIdCamion() {
        return idCamion;
    }
    public Camion(int idCamion,TipoCamion idTipoCamion){
        this.idCamion=idCamion;
        this.idTipoCamion=idTipoCamion;
    
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
}

  