package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Clase TomarMedicamento:
 * Clase que detalla un evento de haber tomado un medicamento
 *
 * Incluye id, id del medicamento, se se tomo o no a tiempo y la fecha del registro
 */

@Parcel
public class TomarMedicamento {
    long id;
    long idMedicamento;
    String sNombreMed;
    Boolean tomadoATiempo;
    Date fechaHora;

    public TomarMedicamento() {}

    public TomarMedicamento(long id, long idMedicamento,String sNombreMed, Boolean tomadoATiempo, Date fechaHora) {
        this.id = id;
        this.idMedicamento = idMedicamento;
        this.sNombreMed = sNombreMed;
        this.tomadoATiempo = tomadoATiempo;
        this.fechaHora = fechaHora;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getsNombreMed() {
        return sNombreMed;
    }

    public void setsNombreMed(String sNombreMed) {
        this.sNombreMed = sNombreMed;
    }

    public Boolean getTomadoATiempo() {
        return tomadoATiempo;
    }

    public void setTomadoATiempo(Boolean tomadoATiempo) {
        this.tomadoATiempo = tomadoATiempo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
