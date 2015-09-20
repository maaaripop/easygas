/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipoa_grasp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.*;
import model.Constantes.EasyGas;
import util.*;

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
        Date ahora= new Date();
        String ahoraString = new SimpleDateFormat("yyyy-MM-dd").format(ahora);
        String horaStringInicio=ahoraString + " 00:00:00";
        String horaStringFin=ahoraString +" 07:59:59";
        Date dHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringInicio);
        Calendar cInicio= Calendar.getInstance();
        cInicio.setTime(dHoraInicio);
        cInicio.add(Calendar.DATE, 0);
       
        Date dHoraFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringFin);
        Calendar cFin= Calendar.getInstance();
        cFin.setTime(dHoraFin);
        cFin.add(Calendar.DATE, 0);
        Turno t = new Turno(1,cInicio.getTime(),cFin.getTime());
        EasyGas.lturnos.add(t);
        //**************************************Segundo turno**************************/
        horaStringInicio=ahoraString + " 08:00:00";
        horaStringFin=ahoraString + " 15:59:59";
        dHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringInicio);
        cInicio= Calendar.getInstance();
        cInicio.setTime(dHoraInicio);
        cInicio.add(Calendar.DATE, 0);
        dHoraFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringFin);
        cFin= Calendar.getInstance();
        cFin.setTime(dHoraFin);
        cFin.add(Calendar.DATE, 0);
        t = new Turno(1,cInicio.getTime(),cFin.getTime());
        EasyGas.lturnos.add(t);
        //**************************************tercer turno**************************/
        horaStringInicio=ahoraString + " 16:00:00";
        horaStringFin=ahoraString + " 23:59:59";
        dHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringInicio);
        cInicio= Calendar.getInstance();
        cInicio.setTime(dHoraInicio);
        cInicio.add(Calendar.DATE, 0);
        dHoraFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringFin);
        cFin= Calendar.getInstance();
        cFin.setTime(dHoraFin);
        cFin.add(Calendar.DATE, 0);
        t = new Turno(1,cInicio.getTime(),cFin.getTime());
        //System.out.println(ahoraString);
        EasyGas.lturnos.add(t);
        
        
        l.cargar("dataset.txt", c, p);
        
        Reloj r = new Reloj();
       
        double alpha = 0.3;
        int nIteraciones = 1;
        
        while(true){
            if(Reloj.horaActual.equals(dHoraFin)) break;
            t = obtenerTurnoActual();
            int cantListos=0;
            if(t!=null) cantListos=obtenerPedidosListos(p,t);
            
            //System.out.println(Reloj.horaActual.getTime());
            //if (cantListos==0) System.out.println("No hay pedidos para este turno aun");
            
            if(cantListos!=0){
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
    }
    
    public static int obtenerPedidosListos(ArrayList<Pedido> lpedidos,Turno t){
        ArrayList<Pedido> pedidosListo= new ArrayList<Pedido>();
        int cantPedidos=lpedidos.size();
        int cantListo=0;
        
        for(int i=0;i<cantPedidos;i++){
            Pedido p = lpedidos.get(i);
            if(perteneceATurno(p.getHoraSolicitada(),t)) {
                
                if(!p.getEstado().equals(new String("atendido"))){
                    if(EasyGas.lturnos.get(0).equals(t)&& p.getIdCliente().isEsPersonaNatural())  p.setPrioridad("tiene");
                    if(EasyGas.lturnos.get(2).equals(t)&& !p.getIdCliente().isEsPersonaNatural()) p.setPrioridad("tiene");
                    lpedidos.get(i).setEstado("listo");
                    cantListo++;
                }
            }
            
        }
        return cantListo;
    
    }
    
    public static Turno obtenerTurnoActual(){
        //String originalString = "2010-07-14 09:00:02";
        //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
        //String newString = new SimpleDateFormat("H:mm").format(date); // 9:00
        Date horaActual = Reloj.horaActual.getTime();
        ArrayList<Turno> lturnos= EasyGas.lturnos;
        //System.out.println(horaActual);
        for(int i=0;i<3;i++){
          
           if(perteneceATurno(horaActual,lturnos.get(i))) return lturnos.get(i);
          
        }
        return null;
    
    }
    
    public static boolean perteneceATurno(Date hora,Turno turno){
        return hora.after(turno.getHoraInicio()) && hora.before(turno.getHoraFin());
      
    }
}
