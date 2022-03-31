package Cajero;


import Controlador.CtrlLogin;
import Modelo.ConsultasFirebase;



public class ClasePrincipal {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.classpath"));
        ConsultasFirebase modC = new ConsultasFirebase();
        CtrlLogin login = new CtrlLogin(modC);
        login.iniciar();
    }
}
