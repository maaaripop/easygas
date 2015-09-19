/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Constantes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.*;
/**
 *
 * @author a20080480
 */
public class EasyGas {
    //constantes de correo electronico
    public static String host = "smtp.gmail.com";
    public static String correoEasyGas = "easygas2015.2@gmail.com";
    public static String contrasenhaEasyGas = "Equipo01EasyGas";
    public static String asuntoRecuperarContrasenha = "EasyGas - Recuperación de contraseña";
    public static String errorEnviarCorreo = "Ocurrió un error tratando de enviar el correo!\n\n"
                                            +"*Verifique su conexión a internet\n\n"
                                            +"**Si el problema persiste envíenos un correo indicando su nombre de usuario";
    
    //constantes de las rutas
    public static int velocidad;
    public static Nodo central;
    public static ArrayList<Turno> lturnos;
    public static Date horaActual;
    
}
