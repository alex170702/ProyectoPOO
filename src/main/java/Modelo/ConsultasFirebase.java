/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jose_
 */
public class ConsultasFirebase extends ConexionFirebase{
    
    private List<Cuenta> datos = new ArrayList<>();
    private Cuenta mod = null;
    
    public ConsultasFirebase(){
        try {
            conectar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al conectar la base de datos");
        }
    }

    public boolean registrar(String coleccion, String documento, Cuenta cuenta){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put("num", cuenta.getId());
            data.put("nombre", cuenta.getNombre());
            data.put("apellido", cuenta.getApellido());
            data.put("saldo", cuenta.getSaldo());
            data.put("usuario", cuenta.getUsuario());
            data.put("contraseña", cuenta.getContraseña());
            data.put("isAdmin", cuenta.getIsAdmin());
            DocumentReference docRef = bd.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean modificar(Cuenta cuenta){
            Map<String, Object> data = new HashMap<>();
            data.put("nombre", cuenta.getNombre());
            data.put("apellido", cuenta.getApellido());
            data.put("saldo", cuenta.getSaldo());
            data.put("usuario", cuenta.getUsuario());
            data.put("contraseña", cuenta.getContraseña());
            data.put("isAdmin", cuenta.getIsAdmin());
            DocumentReference docRef = bd.collection("cuentas").document(cuenta.getUIID());
            ApiFuture<WriteResult> future = docRef.update(data);
            return true;
    }
    
    public boolean eliminar(Cuenta cuenta){
        ApiFuture<WriteResult> writeResult = bd.collection("cuentas").document(cuenta.getUIID()).delete();
        return true;
    }
    
    public List listar() {
        try {
            datos.clear();
            CollectionReference nombres = ConexionFirebase.bd.collection("cuentas");
            ApiFuture<QuerySnapshot> future = nombres.get();
            for(DocumentSnapshot document: future.get().getDocuments()){
                mod = new Cuenta(
                    document.getId(),
                    document.getString("num"),
                    document.getString("nombre"),
                    document.getString("apellido"),
                    document.getDouble("saldo"),
                    document.getString("usuario"),
                    document.getString("contraseña"),
                    document.getBoolean("isAdmin")
                    );
                datos.add(mod);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsultasFirebase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ConsultasFirebase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

    //MÉTODO PARA VALIDAR SI NO HAY OTRO USUARIO CON EL MISMO NOMBRE
    public boolean validarUsuario(Cuenta cliente){
        if (datos.isEmpty()){
            listar();
        }
        int n = 0;
        for (int i = 0; i < datos.size(); i++){
            if (datos.get(i).getUsuario().equals(cliente.getUsuario()) && !datos.get(i).getId().equals(cliente.getId())){
                n++;
            }
        }
        
        if (n >= 1) return false;
        else return true;
    }
    
    public Cuenta validarSesion(String usuario, String pass){
        CollectionReference nombres = ConexionFirebase.bd.collection("cuentas");
        ApiFuture<QuerySnapshot> future = nombres.get();
        try {
            for(DocumentSnapshot document: future.get().getDocuments()){
                if (document.getString("usuario").equals(usuario) && document.getString("contraseña").equals(pass)){
                    mod = new Cuenta(
                            document.getId(),
                            document.getString("num"),
                            document.getString("nombre"),
                            document.getString("apellido"),
                            document.getDouble("saldo"),
                            document.getString("usuario"),
                            document.getString("contraseña"),
                            document.getBoolean("isAdmin")
                    );
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsultasFirebase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ConsultasFirebase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mod;
    }
    
    public Cuenta obtenerCuenta (String num){
        CollectionReference nombres = ConexionFirebase.bd.collection("cuentas");
        ApiFuture<QuerySnapshot> future = nombres.get();
        try {
            for(DocumentSnapshot document: future.get().getDocuments()){
                if (document.getString("num").equals(num)){
                    mod = new Cuenta(
                            document.getId(),
                            document.getString("num"),
                            document.getString("nombre"),
                            document.getString("apellido"),
                            document.getDouble("saldo"),
                            document.getString("usuario"),
                            document.getString("contraseña"),
                            document.getBoolean("isAdmin")
                    );
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsultasFirebase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ConsultasFirebase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mod;
    }
    
}
