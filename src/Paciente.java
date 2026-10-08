public class Paciente extends Usuario implements iNotificable {
    private String telefono;
    private String direccionPrincipal;

    public Paciente() {
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println(mensaje);
        // aca va ael codigo para enviar el mensaje por correo.

    }

    // metodo a agregar
    public void registrarPaciente(){
        this.direccionPrincipal = direccionPrincipal;
        this.telefono = telefono;
    }

}
