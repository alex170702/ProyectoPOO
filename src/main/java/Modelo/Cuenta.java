package Modelo;

public class Cuenta {
    private String UIID;
    private String id;
    private String nombre;
    private String apellido;
    private double saldo;
    private String usuario;
    private String contraseña;
    private boolean isAdmin;

    public Cuenta(String UIID, String id, String nombre, String apellido, double saldo, String usuario, String contraseña, boolean isAdmin) {
        this.UIID = UIID;
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.saldo = saldo;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.isAdmin = isAdmin;
    }

    public Cuenta(){
        
    }
    
    public String getUIID() {
        return UIID;
    }

    public void setUIID(String UIID) {
        this.UIID = UIID;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    
}
