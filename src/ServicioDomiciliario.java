import java.time.LocalDateTime;

public class ServicioDomiciliario {
    private String codigoUnico;
    private LocalDateTime fechaHora;
    private String direccionAtencion;
    private String motivo;
    private String estado;

    private ProfesionalSalud profesionalSalud;
    private Paciente paciente;

    private AtencionMedica atencionMedica;

    public ServicioDomiciliario() {
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDireccionAtencion() {
        return direccionAtencion;
    }

    public void setDireccionAtencion(String direccionAtencion) {
        this.direccionAtencion = direccionAtencion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ProfesionalSalud getProfesionalSalud() {
        return profesionalSalud;
    }

    public void setProfesionalSalud(ProfesionalSalud profesionalSalud) {
        this.profesionalSalud = profesionalSalud;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public AtencionMedica getAtencionMedica() {
        return atencionMedica;
    }

    public void setAtencionMedica(AtencionMedica atencionMedica) {
        this.atencionMedica = atencionMedica;
    }
}
