/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasFirebase;
import Modelo.Cuenta;
import Vista.IniciarSesion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jose_
 */
public class CtrlLogin implements ActionListener {
    private Cuenta mod = new Cuenta(); //Modelo de CUENTA
    public ConsultasFirebase modC;
    private IniciarSesion frm;
    private CtrlPrincipal login;
    private CtrlRegistro registro;
    
    public CtrlLogin(ConsultasFirebase modC) {
        this.modC = modC;
        mod = null;
    }
    
    public void iniciar(){
        frm = new IniciarSesion();
        frm.setVisible(true);
        frm.txtPassword.setText("");
        frm.txtUsuario.setText("");
        login = new CtrlPrincipal(this);
        registro = new CtrlRegistro(this);
        this.frm.btnAcceder.addActionListener(this);
        this.frm.btnRegistro.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //BOTÓN ACCEDOR
       if (e.getSource() == frm.btnAcceder){
           //Nos devuelve la cuenta y sus datos.
           mod = modC.validarSesion(frm.txtUsuario.getText(), frm.txtPassword.getText());
           if (mod != null){
               //MENSAJE DE CUENTA CORRECTA
               login.iniciar(); //Ponemos visible el segundo menú que es el menu principal
               frm.setVisible(false); //Escondemos el menú login
               frm.dispose();
           }
           else{
               //MENSAJE DE CUENTA INCORRECTA
               JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
           }
       }
       
       if(e.getSource() == frm.btnRegistro){
           registro.iniciar();
           frm.setVisible(false); //Escondemos el menú login
           frm.dispose();
       }
    }

    public Cuenta getMod() {
        return mod;
    }
    
    
    
    
    
}
