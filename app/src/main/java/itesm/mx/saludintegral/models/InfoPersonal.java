package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Clase InfoPersonal:
 * Clase que tiene la informacionpersonal del usuario
 *
 * nombre, apodo, fecha de nacimiento, ciudad, pais, foto
 */

@Parcel
public class InfoPersonal {
    String nombre;
    String apodo;
    Date fechaNacimiento;
    String ciudad;
    String pais;
    byte[] foto;

    public InfoPersonal() {
    }

    public InfoPersonal(String nombre, String apodo, Date fechaNacimiento, String ciudad, String pais, byte[] foto) {
        this.nombre = nombre;
        this.apodo = apodo;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.pais = pais;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
