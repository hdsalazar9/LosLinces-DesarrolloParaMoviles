package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Clase Cumpleaños:
 * Clase que detalla un evento especifcio de cumpleaños
 *
 * id autogenerado, nombre de la persona, fecha del cumpleaños
 * tipo de amigo o familiar, y numero de telefono
 */

@Parcel
public class Cumpleano {

    private long id;
    private String nombre;
    private Date fecha;
    private String tipo;
    private String telefono;

    public Cumpleano(long id, String nombre, Date fecha, String tipo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
