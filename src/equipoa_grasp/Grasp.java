/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipoa_grasp;

import java.util.ArrayList;
import model.*;
import model.Constantes.EasyGas;
/**
 *
 * @author a20080480
 */
public class Grasp {
    private ArrayList<Camion> camiones;
    private ArrayList<Camion> nCamiones;
    private ArrayList<Camion> sol;
    private ArrayList<Camion> camiones2OPT;
    private ArrayList<Pedido> pedidos;
    private double alpha; 
    private int numIteraciones;
    private double maximo;
    private double minimo;
    private Nodo nodoInicio;
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
    
    public ArrayList<Camion> correr(){
    
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
            //    public Pedido(int idPedido, Date fechaRegistro, int horaSolicitada, int cantGLP, String estado, String prioridad,Cliente idCliente) {
            for (int b=0; b<cantCamiones;b++) 
                lpedidos.add(new Pedido(pedidos.get(b).getIdPedido(),pedidos.get(b).getFechaRegistro(),pedidos.get(b).getHoraSolicitada(),
                pedidos.get(b).getCantGLP(),pedidos.get(b).getEstado(),pedidos.get(b).getPrioridad(),pedidos.get(b).getIdCliente()));
            
            inicializar(lpedidos); // calcula valar minimo y maximo
            nCamiones = iniciarFaseConstructiva(lcamiones,lpedidos);
            //vehicles2OPT = nVehicles;
            //ETAPA DE MEJORA 2OPT
            //util.Reader r = new util.Reader();
//            System.out.println("CostoTotal " + costoTotal(nVehicles));
            //r.reporte(nVehicles);
            //System.out.println("CostoTotal " + costoRuta(nVehicles.get(0)));
            camiones2OPT = iniciar2OPT(nCamiones);
            //r.reporte(vehicles2OPT);
            //System.out.println("CostoTotal " + costoRuta(vehicles2OPT.get(0)));
//            System.out.println("CostoTotal " + costoTotal(vehicles2OPT));            
            if (i == 0)
            {
                sol = camiones2OPT;
            }
            else
            {
                double aux1= obtenerCostoTotal(camiones2OPT);
                double aux2= obtenerCostoTotal (sol);
                if (aux1 <= aux2)
                {
                    /*
                    System.out.println("c nueva " + aux1);
                    System.out.println("c antigua " + aux2);
                    System.out.println("Cambia iteraci" + i);*/
                    sol = camiones2OPT;
                }
            }
        }	
        return sol;
    
    
    
    
    }
    
     public double obtenerCostoArista (Nodo nodoIni, Nodo nodoFin)
    {
           
            double x2 = (nodoFin.getCoordX()-nodoIni.getCoordX())*(nodoFin.getCoordX()-nodoIni.getCoordX());
            double y2 = (nodoFin.getCoordY()-nodoIni.getCoordY())*(nodoFin.getCoordY()-nodoIni.getCoordY());
            return Math.sqrt(x2+y2)/EasyGas.velocidad;
            
    }
     
     public ArrayList<Camion> iniciarFaseConstructiva(ArrayList<Camion>lcamiones,ArrayList<Pedido>lpedidos){
            int cantCamiones=lcamiones.size();
            int j=1;
            while(j<cantCamiones && hayAtendidos(lpedidos)){
                Camion c= lcamiones.get(j);
                nodoInicio=EasyGas.central;
                ArrayList<Camion> LCR= new ArrayList<Camion>();
                while(hayCapacidadSuficiente(c,lpedidos)){
                   inicializar(lpedidos);
                   LCR=obtenerLCR(lpedidos);
                   Pedido p=obtenerPedidoRandom(LCR);
                   if(p.getCantGLP()<=c.getIdTipoCamion().getCapacidadGLP()){
                       agregarSolucion(c,p);
                       nodoInicio=p.getIdNodo();
                   }
                }
                j++;
           }
           return lcamiones;
    }
    
    public ArrayList<Camion> iniciar2OPT(ArrayList<Camion>nCamiones){
        int n = nCamiones.size();

        Camion camion = null;

        ArrayList<Camion> nuevoCamiones=new ArrayList<Camion>();
        for (int i=0;i<n;i++)   {
            camion = nCamiones.get(i);
            //HACER 2OPT
            nuevoCamiones.add(iniciar2OPTCamion(camion));//mejora ves por referencia
        }
        return nuevoCamiones;
     
     
     
    }
    
    public Camion iniciar2OPTCamion(Camion c){
        //ordena sus pedidos para tener un mejor costo 
        return c;
    
    
    }


    
    public void obtenerLCR(ArrayList<Pedido>lpedidos){
        ArrayList<Client> nuevos=new ArrayList<Client>();
        double costoAux = 0;
        int length = clients.size();
        for (int i=0;i<length;i++)
        {
            if (clients.get(i).isAtendido()==false){
                costoAux =costoArista(clients.get(i).getPosX(),
                        clients.get(i).getPosy(),posX,posY); 
                if (costoAux <= beta + alfa*(tau-beta))
                        nuevos.add(clients.get(i));
            }
        }
        return nuevos;
     
     
     
    } 
     
    public double obtenerCostoTotal (ArrayList<Camion> lcamiones)
    {
        double costoAux=0;
        int cantCamion = lcamiones.size();
        for (int i=0;i<cantCamion;i++)
        {
            costoAux+=obtenerCostoRuta(lcamiones.get(i));
        
        }
        return costoAux;
    }
    public double obtenerCostoRuta(Camion c){
        double costo=0;
        int cantPedidos= c.getRuta().getLaristas().size()+1;
        if (cantPedidos ==0) return 0;
        Nodo nodoInicio=c.getRuta().getLaristas().get(0).getNodoOrigen();
        Nodo nodoFin;
        for (int i = 0;i<cantPedidos-1;i++)
        {
            nodoFin=c.getRuta().getLaristas().get(i).getNodoDestino();
            costo = costo + obtenerCostoArista(nodoInicio,nodoFin);
            nodoInicio=c.getRuta().getLaristas().get(i+1).getNodoOrigen();
        }
        
         
        return costo;
    }
    
    public void inicializar(ArrayList<Pedido> lpedidos){
        
            minimo = 0;
            maximo= 0;
            int cantPedidos = lpedidos.size();
            int flag=0;
            double costoAux;
            for (int i=0;i<cantPedidos;i++)
            {
                    if (lpedidos.get(i).getEstado().equals(new String("atendido"))==false){
                            costoAux=obtenerCostoArista(nodoInicio,lpedidos.get(i).getIdNodo());
                            if (flag == 0){
                                    maximo= costoAux;
                                    minimo = costoAux;
                                    flag =1;
                            }else
                            {
                                    if (costoAux<minimo) 
                                            minimo=costoAux;
                                    if (costoAux>maximo) 
                                            maximo=costoAux;
                            }
                    }	
            }
           
    
    
    
    }
}


