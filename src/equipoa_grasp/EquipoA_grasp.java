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
import javax.swing.JFrame;
import javax.swing.WindowConstants;
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
        Date dHoraFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringFin);
        Turno t = new Turno(1,dHoraInicio,dHoraFin);
        EasyGas.lturnos.add(t);
        //**************************************Segundo turno**************************/
        horaStringInicio=ahoraString + " 08:00:00";
        horaStringFin=ahoraString + " 15:59:59";
        dHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringInicio);
        dHoraFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringFin);
        t = new Turno(2,dHoraInicio,dHoraFin);
        EasyGas.lturnos.add(t);
        //**************************************tercer turno**************************/
        horaStringInicio=ahoraString + " 16:00:00";
        horaStringFin=ahoraString + " 23:59:59";
        dHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringInicio);
        dHoraFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaStringFin);
        t = new Turno(3,dHoraInicio,dHoraFin);
        //System.out.println(ahoraString);
        EasyGas.lturnos.add(t);
        /********************* fin de insertar turnos*******************************/
        
        //System.out.println("Cargar " + obtenerPedidosNoAtendidos(p,t) );
                
        // cargar la lista de camiones y pedidos
        
        Date horaActual= new Date();
        String horaActualString = new SimpleDateFormat("yyyy-MM-dd").format(ahora);
        String horaActualStringFinal= horaActualString + " 06:40:00";
        EasyGas.horaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaActualStringFinal);
        t= EasyGas.lturnos.get(0);
        
        
        for(int i=0;i<1;i++){
            int cantAtendidos=0; 
            String nombreArchivo="dataset-9.txt";
            l.cargar(nombreArchivo, c, p);
            obtenerPedidosListos(p,t);
            double alpha = 0.3;
            int nIteraciones=3000;
            ArrayList<Camion> lcamiones=new ArrayList<Camion>();
            Grasp g = new Grasp();
            g.setAlpha(alpha);
            g.setCamiones(c);
            g.setPedidos(p);
            g.setNumIteraciones(nIteraciones);
            lcamiones=g.correr();
            ArrayList<Camion> lresultados =  lcamiones;
           
            for(int z=0;z<lresultados.size();z++){
                Ruta ruta = lresultados.get(z).getRuta();
                if(ruta!=null){
                    ArrayList <Arista> laristas= ruta.getLaristas();
                    for(int w=0;w<laristas.size();w++){
                      //  System.out.print(laristas.get(w).getNodoDestino().getPedido().getIdPedido() + " -> ");
                        cantAtendidos++;
                    }

                }
            }
            System.out.println("");
            System.out.println("Cant Pedidos atendidos " + cantAtendidos);
            System.out.println("Costo " + g.obtenerCostoTotal(lcamiones));

        }
        
        /*
        
        Reloj r = new Reloj();
        t = obtenerTurnoActual();
        EasyGas.lturnos.get(t.getIdTurno()-1).setLcamiones(c);
        double alpha = 0.3;
        int nIteraciones = 3000;
        System.out.println(dHoraFin);
        while(true){
            ArrayList<Camion> lcamiones= new ArrayList<Camion>();
            if(obtenerPedidosAtendidos(p,t)==p.size()) {
                for(int y=0;y<3;y++){
                    System.out.println("Costo del turno " + EasyGas.lturnos.get(y).getCosto());
                    ArrayList<Camion> lresultados =  EasyGas.lturnos.get(y).getLcamiones();
                    for(int z=0;z<lresultados.size();z++){
                        Ruta ruta = lresultados.get(z).getRuta();
                        if(ruta!=null){
                            ArrayList <Arista> laristas= ruta.getLaristas();
                            for(int w=0;w<laristas.size();w++){
                                System.out.print(laristas.get(w).getNodoDestino().getPedido().getIdPedido() + " -> ");
                            }
                            
                        }
                    }
                    System.out.println(" ");
                }
                System.out.println("Fin del dia");
                return;
            }
            Turno t2 = obtenerTurnoActual();

            int cantListos=0;
            if(t2!=null) {
                        if(!t.equals(t2)) {
                            System.out.println("Cambio de turno");
                            EasyGas.lturnos.get(t2.getIdTurno()-1).setLcamiones(c); //seteo nueva flota (camiones limpios)
                            t=t2;
                        } else { // setteo los camiones con EsUtilizado=false; para volver utilizar el grasp
                            int cantUtilizados= EasyGas.lturnos.get(t2.getIdTurno()-1).getLcamiones().size();
                            for(int y=0;y<cantUtilizados;y++) EasyGas.lturnos.get(t2.getIdTurno()-1).getLcamiones().get(y).setEsUtilizado(false);

                        }
                        cantListos=obtenerPedidosListos(p,t2);
            }
           // if(cantListos!=0) System.out.println("cant listos " +cantListos);
            //System.out.println(Reloj.horaActual.getTime());
            
            
           

            if(cantListos!=0){
               // System.out.println("turno " + t.getIdTurno() + " correr el grasp para " +cantListos );
              //  System.out.println("No Atendidos " + obtenerPedidosNoAtendidos(p,t) );
                    Grasp g = new Grasp();
                    g.setAlpha(alpha);
                    g.setCamiones(EasyGas.lturnos.get(t2.getIdTurno()-1).getLcamiones());
                  //  g.setCamiones(c);
                    g.setPedidos(p);
                    g.setNumIteraciones(nIteraciones);
                    //double inicio = System.currentTimeMillis();
                    lcamiones=g.correr();
                    EasyGas.lturnos.get(t2.getIdTurno()-1).setLcamiones(lcamiones);
                    p=g.getPedidos();
                    EasyGas.lturnos.get(t2.getIdTurno()-1).setCosto(g.obtenerCostoTotal(EasyGas.lturnos.get(t2.getIdTurno()-1).getLcamiones()));
                    //double tiempoTotal=(fin-inicio)/1000;
                    //System.out.printf("Tiempo de ejecucion: %.4f\n",tiempoTotal);
                    System.out.println("Atendidos " + obtenerPedidosAtendidos(p,t2) );   
                    
               // }
              //  l.obtenerReporte(lcamiones);
            }
            
        }*/
    }
    
    public static int obtenerPedidosListos(ArrayList<Pedido> lpedidos,Turno t){
        ArrayList<Pedido> pedidosListo= new ArrayList<Pedido>();
        int cantPedidos=lpedidos.size();
        int cantListo=0;
        
        for(int i=0;i<cantPedidos;i++){
            Pedido p = lpedidos.get(i);
            if(EasyGas.lturnos.get(1).equals(t))  p.setPrioridad("no tiene");
            if(EasyGas.lturnos.get(0).equals(t)&& p.getIdCliente().isEsPersonaNatural())  p.setPrioridad("tiene");
            if(EasyGas.lturnos.get(2).equals(t)&& !p.getIdCliente().isEsPersonaNatural()) p.setPrioridad("tiene");
            lpedidos.get(i).setEstado("listo");
            cantListo++;
            
            /*            if(perteneceATurno(p.getHoraSolicitada(),t)) {
                
                if(p.getEstado().equals(new String("no atendido")) || p.getEstado().equals(new String("listo"))){
                    //System.out.println("No atendido");
                    if(EasyGas.lturnos.get(1).equals(t))  p.setPrioridad("no tiene");
                    if(EasyGas.lturnos.get(0).equals(t)&& p.getIdCliente().isEsPersonaNatural())  p.setPrioridad("tiene");
                    if(EasyGas.lturnos.get(2).equals(t)&& !p.getIdCliente().isEsPersonaNatural()) p.setPrioridad("tiene");
                    lpedidos.get(i).setEstado("listo");
                    cantListo++;
                }
            }*/
            
        }
        return cantListo;
    
    }
    
    public static int obtenerPedidosNoAtendidos(ArrayList<Pedido> lpedidos,Turno t){
        ArrayList<Pedido> pedidosListo= new ArrayList<Pedido>();
        int cantPedidos=lpedidos.size();
        int cantNoAtendidos=0;
        
        for(int i=0;i<cantPedidos;i++){
            Pedido p = lpedidos.get(i);
            
                
                if(p.getEstado().equals(new String("no atendido"))){
                   cantNoAtendidos++;
                }
            
            
        }
        return cantNoAtendidos;
    
    }    
    public static int obtenerPedidosAtendidos(ArrayList<Pedido> lpedidos,Turno t){
        ArrayList<Pedido> pedidosListo= new ArrayList<Pedido>();
        int cantPedidos=lpedidos.size();
        int cantListo=0;
        
        for(int i=0;i<cantPedidos;i++){
            Pedido p = lpedidos.get(i);
                
                if(p.getEstado().equals(new String("atendido"))){
                   cantListo++;
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
