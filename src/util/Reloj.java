/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import model.Constantes.EasyGas;


/**
 *
 * @author alulab14
 */
public class Reloj {
    Calendar horaActual;
    DateFormat formato;
    int factorTiempo = 1000;

    public Reloj() {

            formato = new SimpleDateFormat("HH:mm:ss");
            horaActual = Calendar.getInstance();
            
            horaActual.set(Calendar.HOUR_OF_DAY,0);
            horaActual.set(Calendar.MINUTE, 0);
            horaActual.set(Calendar.SECOND, 0);
            Timer t1 = new Timer(10, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    horaActual.add(Calendar.SECOND, 1);
                    EasyGas.horaActual=horaActual.getTime();
                }
            });
            t1.start();
   }
}


