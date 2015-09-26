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
    private double beta; // mejor valor
    private double tau;  // peor valor
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
   
    ArrayList<Pedido> lpedidos= new ArrayList<Pedido>();
    ArrayList<Camion> lcamiones= new ArrayList<Camion>();
    double aux1=0;
    double aux2=0;
    int numIteracionesParada=0;
    //while(true){
    for(int i=0;i<numIteraciones;i++)  {      //System.out.println("iteracion no " + numIteracionesParada);
            //Se trabajara con copias de los arreglos auxiliares
            lpedidos= new ArrayList<Pedido>();
            lcamiones= new ArrayList<Camion>();
            int cantCamiones = camiones.size();
            
            for (int a=0; a<cantCamiones;a++) {
                lcamiones.add(new Camion(camiones.get(a).getIdCamion(),camiones.get(a).getIdTipoCamion()));
                
               
            }
            int cantPedidos = pedidos.size();
            for (int b=0; b<cantPedidos;b++) 
                lpedidos.add(new Pedido(pedidos.get(b).getIdPedido(),pedidos.get(b).getFechaRegistro(),pedidos.get(b).getHoraSolicitada(),
                pedidos.get(b).getCantGLP(),pedidos.get(b).getEstado(),pedidos.get(b).getPrioridad(),pedidos.get(b).getIdCliente()));
            
            
            nCamiones = iniciarFaseConstructiva(lcamiones,lpedidos);
            camiones2OPT = iniciar2OPT(nCamiones);
            //System.out.println("CostoTotal " + costoRuta(vehicles2OPT.get(0)));
//            System.out.println("CostoTotal " + costoTotal(vehicles2OPT));            
            if (i == 0) sol = camiones2OPT;
            else
            {
                aux1= obtenerCostoTotal(camiones2OPT);
                aux2= obtenerCostoTotal (sol);
               
                if (aux1>0&& aux1 < aux2)
                {
                    
                    //System.out.println("c nueva " + aux1);
                    //System.out.println("c antigua " + aux2);
                    
                    //System.out.println("Cambia iteraci" + i);*/
                    sol = camiones2OPT;
                }
             /*   if(aux1==aux2) {
                    System.out.println("*************************************************************************************");
                    //numIteraciones=numIteracionesParada;
                    pedidos=lpedidos;
       
                    return sol; }
             */   
               // System.out.println("Llegue");
               // System.out.println("iteracion no " + i + " con costo " + aux1);
            }
           // numIteracionesParada++;
        }
        pedidos=lpedidos;
       
        return sol;
        //System.out.println("costo final " + aux1);
        /*
        pedidos = new ArrayList<Pedido>();
        for(int y=0;y<lpedidos.size();y++){
            //if(lpedidos.get(y).getEstado().equals(new String("atendido"))) System.out.println("ha actualizado");
            pedidos.add(new Pedido(lpedidos.get(y).getIdPedido(),lpedidos.get(y).getFechaRegistro(),lpedidos.get(y).getHoraSolicitada(),
                lpedidos.get(y).getCantGLP(),lpedidos.get(y).getEstado(),lpedidos.get(y).getPrioridad(),lpedidos.get(y).getIdCliente()));
            
        }  */  
        //System.out.println(obtenerCantAtendidos(lpedidos));
        
    
    
    
    
    }
    
 
     
     public ArrayList<Camion> iniciarFaseConstructiva(ArrayList<Camion>lcamiones,ArrayList<Pedido> lpedidos){
            int cantCamiones=lcamiones.size();
            int j=0;
            int flag=0;
            while(j<cantCamiones && hayListos(lpedidos)){
                Ruta r = new Ruta();
                Arista a = new Arista();
                ArrayList<Arista> laristas=new ArrayList<Arista>();
                Camion c= lcamiones.get(j);
                nodoInicio=EasyGas.central;
                nodoInicio.setCantPrioridades(0);
                nodoInicio.setCantGLP(0);
               // System.out.println("idCamion "+ c.getIdCamion() + " hay por atender ");
                ArrayList<Pedido> LCR= new ArrayList<Pedido>();
                nodoInicio.setHoraLlegada(Reloj.horaActual.getTime()); //hora que sale el camion
                int cantListos=obtenerCantListo(lpedidos);
            //    System.out.println("nro listos " + cantListos + " " + nodoInicio.getHoraLlegada() + " " + c.getCantDieselActual());
                
                while(hayCapacidadSuficiente(c,lpedidos)){
                  
                   cantListos=obtenerCantListo(lpedidos);
                   System.out.println("hay "+cantListos + " listos con " +(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual()));
                   
                   inicializar(c,lpedidos);
                  // System.out.println("Depues de inicializar");
                //    System.out.println("Tau : " + tau);
                //    System.out.println("beta : " + beta);
                    if (flag==0){
                        LCR=obtenerLCR(c,1,lpedidos);
                        flag=1;
                    }
                    else{
                        LCR=obtenerLCR(c,alpha,lpedidos);
                    }
                  
                   //System.out.println(LCR.size());
                   Pedido p=obtenerPedidoRandom(LCR);
                   //if(p==null) System.out.println("nulo");
                   if(p!=null){ 
                      
                        double cantGLPsobrante=(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual());
                        double consumoDieselsobrante= (c.getIdTipoCamion().getCapacidadDiesel()-obtenerConsumo(c));
                         //System.out.println("No "+ p.getIdPedido()+ " tiene pedido de "+ p.getCantGLP()+ " pero me sobra " + cantGLPsobrante);
                        int tiempoSegundos=obtenerTiempoEntrega(p,nodoInicio);
                        Calendar calendarHoraFin= Calendar.getInstance();
                        calendarHoraFin.setTime(nodoInicio.getHoraLlegada());        
                        calendarHoraFin.add(Calendar.SECOND,tiempoSegundos);
                        // dentroDelTurno(calendarHoraFin.getTime()    
                        if(p.getCantGLP()<=cantGLPsobrante && obtenerConsumoPedido(c,p)<=consumoDieselsobrante && p.getHoraSolicitada().before(calendarHoraFin.getTime()) && obtenerCostoArista(nodoInicio,p.getIdCliente().getIdNodo(),c,p) >0 ){
                           a = new Arista();
                           a.setNodoOrigen(nodoInicio);
                           c.setCantGLPActual(c.getCantGLPActual()+p.getCantGLP());
                           a.setNodoDestino(p.getIdCliente().getIdNodo());
                           a.getNodoDestino().setPedido(p);
                           a.getNodoDestino().setHoraLlegada(calendarHoraFin.getTime());
                           c.setCantDieselActual(c.getCantDieselActual()+obtenerConsumoPedido(c,p));
                           laristas.add(a);
                           for(int k=0;k<lpedidos.size();k++){
                               if(lpedidos.get(k).equals(p)){
                                  lpedidos.get(k).setEstado("atendido");
                                  lpedidos.get(k).setFechaEntrega(calendarHoraFin.getTime());
                                  break;
                               }
                            }
                          //  System.out.println(Reloj.horaActual.getTime() + " camion" +c.getIdCamion() +"  pedido nro " + p.getIdPedido() + " con GLP " + p.getCantGLP() + " hora requerida " + p.getHoraSolicitada() +"  Hora atendido " + horaCalendar.getTime());
                           
                           // setteo el nodoInicio
                           nodoInicio.setCantGLP(p.getCantGLP()+nodoInicio.getCantGLP());
                           nodoInicio=p.getIdCliente().getIdNodo();
                           if(p.getPrioridad().equals(new String("tiene")))nodoInicio.setCantPrioridades(nodoInicio.getCantPrioridades()+1);
                           nodoInicio.setHoraLlegada(calendarHoraFin.getTime());
                           
                           

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
    public boolean dentroDelTurno(Date horaConsultada){
        Date horaUltimoPedidoEntregado=horaConsultada;
        boolean resultado= EasyGas.turnoActual.getHoraFin().before(horaUltimoPedidoEntregado) ; 
        return resultado;
    }
    
    public boolean esTiempoDeEntregar(Pedido p, Nodo nodoIni){
      
        //int tiempoSegundos=obtenerTiempoEntrega(p,nodoIni);
        //long diferencia =  tiempoSegundos;
        //long diferenciaLimite=1*60*60;
        //boolean resultado=diferencia<=diferenciaLimite;
        
        int tiempoSegundos=obtenerTiempoEntrega(p,nodoIni);
        Calendar calendarHoraFin= Calendar.getInstance();
        calendarHoraFin.setTime(nodoIni.getHoraLlegada());        
        calendarHoraFin.add(Calendar.SECOND,tiempoSegundos);
        long diferencia =  (calendarHoraFin.getTimeInMillis() - p.getHoraSolicitada().getTime()); 
        long limite=1000*60*60; //3 600 000
       // System.out.println("la diferencia es " + diferencia);
        boolean resultado=diferencia>=0 && diferencia<=limite;
        //boolean resultado=calendarHoraFin.getTime().after(p.getHoraSolicitada()) ; // hora fin > horasolicitada
        return resultado;
    
    }
    
    
    public int obtenerTiempoEntrega(Pedido p,Nodo nodoIni){
        double tiempo = obtenerDistancia(nodoIni,p.getIdCliente().getIdNodo())/EasyGas.velocidad*1.0;
        int tiempoSegundos=(int)Math.round(tiempo*60*60);
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
    public double obtenerCostoArista (Nodo nodoIni, Nodo nodoFin,Camion c,Pedido p){
            // asumir coger el pedido es decir toda la informacion del pedido debe de estar incluida
            double glpActual=nodoIni.getCantGLP()+p.getCantGLP();
            double difGLP=c.getIdTipoCamion().getCapacidadGLP()-glpActual;
            int tiempoSegundos=obtenerTiempoEntrega(p,nodoIni);
            Calendar calendarHoraFin= Calendar.getInstance();
            calendarHoraFin.setTime(nodoIni.getHoraLlegada());        
            calendarHoraFin.add(Calendar.SECOND,tiempoSegundos);
            long diferenciaTiemposEntregas =  (calendarHoraFin.getTimeInMillis() - p.getHoraSolicitada().getTime())/(1000*60); 
            double distancia=Point2D.distance(nodoIni.getCoordX(), nodoIni.getCoordY(), nodoFin.getCoordX(), nodoFin.getCoordY());
            int cantPrioridades=p.getPrioridad().equals(new String("tiene"))?1:0;
            double arriba=((distancia)*difGLP*diferenciaTiemposEntregas);
            double abajo=(p.getCantGLP());
         //   double abajo=(p.getCantGLP()+0.1*cantPrioridades+1);
            double costo=arriba/abajo;
            //beta el mejor valor es decir el 
          // System.out.println("Costo: " + costo + " arriba " + arriba+ " abajo " + abajo + " dif tiempos "+ diferenciaTiemposEntregas + " distancia " + distancia + " difGLP " + difGLP);
            return costo;
    } 
    
    public double obtenerDistancia(Nodo nodoIni, Nodo nodoFin){
        double distancia=0;
        distancia=Point2D.distance(nodoIni.getCoordX(), nodoIni.getCoordY(), nodoFin.getCoordX(), nodoFin.getCoordY());
        return Math.abs(distancia);
    
    }
    public boolean hayCapacidadSuficiente(Camion c,ArrayList<Pedido> lpedido){
       
        //System.out.println("prioridad " +cantPrioridad);
        int cantPedido = lpedido.size();
            for (int i=0;i<cantPedido;i++)
            {
                    if (lpedido.get(i).getEstado().equals(new String("listo")) ){
                           // System.out.println(pedidos.get(i).getCantGLP());
                           // System.out.println(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual());
                            double cantGLPRestante=(c.getIdTipoCamion().getCapacidadGLP()-c.getCantGLPActual());
                            double consumoDiesel= obtenerConsumo(c);
                            double consumoDieselPedido= obtenerConsumoPedido(c,lpedido.get(i));
                            double consumoDieselRestante=c.getIdTipoCamion().getCapacidadDiesel()-consumoDiesel;
                            //System.out.println("Diesel Restante " + consumoDieselRestante);
                            //System.out.println("Diesel para el pedido " + consumoDieselPedido);
                            int tiempoSegundos=this.obtenerTiempoEntrega(lpedido.get(i), nodoInicio);
                            Calendar calendarHoraFin= Calendar.getInstance();
                            calendarHoraFin.setTime(nodoInicio.getHoraLlegada());        
                            calendarHoraFin.add(Calendar.SECOND,tiempoSegundos);
                            double costoArista=this.obtenerCostoArista(nodoInicio, lpedido.get(i).getIdCliente().getIdNodo(), c, lpedido.get(i));
                            Date horaPosibleEntrega = calendarHoraFin.getTime();
                            // if(p.getCantGLP()<=cantGLPsobrante && obtenerConsumoPedido(c,p)<=consumoDieselsobrante && esTiempoDeEntregar(p,nodoInicio)){
                            boolean fecha=lpedido.get(i).getHoraSolicitada().before(calendarHoraFin.getTime());
                            if (cantGLPRestante >= lpedido.get(i).getCantGLP() && consumoDieselRestante>=consumoDieselPedido  && fecha && costoArista >=0)
                            {    //  System.out.println( pedidos.get(i).getIdPedido() + " llegara en " + horaPosibleEntrega + " hora solicitada " +lpedido.get(i).getHoraSolicitada() );
                                // existe por lo menos un pedido que tenga suficinete glp, diesel, que se entregue dentro del turno y que la diferencia de entrega sea poco    
                                
                                return true;
                            }
                            

                    }
            }
            return false;
    
    
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
        if(c.getRuta()==null) return c;
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
                if (costoAux!=0 && costoAux < costoMin)
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
            ArrayList<Pedido> pedidosListos=new ArrayList<Pedido>();
            
            for(int i=0;i<cantPedidos;i++){
                if(RCL.get(i).getEstado().equals(new String("listo"))){
                    pedidosListos.add(RCL.get(i));
                }
            
            }
            
            numberRandom = (int)(Math.random()*pedidosListos.size());
            if (numberRandom < pedidosListos.size()){
                
                return pedidosListos.get(numberRandom);
                
            }
          
            return null;
    
    
    
    }
    public ArrayList<Pedido> obtenerLCR(Camion c,double alphaNuevo,ArrayList<Pedido>lpedidos){
        ArrayList<Pedido> nuevoPedidos =new ArrayList<Pedido>();
        ArrayList<Pedido> conPrioridad =new ArrayList<Pedido>();
        double costoAux = 0;
        int cantPedidos = lpedidos.size();
        //maximo peor valor tau
        //minimo mejor valor beta
        for (int i=0;i<cantPedidos;i++)
        {
            if (lpedidos.get(i).getEstado().equals(new String("listo"))){
                costoAux =obtenerCostoArista(nodoInicio,lpedidos.get(i).getIdCliente().getIdNodo(),c,lpedidos.get(i)); 
                //System.out.println("Costo aux LCR " + costoAux);
                //minizar la funcion 
                if (beta<=costoAux && costoAux<=( beta + alphaNuevo*(tau-beta))){
                   
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
            costo = costo + obtenerCostoArista(nodoInicio,nodoFin,c,c.getRuta().getLaristas().get(i).getNodoDestino().getPedido());
            nodoInicio=c.getRuta().getLaristas().get(i+1).getNodoOrigen();
        }
        
         
        return costo;
    }
    public void inicializar(Camion c,ArrayList<Pedido>lpedidos){
        
            beta = 1000000000;
            tau= 1;
            int cantPedidos = lpedidos.size();
            int flag=0;
            double costoAux;
           
            //System.out.println(cantPedidos);
            for (int i=0;i<cantPedidos;i++) {
                    if (lpedidos.get(i).getEstado().equals(new String("listo"))){
                            
                                    
                            costoAux=obtenerCostoArista(nodoInicio,lpedidos.get(i).getIdCliente().getIdNodo(),c,lpedidos.get(i));
                            //System.out.println("Este ees el costo calculado " + costoAux);  
                            // beta es el mejor valor es decir el minimo
                            // tau es el peor valor es decir el maximo 
                            //System.out.println(costoAux);
                            if(costoAux>0){  
                                 if (costoAux<beta && costoAux>=0) 
                                            beta=costoAux;
                                 if (costoAux>tau && costoAux!=0) 
                                            tau=costoAux;
                               
                            }
                    
                    }	
            }
           //System.out.println("tau " + tau + " beta " + beta);
    
    
    
    }
}


