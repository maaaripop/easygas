/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Eduardo
 */
public class Mapa {

    int alto, ancho;
    int[][] mapa;
    boolean[][] visitado;

    //estos arreglos serviran para mover el punto actual hacia sus adyacentes, como son 4 posibles movimientos: izquierda(-1,0), arriba(0,-1), derecha(1,0) y abajo(0,1)
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};

    //se definen todas las posiciones del mapa como No Visitados
    private void inicializaVisitados() {
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                visitado[i][j] = false;
            }
        }
    }

    //se carga el mapa en "m" desde el archivo mapa.txt
    private void cargaMapa() throws FileNotFoundException { //el throw es es formalidad xD en caso no exista el txt
        Scanner s = new Scanner(new File("mapa.txt"));

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                mapa[i][j] = s.nextInt();
            }
        }
    }

    //constructor
    public Mapa(int nuevoAlto, int nuevoAncho) throws FileNotFoundException {
        alto = nuevoAlto;
        ancho = nuevoAncho;

        mapa = new int[alto][ancho];
        visitado = new boolean[alto][ancho];

        inicializaVisitados();
        cargaMapa();
    }

    private boolean hayBarreras(int xi, int yi, int xf, int yf) {
        boolean hayBarreraX1 = false;
        boolean hayBarreraX2 = false;
        boolean hayBarreraY1 = false;
        boolean hayBarreraY2 = false;

        int i;

        if (xf > xi) {
            for (i = xi + 1; i < xf; i++) {
                if (mapa[yi][i] == 0) {
                    hayBarreraX1 = true;
                }
                if (mapa[yf][i] == 0) {
                    hayBarreraX2 = true;
                }
            }
        }

        if (xf < xi) {
            for (i = xf + 1; i < xi; i++) {
                if (mapa[yi][i] == 0) {
                    hayBarreraX1 = true;
                }
                if (mapa[yf][i] == 0) {
                    hayBarreraX2 = true;
                }
            }
        }

        if (yf > yi) {
            for (i = yi + 1; i < yf; i++) {
                if (mapa[i][xi] == 0) {
                    hayBarreraY1 = true;
                }
                if (mapa[i][xf] == 0) {
                    hayBarreraY2 = true;
                }
            }
        }

        if (yf < yi) {
            for (i = yf + 1; i < yi; i++) {
                if (mapa[i][xi] == 0) {
                    hayBarreraY1 = true;
                }
                if (mapa[i][xf] == 0) {
                    hayBarreraY2 = true;
                }
            }
        }
        
        boolean barrera1 = hayBarreraX1 || hayBarreraY2;
        boolean barrera2 = hayBarreraY1 || hayBarreraY2;
        
        return (barrera1 && barrera2);
    }

    public int distanciaMinima(int xi, int yi, int xf, int yf) { //coordenadas del punto inicial (xi,yi), coordenadas del punto final (xf,yf)

        if (!hayBarreras(xi, yi, xf, yf)) {
            return Math.abs(yf - yi) + Math.abs(xf - xi);
        }

        //definimos nuestro punto inicial
        Punto inicial = new Punto(xi, yi, 0); //distancia = 0 porque no ha avanzado nada

        ArrayList<Punto> cola = new ArrayList<Punto>(); //cola de todos los posibles puntos por donde pasar para llegar al destino (xf,yf)

        cola.add(inicial); //metemos el punto inicial a la cola

        while (cola.size() > 0) { //mientras hayan puntos que atender

            Punto actual = cola.get(0); //atiende al primero de la cola
            cola.remove(0);

            if (actual.x == xf && actual.y == yf) //verifica si llegó al punto final
            {
                return actual.d;
            }

            visitado[actual.y][actual.x] = true; //se marca como visitado el punto actual, parece que estuviera al revez las coordenadas, pero es que estamos considerando el mapa con los ejes invertidos

            for (int i = 0; i < 4; i++) { //for para recorrer los 4 posibles adyacentes
                int nx = dx[i] + actual.x;
                int ny = dy[i] + actual.y;

                //aqui comprobamos que la coordenada adyacente no sobrepase las dimensiones del mapa
                //ademas comprobamos que no sea una vía intransitable (0) y no esté visitado
                if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto && mapa[ny][nx] != 0 && !visitado[ny][nx]) {
                    Punto adyacente = new Punto(nx, ny, actual.d + 1); //se define un nuevo adyacente
                    cola.add(adyacente); //se agrega a la cola para ser atendido
                }
            }
        }
        return -1; //en caso no encuentre ni un camino para llegar
    }
}
