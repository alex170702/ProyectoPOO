/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasFirebase;
import Modelo.Cuenta;
import Vista.ModificarCuenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author jose_
 */
public class CtrlModificar implements ActionListener {

    private ModificarCuenta frm;
    private ConsultasFirebase modC;
    private CtrlPrincipal menu;
    private Cuenta mod;

    public CtrlModificar(Cuenta mod, CtrlPrincipal menu) {
        this.mod = mod;
        this.menu = menu;
        modC = menu.modC;
    }

    public void iniciar() {
        frm = new ModificarCuenta();
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        this.frm.btnGuardar.addActionListener(this);
        this.frm.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnGuardar) {
            actualizar();
        }
        if (e.getSource() == frm.btnCancelar) {
            salir();
        }
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

    public void salir() {
        menu.iniciar();
        frm.setVisible(false);
        frm.dispose();
    }

    public void actualizar() {
        if (validarCampos()) {
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setUsuario(frm.txtUsuario.getText());
            mod.setContraseña(frm.txtPass.getText());
            if (modC.validarUsuario(mod)) {
                if (confirmarContraseña()) {
                    modC.modificar(mod);
                    menu.actModC();
                    JOptionPane.showMessageDialog(null, "Modificado Exitoso", "Aviso", JOptionPane.PLAIN_MESSAGE);
                    salir();
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El usuario ya está registrado, ingresa uno diferente", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Rellena todos los campos por favor", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean validarCampos() {
        if (frm.txtNombre.getText().equals("")
                || frm.txtApellido.getText().equals("")
                || frm.txtUsuario.getText().equals("")
                || frm.txtPass.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
