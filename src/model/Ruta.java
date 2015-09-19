/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author alulab14
 */
public class Ruta {
    private ArrayList<Arista> laristas;

    /**
     * @return the laristas
     */
    public ArrayList<Arista> getLaristas() {
        return laristas;
    }

    /**
     * @param laristas the laristas to set
     */
    public void setLaristas(ArrayList<Arista> laristas) {
        this.laristas = laristas;
    }
}
