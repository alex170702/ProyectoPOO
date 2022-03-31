/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasFirebase;
import Modelo.Cuenta;
import Vista.MenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jose_
 */
public class CtrlPrincipal implements ActionListener {

    //AQUI VAN LOS METODOS DE TRANSFERIR, DEPOSITAR Y RETIRAR
    public ConsultasFirebase modC;
    private Cuenta mod;
    private CtrlLogin login;
    private MenuPrincipal frm;
    private CtrlAdmin admin;
    private CtrlModificar modificar;

    public CtrlPrincipal(CtrlLogin login) {
        this.login = login;
        this.modC = login.modC;
    }

    public void iniciar() {
        frm = new MenuPrincipal();
        frm.setVisible(true);
        mod = login.getMod();
        admin = new CtrlAdmin(this);
        modificar = new CtrlModificar(mod, this);
        actualizar();
        if (mod.getIsAdmin()) {
            frm.btnAdmin.setVisible(true);
        } else {
            frm.btnAdmin.setVisible(false);
        }
                this.frm.btnCerrar.addActionListener(this);
        this.frm.btnAdmin.addActionListener(this);
        this.frm.btnIngresar.addActionListener(this);
        this.frm.btnModificar.addActionListener(this);
        this.frm.btnRetirar.addActionListener(this);
        this.frm.btnTransferir.addActionListener(this);
    }

    public void actualizar() {
        frm.txtNum.setText("" + mod.getId());
        frm.txtNombre.setText(mod.getNombre());
        frm.txtApellido.setText(mod.getApellido());
        frm.txtDinero.setText("$" + mod.getSaldo());
    }
    
    public void actModC(){
        mod = modC.obtenerCuenta(mod.getId());
        actualizar();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnCerrar) {
            login.iniciar();
            frm.setVisible(false);
            frm.dispose();
        }
        if (e.getSource() == frm.btnAdmin) {
            frm.setVisible(false);
            admin.iniciar();
        }
        if (e.getSource() == frm.btnModificar){
            modificar.iniciar();
            frm.setVisible(false);
        }
        if (e.getSource() == frm.btnIngresar) {
            ingresar();
        }
        if (e.getSource() == frm.btnRetirar) {
            retirar();
        }
        if (e.getSource() == frm.btnTransferir) {
            transferir();
        }
    }

    public MenuPrincipal getFrm() {
        return frm;
    }

    public void ingresar() {
        try {
            double cantidad = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingresa la cantidad a ingresar: "));
            double dineroInicial = mod.getSaldo();
            double dineroFinal = dineroInicial + cantidad;
            mod.setSaldo(dineroFinal);
            modC.modificar(mod);
            JOptionPane.showMessageDialog(null, "Se ingresó $" + cantidad + " a su saldo");
            actualizar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingresa un valor númerico por favor");
        }
    }

    public void retirar() {
        try {
            double cantidad = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingresa la cantidad a retirar: "));
            double dineroInicial = mod.getSaldo();
            if (cantidad > 0 && cantidad < dineroInicial) {
                double dineroFinal = dineroInicial - cantidad;
                mod.setSaldo(dineroFinal);
                modC.modificar(mod);
                JOptionPane.showMessageDialog(null, "Se retiró $" + cantidad + " de su saldo");
                actualizar();
            } else {
                JOptionPane.showMessageDialog(null, "No tienes suficiente saldo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingresa un valor númerico por favor");
        }
    }

    public void transferir() {
        try {
            String num = JOptionPane.showInputDialog(null, "Ingresa el número de cuenta a transferir: ");
            double cantidad = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingresa la cantidad a transferir: "));

            Cuenta cuenta2 = modC.obtenerCuenta(num);
            if (cuenta2 != null) {
                double dineroInicial2 = cuenta2.getSaldo();
                double dineroInicial = mod.getSaldo();

                if (cantidad > 0 && cantidad < dineroInicial) {
                    double dineroFinal = dineroInicial - cantidad; //Dinero de la cuenta remitente
                    double dineroFinal2 = dineroInicial2 + cantidad; //Dinero de a cuenta destino
                    mod.setSaldo(dineroFinal);
                    cuenta2.setSaldo(dineroFinal2);
                    modC.modificar(mod); //FIREBASE DE LA CUENTA REMITENTE
                    modC.modificar(cuenta2); //FIREBASE DE LA CUENTA DESTINO
                    JOptionPane.showMessageDialog(null, "Se transfirió $" + cantidad + " de su saldo a la cuenta " + num);
                    actualizar();
                } else {
                    JOptionPane.showMessageDialog(null, "No tienes suficiente saldo");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No existe ninguna cuenta con ese número");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingresa un valor númerico por favor");
        }
    }

}
