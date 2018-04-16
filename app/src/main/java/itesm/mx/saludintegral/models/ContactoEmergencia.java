package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

/**
 * Clase Cumpleaños:
 * Clase que representa un contacto de emergencia para el caso de la persona
 *
 * id, nombre de la persona y sunumero
 */

@Parcel
public class ContactoEmergencia {

    private long id;
    private String nombre;
    private String telefono;

    public ContactoEmergencia(long id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
