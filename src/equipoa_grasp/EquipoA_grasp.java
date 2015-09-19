/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipoa_grasp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.*;
import util.*;
import java.util.ArrayList;
import java.util.Date;
import model.Constantes.EasyGas;

/**
 *
 * @author a20080480
 */
public class EquipoA_grasp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        Lector l = new Lector();
        
        ArrayList<Camion> c = new ArrayList<Camion>();
        ArrayList<Pedido> p = new ArrayList<Pedido>();
        //setear turnos, son solo tres
        //****************************************Primer turno**************************/
        String horaStringInicio="00:00:00";
        String horaStringFin="07:59:59";
        Date dHoraInicio = new SimpleDateFormat("HH:mm:ss").parse(horaStringInicio);
        Date dHoraFin = new SimpleDateFormat("HH:mm:ss").parse(horaStringFin);
        Turno t = new Turno(1,dHoraInicio,dHoraFin);
        //**************************************Segundo turno**************************/
        horaStringInicio="08:00:00";
        horaStringFin="15:59:59";
        dHoraInicio = new SimpleDateFormat("HH:mm:ss").parse(horaStringInicio);
        dHoraFin = new SimpleDateFormat("HH:mm:ss").parse(horaStringFin);
        EasyGas.lturnos.add(t);
        //**************************************tercer turno**************************/
        horaStringInicio="16:00:00";
        horaStringFin="23:59:59";
        dHoraInicio = new SimpleDateFormat("HH:mm:ss").parse(horaStringInicio);
        dHoraFin = new SimpleDateFormat("HH:mm:ss").parse(horaStringFin);
        EasyGas.lturnos.add(t);
       
        
        l.cargar("dataset.txt", c, p);
        double alpha = 0.3;
        int nIteraciones = 3000;
        
        while(true){
            t = obtenerTurnoActual();
            ArrayList<Pedido> pedidosListo= obtenerPedidosListos(p,t);
            for (int i = 0; i < 10; i++) {
                Grasp g = new Grasp();
                g.setAlpha(alpha);
                g.setCamiones(c);
                g.setPedidos(p);
                g.setNumIteraciones(nIteraciones);
                //double inicio = System.currentTimeMillis();
                ArrayList<Camion> lcamiones=g.correr();
                double fin = System.currentTimeMillis();
                //double tiempoTotal=(fin-inicio)/1000;
                //System.out.printf("Tiempo de ejecucion: %.4f\n",tiempoTotal);
               // r.reporte(v, a);
            }
        }
    }
    
    public ArrayList<Pedido> obtenerPedidosListos(ArrayList<Pedido> lpedidos,Turno t){
        ArrayList<Pedido> pedidosListo= new ArrayList<Pedido>();
        int cantPedidos=lpedidos.size();
        for(int i=0;i<cantPedidos;i++){
            if(perteneceATurno(lpedidos.get(i).getHoraSolicitada(),t)) pedidosListo.add(lpedidos.get(i));
        }
        return pedidosListo;
    
    }
    
    public Turno obtenerTurnoActual(){
        //String originalString = "2010-07-14 09:00:02";
        //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
        //String newString = new SimpleDateFormat("H:mm").format(date); // 9:00
        Date horaActual = EasyGas.horaActual;
        ArrayList<Turno> lturnos= EasyGas.lturnos;
        for(int i=0;i<3;i++){
           if(perteneceATurno(horaActual,lturnos.get(i))) ;
           return lturnos.get(i);
          
        }
        return null;
    
    }
    
    public boolean perteneceATurno(Date hora,Turno turno){
        Date horaActual = EasyGas.horaActual;
        return (horaActual.after(turno.getHoraInicio()) && horaActual.before(turno.getHoraFin()));
      
    }
}
