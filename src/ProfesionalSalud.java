public class ProfesionalSalud extends Usuario implements iNotificable {
    private String numeroRegistroProfesional;

    public ProfesionalSalud(String numeroRegistroProfesional) {
        this.numeroRegistroProfesional = numeroRegistroProfesional;
    }

    public String getNumeroRegistroProfesional() {
        return numeroRegistroProfesional;
    }

    public void setNumeroRegistroProfesional(String numeroRegistroProfesional) {
        this.numeroRegistroProfesional = numeroRegistroProfesional;
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println(mensaje);
        //Codigo para enviar notificacion por whatsaap

    }

    //metodo a agregar
    public void registrarProfesional(){
        this.numeroRegistroProfesional = numeroRegistroProfesional;

    }
}
