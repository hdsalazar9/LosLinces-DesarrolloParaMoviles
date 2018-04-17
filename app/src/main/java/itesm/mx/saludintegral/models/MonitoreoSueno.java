package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Clase MonitoreoSueno:
 * Clase que representa una noche para el monitoreo de sueño
 *
 * id, fecha de la noche y horas dormidas
 */

@Parcel
public class MonitoreoSueno {

    long id;
    Date fecha;
    Double horas;

    public MonitoreoSueno() {
    }

    public MonitoreoSueno(long id, Date fecha, Double horas) {
        this.id = id;
        this.fecha = fecha;
        this.horas = horas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }
}
