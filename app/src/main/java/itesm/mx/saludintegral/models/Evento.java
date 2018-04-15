package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Clase Evento:
 * Clase que detalla un evento recordatorio, de cualquier que sea el tipo (
 * ejercicio, familiar, amigos, cognitivo, espiritual, finanzas)
 *
 * Incluye id, nombre tipo resumen, descripcion detallada del evento
 * fecha date, periodicidad boolean, tipo de evento
 */

@Parcel
public class Evento {

    private long id;
    private String name;
    private String descripcion;
    private Date fecha;
    private Boolean periodicidad;
    private String tipo;

    public Evento(long id, String name, String descripcion, Date fecha, Boolean periodicidad, String tipo) {
        this.id = id;
        this.name = name;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.periodicidad = periodicidad;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Boolean periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
