/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipoa_grasp;
import model.*;
import util.*;
import java.util.ArrayList;

/**
 *
 * @author a20080480
 */
public class EquipoA_grasp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Lector l = new Lector();
        
        ArrayList<Camion> c = new ArrayList<Camion>();
        ArrayList<Pedido> p = new ArrayList<Pedido>();
        l.cargar("dataset.txt", c, p);
        double alpha = 0.3;
        int nIteraciones = 3000;
        for (int i = 0; i < 10; i++) {
            Grasp g = new Grasp();
            g.setAlpha(alpha);
            g.setCamiones(c);
            g.setPedidos(p);
            g.setNumIteraciones(nIteraciones);
            //double inicio = System.currentTimeMillis();
            g.correr();
            double fin = System.currentTimeMillis();
            //double tiempoTotal=(fin-inicio)/1000;
            //System.out.printf("Tiempo de ejecucion: %.4f\n",tiempoTotal);
           // r.reporte(v, a);
        }
    }
}
