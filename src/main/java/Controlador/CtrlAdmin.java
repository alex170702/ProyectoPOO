package Controlador;

import Modelo.ConsultasFirebase;
import Modelo.Cuenta;
import Vista.MenuAdmin;
import Vista.MenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CtrlAdmin implements ActionListener {

    private Cuenta mod = new Cuenta();
    private MenuAdmin frm;
    private DefaultTableModel modelo = new DefaultTableModel(); //Modelo de la tabla del formulario.
    private CtrlPrincipal menuP;
    private ConsultasFirebase modC;

    public CtrlAdmin(CtrlPrincipal menuP) {
        this.menuP = menuP;
        this.modC = menuP.modC;
        
    }

    public void iniciar() {
        frm = new MenuAdmin();
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.txtUIID.setText(null);
        listar(frm.TablaDatos);
        this.frm.btnAgregar.addActionListener(this);
        this.frm.btnModificar.addActionListener(this);
        this.frm.btnEliminar.addActionListener(this);
        this.frm.btnLimpiar.addActionListener(this);
        this.frm.btnSelec.addActionListener(this);
        this.frm.btnExit.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        //BOTÓN DE AGREGAR
        if (e.getSource() == frm.btnAgregar) {
            if (frm.txtUIID.getText().equals("")) {
                mod.setId(getUUID());
                mod.setNombre(frm.txtNombre.getText());
                mod.setApellido(frm.txtApellido.getText());
                mod.setSaldo(0);
                mod.setUsuario(frm.txtUsuario.getText().toLowerCase());
                mod.setContraseña(frm.txtPass.getText());
                mod.setIsAdmin(frm.checkAdmin.isSelected());
                if (modC.validarUsuario(mod)) {
                    if (confirmarContraseña()) {
                        String uuid = java.util.UUID.randomUUID().toString();
                        if (modC.registrar("cuentas", uuid, mod)) {
                            JOptionPane.showMessageDialog(null, "Cuenta registrada exitosamente.");
                            listar(frm.TablaDatos);
                            limpiar();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrió algun error al intentar agregar la cuenta");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya está registrado, ingrese uno distinto");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Limpia todas las celdas primero para agregar");
            }
        }

        if (e.getSource() == frm.btnModificar) {
            if (!frm.txtUIID.getText().equals("")) {
                mod.setId(frm.txtID.getText());
                mod.setUIID(frm.txtUIID.getText());
                mod.setNombre(frm.txtNombre.getText());
                mod.setApellido(frm.txtApellido.getText());
                mod.setSaldo(Double.parseDouble(frm.txtSaldo.getText()));
                mod.setUsuario(frm.txtUsuario.getText().toLowerCase());
                mod.setContraseña(frm.txtPass.getText());
                mod.setIsAdmin(frm.checkAdmin.isSelected());
                if (modC.validarUsuario(mod)) {
                    if (confirmarContraseña()) {
                        if (modC.modificar(mod)) {
                            JOptionPane.showMessageDialog(null, "Cuenta modificada exitosamente.");
                            listar(frm.TablaDatos);
                            limpiar();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrió algun error al intentar modificar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya está registrado, ingrese uno distinto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No has seleccionado nada todavia");
            }
        }

        //BOTÓN DE SELECCIONAR
        if (e.getSource() == frm.btnSelec) {
            int fila = frm.TablaDatos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes selecciona una fila");
            } else {
                String idSelec = frm.TablaDatos.getValueAt(fila, 0).toString();
                String nom = frm.TablaDatos.getValueAt(fila, 1).toString();
                String apellido = frm.TablaDatos.getValueAt(fila, 2).toString();
                String saldo = frm.TablaDatos.getValueAt(fila, 3).toString();
                String usuario = frm.TablaDatos.getValueAt(fila, 4).toString();
                String contraseña = frm.TablaDatos.getValueAt(fila, 5).toString();
                boolean admin = Boolean.valueOf(frm.TablaDatos.getValueAt(fila, 6).toString());
                String UUID = frm.TablaDatos.getValueAt(fila, 7).toString();
                frm.txtID.setText(idSelec);
                frm.txtNombre.setText(nom);
                frm.txtApellido.setText(apellido);
                frm.txtSaldo.setText(saldo);
                frm.txtUsuario.setText(usuario);
                frm.txtPass.setText(contraseña);
                frm.checkAdmin.setSelected(admin);
                frm.txtUIID.setText(UUID);
            }
        }

        //Botón de Eliminar
        if (e.getSource() == frm.btnEliminar) {
            if (!frm.txtUIID.getText().equals("")) {
                mod.setUIID(frm.txtUIID.getText());
                mod.setContraseña(frm.txtPass.getText());
                if (confirmarContraseña()) {
                    if (modC.eliminar(mod)) {
                        JOptionPane.showMessageDialog(null, "Cuenta borrada con exito.");
                        listar(frm.TablaDatos);
                        limpiar();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No has seleccionado nada todavia");
            }

        }

        //BOTÓN DE LIMPIAR
        if (e.getSource() == frm.btnLimpiar) {
            limpiar();
        }
        
        //BOTÓN DE SALIR
        if (e.getSource() == frm.btnExit){
                menuP.iniciar();
                menuP.actModC();
                //menuP.actualizar();
                frm.setVisible(false);
                frm.dispose();
            }
    }

    private void limpiar() {
        frm.txtUIID.setText(null);
        frm.txtNombre.setText(null);
        frm.txtApellido.setText(null);
        frm.txtSaldo.setText(null);
        frm.txtUsuario.setText(null);
        frm.txtPass.setText(null);
        frm.checkAdmin.setSelected(false);
        frm.txtID.setText(null);
    }

    private void listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        List<Cuenta> lista = modC.listar();
        Object[] object = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getApellido();
            object[3] = lista.get(i).getSaldo();
            object[4] = lista.get(i).getUsuario();
            object[5] = lista.get(i).getContraseña();
            object[6] = lista.get(i).getIsAdmin();
            object[7] = lista.get(i).getUIID();
            modelo.addRow(object);
        }
        frm.TablaDatos.setModel(modelo);
    }

}
