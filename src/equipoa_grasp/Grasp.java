/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipoa_grasp;

import java.util.ArrayList;
import model.*;
/**
 *
 * @author a20080480
 */
public class Grasp {
    private ArrayList<Camion> camiones;
    private ArrayList<Pedido> pedidos;
    private double alpha; 
    private int numIteraciones;

    /**
     * @return the camiones
     */
    public ArrayList<Camion> getCamiones() {
        return camiones;
    }

    /**
     * @param camiones the camiones to set
     */
    public void setCamiones(ArrayList<Camion> camiones) {
        this.camiones = camiones;
    }

    /**
     * @return the pedidos
     */
    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos the pedidos to set
     */
    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    
   /**
     * @return the numIteraciones
     */
    public int getNumIteraciones() {
        return numIteraciones;
    }

    /**
     * @param numIteraciones the numIteraciones to set
     */
    public void setNumIteraciones(int numIteraciones) {
        this.numIteraciones = numIteraciones;
    }
    
    public void cargar(){
    
    for (int i =0;i<numIteraciones; i++)
        {
            //ETAPA DE CONSTRUCCION
            //Se trabajara con copias de los arreglos auxiliares
            ArrayList<Camion> lcamiones= new ArrayList<Camion>();
            int cantCamiones = camiones.size();
            for (int a=0; a<cantCamiones;a++) 
                lcamiones.add(new Camion(camiones.get(a).getIdCamion(),camiones.get(a).getIdTipoCamion()));
            ArrayList<Pedido> lpedidos= new ArrayList<Pedido>();
            int cantPedidos = pedidos.size();
            for (int b=0;b<sc;b++) cl.add(new Client(c.get(b)));
            
            Inicializar(cl,ve);
            this.almacen =almacen;
            nVehicles = Construction(ve,cl,almacen,alfa);
            //vehicles2OPT = nVehicles;
            //ETAPA DE MEJORA 2OPT
            util.Reader r = new util.Reader();
//            System.out.println("CostoTotal " + costoTotal(nVehicles));
            //r.reporte(nVehicles);
            //System.out.println("CostoTotal " + costoRuta(nVehicles.get(0)));
            vehicles2OPT = Improve2OPT(nVehicles);
            //r.reporte(vehicles2OPT);
            //System.out.println("CostoTotal " + costoRuta(vehicles2OPT.get(0)));
//            System.out.println("CostoTotal " + costoTotal(vehicles2OPT));            
            if (i == 0)
            {
                sol = vehicles2OPT;
            }
            else
            {
                double aux1= costoTotal(vehicles2OPT);
                double aux2= costoTotal (sol);
                if (aux1 <= aux2)
                {
                    /*
                    System.out.println("c nueva " + aux1);
                    System.out.println("c antigua " + aux2);
                    System.out.println("Cambia iteraci" + i);*/
                    sol = vehicles2OPT;
                }
            }
        }	
        return sol;
    
    
    
    
    }
}


