package itesm.mx.saludintegral.models;

import org.parceler.Parcel;

import java.sql.Time;
import java.util.Date;

/**
 * Clase Medicamento:
 * Clase que detalla un objeto de tipo medicamento
 *
 * Incluye los valores de id, nombre, gramaje, cantidad
 *  periodicidad, hora, cadaCuanto, fechaComienzo, fechaTermino
 *  antesDespuesDeComer y foto
 */

@Parcel
public class Medicamento {

    private long id;
    private String nombre;
    private Double gramaje;
    private int cantidad;
    private String perodicidad;
    private Time hora;
    private int cadaCuanto; //Cada cuantas horas se toma, en caso de ser 0 solo se toma una vez
    private Date fechaComienzo;
    private Date fechaTermino;
    private Boolean antesDespuesDeComer;
    private byte[] foto;

    public Medicamento(long id, String nombre, Double gramaje, int cantidad, String perodicidad, Time hora, int cadaCuanto, Date fechaComienzo, Date fechaTermino, Boolean antesDespuesDeComer, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.gramaje = gramaje;
        this.cantidad = cantidad;
        this.perodicidad = perodicidad;
        this.hora = hora;
        this.cadaCuanto = cadaCuanto;
        this.fechaComienzo = fechaComienzo;
        this.fechaTermino = fechaTermino;
        this.antesDespuesDeComer = antesDespuesDeComer;
        this.foto = foto;
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

    public Double getGramaje() {
        return gramaje;
    }

    public void setGramaje(Double gramaje) {
        this.gramaje = gramaje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPerodicidad() {
        return perodicidad;
    }

    public void setPerodicidad(String perodicidad) {
        this.perodicidad = perodicidad;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getCadaCuanto() {
        return cadaCuanto;
    }

    public void setCadaCuanto(int cadaCuanto) {
        this.cadaCuanto = cadaCuanto;
    }

    public Date getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(Date fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Boolean getAntesDespuesDeComer() {
        return antesDespuesDeComer;
    }

    public void setAntesDespuesDeComer(Boolean antesDespuesDeComer) {
        this.antesDespuesDeComer = antesDespuesDeComer;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }


}
