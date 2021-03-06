/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.*;
import equipoa_grasp.*;
import model.Constantes.EasyGas;

/**
 *
 * @author a20080480
 */
public class Lector {
    public void cargar(String nombreArchivo,ArrayList<Camion> camiones,ArrayList<Pedido> pedidos){
         try{            
           
            FileInputStream fistream = new FileInputStream(nombreArchivo);
            DataInputStream in = new DataInputStream(fistream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String fullLine = br.readLine();
            String[] lineRead = fullLine.split("\\s+");
             //leer cantidad de velocidad
            EasyGas.velocidad = Integer.parseInt(lineRead[0]);
            //leer tipos de camiones
            // cantidad,tara,capacidadGLP,capacidadDiesel

            for(int i=0;i<4;i++){
               fullLine = br.readLine();
               lineRead = fullLine.split("\\s+");
               int cantidad = Integer.parseInt(lineRead[0]);
               int taraTon = Integer.parseInt(lineRead[1]);
               int capacidadGLP = Integer.parseInt(lineRead[2]);
               int capacidadDiesel = Integer.parseInt(lineRead[3]);
               
               //  public TipoCamion(int idTipoCamion, int capacidadDiesel, int capacidadGLP,int taraTon) {
               TipoCamion tc= new TipoCamion(i+1,capacidadDiesel,capacidadGLP,taraTon);
               for(int j=0;j<cantidad;j++){
                    Camion c = new Camion(j+1,tc);   
                    camiones.add(c);
               }
            
            }
            
            //leer coordenadas de central de distribucion            
            fullLine = br.readLine();
            lineRead = fullLine.split("\\s+");
            EasyGas.central = new Nodo(1,Integer.parseInt(lineRead[0]), Integer.parseInt(lineRead[1]));
           
            
            fullLine = br.readLine();
            lineRead = fullLine.split("\\s+");
            
            //leer cantidad de pedidos
            int cantPedidos = Integer.parseInt(lineRead[0]);

           
            // hora solicitada, cant,posx,posy,tipopersona(1 natural,2 juridica)
            int posX,posY,tipoPersona;
            double cantGLP;
            String horaSolicitada;
            Date ahora= new Date();
            String ahoraString = new SimpleDateFormat("yyyy-MM-dd").format(ahora);
            for (int i=0;i<cantPedidos;i++){
                fullLine = br.readLine();
                lineRead = fullLine.split("\\s+");
                horaSolicitada=ahoraString +" "+lineRead[0];
                //System.out.println(horaSolicitada);
                cantGLP=Double.parseDouble(lineRead[1]);
                posX=Integer.parseInt(lineRead[2]);
                posY=Integer.parseInt(lineRead[3]);
                tipoPersona=Integer.parseInt(lineRead[4]);
                //public Pedido(int idPedido, Date fechaRegistro, int horaSolicitada, int cantGLP, String estado, String prioridad) {
                Cliente c = new Cliente();
                c.setEsPersonaNatural(tipoPersona==1?true:false);
                Date dataHoraSolicitada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(horaSolicitada);
                Pedido p = new Pedido(i+1,null,dataHoraSolicitada,cantGLP,"no atendido","no tiene",c);
                Nodo n = new Nodo(1,posX,posY);
                p.getIdCliente().setIdNodo(n);
                pedidos.add(p);
                
            }
 
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
    }
    public void obtenerReporte(ArrayList<Camion> camiones){
    
    
            Grasp g = new Grasp();
            double costo = g.obtenerCostoTotal(camiones);
            System.out.println("Costo total es" + costo);
    
    }
   
}
