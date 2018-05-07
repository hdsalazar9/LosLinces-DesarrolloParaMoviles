package itesm.mx.saludintegral.dbcreation;

//////////////////////////////////////////////////////////
//Clase: DataBaseSchema.java
// Descripción: Empleada para obtener los campos que existen en la Base de Datos creada.
// Autor: Fernando David Romero Nava  A01039364
// Fecha de creación: 14/04/2018
// Fecha de última modificación: 14/04/2018
//////////////////////////////////////////////////////////
import android.provider.BaseColumns;

public final class DataBaseSchema {
    private DataBaseSchema() {
    }

    public static class MedicamentoTable implements BaseColumns {
        // El valor de los strings son los campos de la tabla.
        public static final String TABLE_NAME = "Medicamentos";
        public static final String COLUMN_NAME_NOMBRE = "med_nombre";
        public static final String COLUMN_NAME_GRAMAJE = "med_gramaje";
        public static final String COLUMN_NAME_CANTIDAD = "med_cantidad";
        public static final String COLUMN_NAME_PERIOCIDAD = "med_periodicidad";
        public static final String COLUMN_NAME_HORA = "med_hora";
        public static final String COLUMN_NAME_CADACUANTO = "med_cadaCuanto";
        public static final String COLUMN_NAME_COMIENZO = "med_comienzo";
        public static final String COLUMN_NAME_TERMINO = "med_termino";
        public static final String COLUMN_NAME_INGERIRANTESDESPUESCOMIDA = "med_ingerirAntesDespues";
        public static final String COLUMN_NAME_FOTO = "med_foto";
    }

    public static class TomarMedicamentoTable implements BaseColumns {
        public static final String TABLE_NAME = "TomarMedicamentos";
        public static final String COLUMN_NAME_ID_MEDICAMENTO = "tomed_ID_medicamento";
        public static final String COLUMN_NAME_TOMADOATIEMPO = "tomed_tomadoATiempo";
        public static final String COLUMN_NAME_FECHAHORA = "tomed_fechaHora";
    }

    public static class EventosTable implements BaseColumns {
        public static final String TABLE_NAME = "Eventos";
        public static final String COLUMN_NAME_NOMBRE = "eve_nombre";
        public static final String COLUMN_NAME_DESCRICPION = "eve_descripcion";
        public static final String COLUMN_NAME_FECHA = "eve_fecha";
        public static final String COLUMN_NAME_TIPO = "eve_tipo";
    }

    public static class CumpleanosTable implements BaseColumns {
        public static final String TABLE_NAME = "Cumpleanos";
        public static final String COLUMN_NAME_NOMBRE = "cumple_nombre";
        public static final String COLUMN_NAME_FECHA = "cumple_fecha";
        public static final String COLUMN_NAME_TIPO = "cumple_tipo";
        public static final String COLUMN_NAME_TELEFONO = "cumple_telefono";
    }

    public static class ContactoEmergenciaTable implements BaseColumns {
        public static final String TABLE_NAME = "ContactoEmergencia";
        public static final String COLUMN_NAME_NOMBRE = "conteme_nombre";
        public static final String COLUMN_NAME_TELEFONO = "conteme_telefono";
    }

    public static class MonitoreoSuenoTable implements BaseColumns {
        public static final String TABLE_NAME = "MonitoreoSueno";
        public static final String COLUMN_NAME_FECHA = "conteme_fecha";
        public static final String COLUMN_NAME_HORAS = "conteme_horas";
    }

    public static class InfoPersonalTable implements BaseColumns {
        public static final String TABLE_NAME = "InfoPersonal";
        public static final String COLUMN_NAME_NOMBRE = "person_nombre";
        public static final String COLUMN_NAME_APODO = "person_apodo";
        public static final String COLUMN_NAME_FECHANACIMIENTO = "person_fechaNacimiento";
        public static final String COLUMN_NAME_CIUDAD = "person_ciudad";
        public static final String COLUMN_NAME_PAIS = "person_pais";
        public static final String COLUMN_NAME_FOTO = "person_foto";

    }

}