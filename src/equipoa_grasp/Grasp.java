/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipoa_grasp;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.*;
import util.*;
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
    int i=0;
    ArrayList<Pedido> lpedidos= new ArrayList<Pedido>();
    ArrayList<Camion> lcamiones= new ArrayList<Camion>();
    double aux1=0;
    double aux2=0;
    for (i =0;i<numIteraciones; i++){
            //System.out.println("iteracion no " + i);
            //Se trabajara con copias de los arreglos auxiliares
            lpedidos= new ArrayList<Pedido>();
            lcamiones= new ArrayList<Camion>();
            int cantCamiones = camiones.size();
            
            for (int a=0; a<cantCamiones;a++) 
                lcamiones.add(new Camion(camiones.get(a).getIdCamion(),camiones.get(a).getIdTipoCamion()));
            int cantPedidos = pedidos.size();
            for (int b=0; b<cantPedidos;b++) 
                lpedidos.add(new Pedido(pedidos.get(b).getIdPedido(),pedidos.get(b).getFechaRegistro(),pedidos.get(b).getHoraSolicitada(),
                pedidos.get(b).getCantGLP(),pedidos.get(b).getEstado(),pedidos.get(b).getPrioridad(),pedidos.get(b).getIdCliente()));
            nodoInicio=EasyGas.central;
            nCamiones = iniciarFaseConstructiva(lcamiones,lpedidos);
            camiones2OPT = iniciar2OPT(nCamiones);
            //System.out.println("CostoTotal " + costoRuta(vehicles2OPT.get(0)));
//            System.out.println("CostoTotal " + costoTotal(vehicles2OPT));            
            if (i == 0) sol = camiones2OPT;
            else
            {
                aux1= obtenerCostoTotal(camiones2OPT);
                aux2= obtenerCostoTotal (sol);
               
                if (aux1 <= aux2)
                {
                    
                    //System.out.println("c nueva " + aux1);
                    //System.out.println("c antigua " + aux2);
                    
                    //System.out.println("Cambia iteraci" + i);*/
                    sol = camiones2OPT;
                }
               // System.out.println("Llegue");
               // System.out.println("iteracion no " + i + " con costo " + aux1);
            }
        }
        System.out.println("costo final " + aux1);
        /*
        pedidos = new ArrayList<Pedido>();
        for(int y=0;y<lpedidos.size();y++){
            //if(lpedidos.get(y).getEstado().equals(new String("atendido"))) System.out.println("ha actualizado");
            pedidos.add(new Pedido(lpedidos.get(y).getIdPedido(),lpedidos.get(y).getFechaRegistro(),lpedidos.get(y).getHoraSolicitada(),
                lpedidos.get(y).getCantGLP(),lpedidos.get(y).getEstado(),lpedidos.get(y).getPrioridad(),lpedidos.get(y).getIdCliente()));
            
        }  */  
        //System.out.println(obtenerCantAtendidos(lpedidos));
        pedidos=lpedidos;
        //System.out.println(obtenerCantAtendidos(pedidos));
        return sol;
    
    
    
    
    }
    
 
     
     public ArrayList<Camion> iniciarFaseConstructiva(ArrayList<Camion>lcamiones,ArrayList<Pedido> lpedidos){
            int cantCamiones=lcamiones.size();
            int j=0;
            
            
            int flag=0;
            while(j<cantCamiones && hayListos(lpedidos)){
                Ruta r = new Ruta();
                Arista a = new Arista();
                ArrayList<Arista> laristas=new ArrayList<Arista>();
                System.out.println("hay por atender");
                Camion c= lcamiones.get(j);
                nodoInicio=EasyGas.central;
                ArrayList<Pedido> LCR= new ArrayList<Pedido>();
                nodoInicio.setHoraLlegada(Reloj.horaActual.getTime()); //hora que sale el camion
                
                while(hayListos(lpedidos) && hayCapacidadSuficiente(c,lpedidos) && dentroDelTurno()){
                  
                   int cantListos=obtenerCantListo(lpedidos);
                   System.out.println("hay "+cantListos + " listos con " + c.getCantGLPActual());
                   a.setNodoOrigen(nodoInicio); 
                   inicializar(c,lpedidos);
                  // System.out.println("Depues de inicializar");
                  //System.out.println("Maximo : " + maximo);
                  // System.out.println("Minimo : " + minimo);
                    if (flag==0){
                        LCR=obtenerLCR(c,1,lpedidos);
                        flag=1;
                    }
                    else{
                        LCR=obtenerLCR(c,alpha,lpedidos);
                    }
                  
                   //System.out.println(LCR.size());
                   Pedido p=obtenerPedidoRandom(LCR);
                   
                   if(p!=null){ 
                      
                       double cantGLPsobrante=(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual());
                       double consumoDieselsobrante= (c.getIdTipoCamion().getCapacidadDiesel()-obtenerConsumo(c));
                        //System.out.println("No "+ p.getIdPedido()+ " tiene pedido de "+ p.getCantGLP()+ " pero me sobra " + cantGLPsobrante);
                       
                       if(p.getCantGLP()<=cantGLPsobrante && obtenerConsumoPedido(c,p)<=consumoDieselsobrante && esTiempoDeEntregar(p,nodoInicio)){
                           
                           c.setCantGLPActual(c.getCantGLPActual()+p.getCantGLP());
                           a.setNodoDestino(p.getIdCliente().getIdNodo());
                           a.getNodoDestino().setPedido(p);
                           a.getNodoDestino().setHoraLlegada(p.getFechaRegistro());
                           a.setCantGLP(p.getCantGLP());
                           c.setCantDieselActual(c.getCantDieselActual()+obtenerConsumoPedido(c,p));
                           laristas.add(a);
                           for(int k=0;k<lpedidos.size();k++){
                               if(lpedidos.get(k).equals(p)){
                                  lpedidos.get(k).setEstado("atendido");
                                  break;
                               }

                           }
                           
                           Calendar horaCalendar= Calendar.getInstance();
                           horaCalendar.setTime(nodoInicio.getHoraLlegada());        
                           horaCalendar.add(Calendar.SECOND,obtenerTiempoEntrega(p,nodoInicio));
                           System.out.println(Reloj.horaActual.getTime() + " camion" +c.getIdCamion() +"  pedido nro " + p.getIdPedido() + " con GLP " + p.getCantGLP() + " hora requerida " + p.getHoraSolicitada() +"  Hora atendido " + horaCalendar.getTime());
                           nodoInicio=p.getIdCliente().getIdNodo();
                           nodoInicio.setHoraLlegada(horaCalendar.getTime());
                           

                       }
                   }
                }
                r.setLaristas(laristas);
                lcamiones.get(j).setRuta(r);
                j++;
           }
           return lcamiones;
    }
    public double obtenerConsumo(Camion c){
        double consumo=0;
        consumo= 0.05 * ( c.getIdTipoCamion().getTaraTon()+c.getCantGLPActual() / 52 )*c.getKilometrosRecorridos();
        return consumo;                    
    
    }
    // no aplica porque si viene un camion nuevo
    public boolean dentroDelTurno(){
        Date horaUltimoPedidoEntregado=nodoInicio.getHoraLlegada();
        boolean resultado= EasyGas.turnoActual.getHoraFin().after(horaUltimoPedidoEntregado) ; 
       // System.out.println(resultado);
        return resultado;
    }
    
    public boolean esTiempoDeEntregar(Pedido p, Nodo nodoIni){
      
        int tiempoSegundos=obtenerTiempoEntrega(p,nodoIni);
        Calendar calendarHoraFin= Calendar.getInstance();
        calendarHoraFin.setTime(nodoIni.getHoraLlegada());        
        calendarHoraFin.add(Calendar.SECOND,tiempoSegundos);
        final long MILLSECS_PER_HOUR = 60 * 60 * 1000; 
        long diferencia =  (calendarHoraFin.getTimeInMillis() - nodoIni.getHoraLlegada().getTime()); 
        long diferenciaLimite=1000*60*60;
        boolean resultado=diferencia<=diferenciaLimite;
        //boolean resultado=calendarHoraFin.getTime().after(p.getHoraSolicitada()) ; // hora fin > horasolicitada
        return resultado;
    
    }
    
    
    public int obtenerTiempoEntrega(Pedido p,Nodo nodoIni){
        double tiempo = obtenerDistancia(nodoIni,p.getIdCliente().getIdNodo())/EasyGas.velocidad*1.0;
        int tiempoSegundos=(int)Math.round(tiempo*60*60 );
        return tiempoSegundos;
    }
    public double obtenerConsumoPedido(Camion c, Pedido p){
    
        double consumo=0.05 * ( (c.getIdTipoCamion().getTaraTon()+c.getCantGLPActual()) / 52 )*obtenerDistancia(nodoInicio,p.getIdCliente().getIdNodo());
        return consumo;
    }
    public int obtenerCantListo(ArrayList<Pedido>lpedidos){
        int cantPedidos=lpedidos.size();
        int cantListos=0;
        for(int i=0;i<cantPedidos;i++){
            //System.out.println(pedidos.get(i).getEstado());
            if(lpedidos.get(i).getEstado().equals(new String("listo"))) cantListos++;
           
        }
        return cantListos;
    
    }
    
    public int obtenerCantAtendidos(ArrayList<Pedido>lpedidos){
        int cantPedidos=lpedidos.size();
        int cantAtendidos=0;
        for(int i=0;i<cantPedidos;i++){
            //System.out.println(pedidos.get(i).getEstado());
            if(lpedidos.get(i).getEstado().equals(new String("listo"))) cantAtendidos++;
           
        }
        return cantAtendidos;
    
    }
    public double obtenerCostoArista (Nodo nodoIni, Nodo nodoFin,Camion c,double cantGLP){
    
            // C = 0.05 x ( PesoTotal / 52 ) Gal/Km

            //donde el PesoTotal = PesoVehículo (llamada tara) + PesoCarga (GLP transportado)
            //Gal = galones 
            //double pesoTotal=c.getIdTipoCamion().getTaraTon()+c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual();
            double pesoTotal=c.getCantGLPActual();
            // MAX = PesoTara + PesoCarga  / distnacia
            //double x2 = (nodoFin.getCoordX()-nodoIni.getCoordX())*(nodoFin.getCoordX()-nodoIni.getCoordX());
            //double y2 = (nodoFin.getCoordY()-nodoIni.getCoordY())*(nodoFin.getCoordY()-nodoIni.getCoordY());
            double distancia=0;
            /*if (nodoIni.getCoordX()==nodoFin.getCoordX())
                    distancia=Math.abs(nodoFin.getCoordY()-nodoIni.getCoordY());
            if (nodoIni.getCoordY()==nodoFin.getCoordY())
                    distancia=Math.abs(nodoFin.getCoordX()-nodoIni.getCoordX());
            if (nodoIni.getCoordY()!=nodoFin.getCoordY() || nodoIni.getCoordX()!=nodoFin.getCoordX())
                    distancia=Math.abs(nodoFin.getCoordX()-nodoIni.getCoordX())+
                              Math.abs(nodoFin.getCoordY()-nodoIni.getCoordY());
            */
            distancia=Point2D.distance(nodoIni.getCoordX(), nodoIni.getCoordX(), nodoFin.getCoordY(), nodoFin.getCoordY());
            //return Math.sqrt(x2+y2)/EasyGas.velocidad;
            //System.out.println(pesoTotal/distancia);
            /*if(distancia==0) {
                System.out.println(nodoIni.getCoordX() + " " +nodoIni.getCoordY() );
                System.out.println(nodoFin.getCoordX() + " " +nodoFin.getCoordY() );
            }*/
            
            return cantGLP/distancia;
    } 
    
    public double obtenerDistancia(Nodo nodoIni, Nodo nodoFin){
        double distancia=0;
        distancia=Point2D.distance(nodoIni.getCoordX(), nodoIni.getCoordX(), nodoFin.getCoordY(), nodoFin.getCoordY());
        return distancia;
    
    }
    public boolean hayCapacidadSuficiente(Camion c,ArrayList<Pedido> lpedido){
        // y si tienen prioridad
        int cantPrioridad=obtenerCantPriordades(lpedido);
        //System.out.println(cantPrioridad);
        int cantPedido = lpedido.size();
            for (int i=0;i<cantPedido;i++)
            {
                    if (lpedido.get(i).getEstado().equals(new String("listo"))&&(cantPrioridad==0 || lpedido.get(i).getPrioridad().equals(new String("tiene")) ) ){
                           // System.out.println(pedidos.get(i).getCantGLP());
                           // System.out.println(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual());
                            double cantGLPRestante=(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual());
                            double consumoDiesel= obtenerConsumo(c);
                            double consumoDieselPedido= obtenerConsumoPedido(c,lpedido.get(i));
                            double consumoDieselRestante=c.getIdTipoCamion().getCapacidadDiesel()-consumoDiesel;
                            //System.out.println("Diesel Restante " + consumoDieselRestante);
                            //System.out.println("Diesel para el pedido " + consumoDieselPedido);
                            if (cantGLPRestante >= lpedido.get(i).getCantGLP() && consumoDieselRestante>=consumoDieselPedido)
                            {     //  System.out.println("Hay espacio para el pedido " + pedidos.get(i).getIdPedido());
                                    return true;
                            }
                            

                    }
            }
            return false;
    
    
    }
    
    public int obtenerCantPriordades(ArrayList<Pedido> lpedidos){
        int cantPriordad=0;
        int cantPedidos=lpedidos.size();
        for(int i=0;i<cantPedidos;i++){
            if(lpedidos.get(i).getEstado().equals(new String("listo")) && lpedidos.get(i).getPrioridad().equals(new String("tiene"))) cantPriordad++;
            
        }
        return cantPriordad;
    
    }
    public boolean hayListos(ArrayList<Pedido> lpedidos){
        int cantPedidos=lpedidos.size();
        for(int i=0;i<cantPedidos;i++){
            if(lpedidos.get(i).getEstado().equals(new String("listo"))) return true;
           
        }
        return false;
        
    } 
    /*
Para i←0 hasta cantidadVehiculos(listaVehiculos) hacer
    listaNodos ← obtenerListaNodos(listaVehiculos,i)
    mejorListaNodos ← listaNodos
    mejorCosto ← obtenerCosto (listaNodos, grafo)
    Para cada j←0 hasta cantidadNodos(listaNodos) hacer
        Para cada k←j+1 hasta cantidadNodos(listaNodos) hacer
            nodoTemp ← listaNodos (k)
            borrarNodo(listaNodos, k)
            insertarNodo(listaNodos, j, nodoTemp)
            costo ← obtenerCosto(listaNodos, grafo)
            Si (costo < mejorCosto) entonces
                actualizar(listaNodos, mejorListaNodos, costo, mejorCosto)
            Fin Si
        Fin Para
        listaNodos ← obtenerListaNodos(listaVehiculos,i)
    Fin Para
Fin Para
Retornar listaVehiculos
*/
    public ArrayList<Camion> iniciar2OPT(ArrayList<Camion>nCamiones){
        int n = nCamiones.size();

        Camion camion = null;

        ArrayList<Camion> nuevoCamiones=new ArrayList<Camion>();
        for (int i=0;i<n;i++)   {
            camion = nCamiones.get(i);
            //HACER 2OPT
            if(camion.getRuta()!=null) nuevoCamiones.add(iniciar2OPTCamion(camion));//mejora ves por referencia
            else nuevoCamiones.add(camion);
        }
        return nuevoCamiones;
     
     
     
    }
    public Camion iniciar2OPTCamion(Camion c)   {
        //ordena sus pedidos para tener un mejor costo 
        int cantPedidos = c.getRuta().getLaristas().size();
        Camion aux = new Camion(c.getIdCamion(),c.getIdTipoCamion());
        aux.setRuta(c.getRuta());
        double costoMin = obtenerCostoRuta(c);
        double costoAux;
        for (int j=0;j<cantPedidos-1;j++)
        {
            for (int k= j+1 ;k< cantPedidos;k++)
            {
                if (j==0 && k == cantPedidos-1) break;
                aux = new Camion(c.getIdCamion(),c.getIdTipoCamion());
                aux.setRuta(c.getRuta());
                Mejorar(aux,j,k,cantPedidos);
                costoAux =obtenerCostoRuta(aux);
                if (costoAux < costoMin)
                {
                    costoMin = costoAux;
                    c = aux;
                }
            }
        }
        return c;
    
    
    }
    
    
    
    
     public int Mejorar(Camion aux, int j, int k,int cantPedidos)
    {
        ArrayList<Pedido> p1 = new ArrayList<Pedido>();
        ArrayList<Pedido> p2 = new ArrayList<Pedido>();
        ArrayList<Pedido> p3 = new ArrayList<Pedido>();
        
        ArrayList<Pedido> ped = new ArrayList<Pedido>();
        ArrayList<Pedido> auxP = new ArrayList<Pedido>();
        try{
        for (int a=0;a<j;a++){
            p1.add(aux.getRuta().getLaristas().get(a).getNodoDestino().getPedido());
        }
        for (int b=j;b<=k;b++){
            p2.add(aux.getRuta().getLaristas().get(b).getNodoDestino().getPedido());
        }
        if (k== cantPedidos-1){
            
        }else{
            
            for (int c=k+1;c<cantPedidos;c++){
                p3.add(aux.getRuta().getLaristas().get(c).getNodoDestino().getPedido());
            }
            
        
        }
        
        int tam = p2.size();
        for (int i=tam-1;i>=0;i--){
            ped.add(p2.get(i));
        }
        
        for (int a=0;a<j;a++){
            auxP.add(p1.get(a));
        }
        for (int b=0;b<k-j+1;b++){
            auxP.add(ped.get(b));
        }
        for (int c=0;c<cantPedidos-k-1;c++){
            auxP.add(p3.get(c));
        }
        aux.agregarRuta(auxP);
        
        }
        catch(Exception e){
            //System.out.println("Error noob");
            //System.out.println(e.toString());
        }
        return 1;
        
    }
    public Pedido obtenerPedidoRandom(ArrayList<Pedido> RCL){
            int cantPedidos = RCL.size();
            int numberRandom = 0;
            ArrayList<Pedido> conPrioridad=new ArrayList<Pedido>();
            ArrayList<Pedido> sinPrioridad=new ArrayList<Pedido>();
            for(int i=0;i<cantPedidos;i++){
                if(RCL.get(i).getEstado().equals(new String("listo"))){
                    if (RCL.get(i).getPrioridad().equals(new String("tiene"))){
                        conPrioridad.add(RCL.get(i));
                    }
                    else sinPrioridad.add(RCL.get(i));
                }
            
            }
            if (conPrioridad.size()!=0) {
                numberRandom = (int)(Math.random()*conPrioridad.size());
                return conPrioridad.get(numberRandom);
            }
            numberRandom = (int)(Math.random()*sinPrioridad.size());
            if (numberRandom < sinPrioridad.size()){
             
                return sinPrioridad.get(numberRandom);
                
            }
          
            return null;
    
    
    
    }
    public ArrayList<Pedido> obtenerLCR(Camion c,double alphaNuevo,ArrayList<Pedido>lpedidos){
        ArrayList<Pedido> nuevoPedidos =new ArrayList<Pedido>();
        ArrayList<Pedido> conPrioridad =new ArrayList<Pedido>();
        double costoAux = 0;
        int cantPedidos = lpedidos.size();
        for (int i=0;i<cantPedidos;i++)
        {
            if (lpedidos.get(i).getEstado().equals(new String("listo"))){
                costoAux =obtenerCostoArista(nodoInicio,lpedidos.get(i).getIdCliente().getIdNodo(),c,lpedidos.get(i).getCantGLP()); 
                //System.out.println("Costo aux LCR " + costoAux);
                if (costoAux <= minimo + alphaNuevo*(maximo-minimo)){
                   
                    nuevoPedidos.add(lpedidos.get(i));
                }
            }
        }
        //System.out.println("tamaño " + nuevoPedidos.size());
        return nuevoPedidos;
     
     
     
    } 
    public double obtenerCostoTotal (ArrayList<Camion> lcamiones){
    
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
        if(c.getRuta()==null || c.getRuta().getLaristas().size()==0 ) return 0;
        int cantPedidos= c.getRuta().getLaristas().size();
        //System.out.println(cantPedidos);
        
        Nodo nodoInicio=c.getRuta().getLaristas().get(0).getNodoOrigen();
        Nodo nodoFin;
        for (int i = 0;i<cantPedidos-1;i++)
        {
            nodoFin=c.getRuta().getLaristas().get(i).getNodoDestino();
            costo = costo + obtenerCostoArista(nodoInicio,nodoFin,c,c.getRuta().getLaristas().get(i).getNodoDestino().getPedido().getCantGLP());
            nodoInicio=c.getRuta().getLaristas().get(i+1).getNodoOrigen();
        }
        
         
        return costo;
    }
    public void inicializar(Camion c,ArrayList<Pedido>lpedidos){
        
            minimo = 0;
            maximo= 0;
            int cantPedidos = lpedidos.size();
            int flag=0;
            double costoAux;
           
            //System.out.println(cantPedidos);
            for (int i=0;i<cantPedidos;i++)
            {
                    if (lpedidos.get(i).getEstado().equals(new String("atendido"))==false){
                            
                                    
                            costoAux=obtenerCostoArista(nodoInicio,lpedidos.get(i).getIdCliente().getIdNodo(),c,lpedidos.get(i).getCantGLP());
                            //System.out.println("Este ees el costo calculado " + costoAux);  
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


