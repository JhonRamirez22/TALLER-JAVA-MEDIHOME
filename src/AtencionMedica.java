import java.time.LocalDateTime;
import java.util.List;

public class AtencionMedica {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String observaciones;
    private String recomendaciones;

    private List<MedicionSignos> medicionSignos;

    public AtencionMedica() {
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public List<MedicionSignos> getMedicionSignos() {
        return medicionSignos;
    }

    public void setMedicionSignos(List<MedicionSignos> medicionSignos) {
        this.medicionSignos = medicionSignos;
    }

    public void registar(){
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = LocalDateTime.now();
        this.observaciones = observaciones;
        this.recomendaciones = recomendaciones;
    }
}
