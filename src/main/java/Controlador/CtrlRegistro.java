/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.CtrlAdmin.getUUID;
import Modelo.ConsultasFirebase;
import Modelo.Cuenta;
import Vista.RegistroCuenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author jose_
 */
public class CtrlRegistro implements ActionListener{
    private RegistroCuenta frm;
    private ConsultasFirebase modC;
    private CtrlLogin login;
    private Cuenta mod = new Cuenta();
    
    public CtrlRegistro(CtrlLogin login) {
        this.login = login;
        modC = login.modC;
    }

    public void iniciar(){
        frm = new RegistroCuenta();
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        this.frm.btnGuardar.addActionListener(this);
        this.frm.btnCancelar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnGuardar){
            registrar();
        }
        if(e.getSource() == frm.btnCancelar){
            salir();
        }
    }
    
    public static String getUUID() {
        String id = null;
        java.util.UUID uuid = java.util.UUID.randomUUID();
        id = uuid.toString();
        // Elimina el guión de la ID aleatoria
        id = id.replace("-", "");
        // Cambia la ID aleatoria a un número
        int num = id.hashCode();
        // Ir a valor absoluto
        num = num < 0 ? -num : num;

        String res = "" + num;

        return res;
    }
    
    private boolean confirmarContraseña() {
        JPasswordField pf = new JPasswordField();
        String pass = "";
        int opc = JOptionPane.showConfirmDialog(null,
                pf, "Ingresa de nuevo la contraseña: ",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opc == JOptionPane.OK_OPTION) {
            pass = new String(pf.getPassword());
        }
        if (pass.equals(mod.getContraseña())) {
            return true;
        } else {
            return false;
        }
    }
    
    public void salir(){
        login.iniciar();
        frm.setVisible(false);
        frm.dispose();
    }
    
    public void registrar(){
        if(validarCampos()){
            mod.setId(getUUID());
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setSaldo(0);
            mod.setUsuario(frm.txtUsuario.getText());
            mod.setContraseña(frm.txtPass.getText());
            mod.setIsAdmin(false);
            if (modC.validarUsuario(mod)){
                if (confirmarContraseña()){
                    String uuid = java.util.UUID.randomUUID().toString();
                    modC.registrar("cuentas", uuid, mod);
                    JOptionPane.showMessageDialog(null, "Se ha registrado exitosamente, ingrese con la cuenta registrada", "Aviso", JOptionPane.PLAIN_MESSAGE);
                    salir();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "El usuario ya está registrado, ingresa uno diferente", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Rellena todos los campos por favor", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public boolean validarCampos(){
        if (frm.txtNombre.getText().equals("") ||
                frm.txtApellido.getText().equals("") ||
                frm.txtUsuario.getText().equals("") ||
                frm.txtPass.getText().equals("")){
            return false;
        }
        else {
            return true;
        }
    }
}
