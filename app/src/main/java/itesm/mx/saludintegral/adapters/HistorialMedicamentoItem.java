package itesm.mx.saludintegral.adapters;

import java.util.Date;

/**
 * Created by Héctor on 5/7/2018.
 */

public class HistorialMedicamentoItem {

    private String nombreMed;
    private Date fechaTomado;
    private Boolean tomadTiempo;

    public HistorialMedicamentoItem(String nombreMed, Date fechaTomado, Boolean tomadTiempo) {
        this.nombreMed = nombreMed;
        this.fechaTomado = fechaTomado;
        this.tomadTiempo = tomadTiempo;
    }

    public String getNombreMed() {
        return nombreMed;
    }

    public void setNombreMed(String nombreMed) {
        this.nombreMed = nombreMed;
    }

    public Date getFechaTomado() {
        return fechaTomado;
    }

    public void setFechaTomado(Date fechaTomado) {
        this.fechaTomado = fechaTomado;
    }

    public Boolean getTomadTiempo() {
        return tomadTiempo;
    }

    public void setTomadTiempo(Boolean tomadTiempo) {
        this.tomadTiempo = tomadTiempo;
    }
}
