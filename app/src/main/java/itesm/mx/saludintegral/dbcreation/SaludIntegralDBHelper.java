package itesm.mx.saludintegral.dbcreation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.PublicKey;

//////////////////////////////////////////////////////////
//Clase: EventoDBHelper.java
// Descripción: Empleada para hacer la creación de la Base de Datos.
// Autor: Fernando David Romero Nava  A01039364
// Fecha de creación: 14/04/2018
// Fecha de última modificación: 14/04/2018
//////////////////////////////////////////////////////////

public class SaludIntegralDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SaludIntegralDB.db";
    private static final int DATABASE_VERSION = 1;

    //Crea la base de datos
    public SaludIntegralDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Crea las tablas de la BD.
    @Override
    public void onCreate (SQLiteDatabase db) {
        String CREATE_MEDICAMENTO_TABLE = "CREATE TABLE " +
                DataBaseSchema.MedicamentoTable.TABLE_NAME +
                "(" +
                DataBaseSchema.MedicamentoTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_NOMBRE + " TEXT," +
                //SI HAY FALLAS, CAMBIA DE REAL A TEXT
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_GRAMAJE + " REAL," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_CANTIDAD + " INTEGER," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_PERIOCIDAD + " TEXT," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_HORA + " TEXT," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_CADACUANTO + " INTEGER," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_COMIENZO + " TEXT," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_TERMINO + " TEXT," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_INGERIRANTESDESPUESCOMIDA + " TEXT," +
                DataBaseSchema.MedicamentoTable.COLUMN_NAME_FOTO + " BLOB " +
                ")";
        Log.i("Producthelper onCreate1", CREATE_MEDICAMENTO_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_MEDICAMENTO_TABLE);

        String CREATE_TOMARMEDICAMENTO_TABLE = "CREATE TABLE " +
                DataBaseSchema.TomarMedicamentoTable.TABLE_NAME +
                "(" +
                DataBaseSchema.MedicamentoTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_ID_MEDICAMENTO + " INTEGER," +
                DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_TOMADOATIEMPO + " TEXT," +
                DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_FECHAHORA + " BLOB " +
                ")";
        Log.i("Producthelper onCreate2", CREATE_TOMARMEDICAMENTO_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_TOMARMEDICAMENTO_TABLE);

        String CREATE_EVENTO_TABLE = "CREATE TABLE " +
                DataBaseSchema.EventosTable.TABLE_NAME +
                "(" +
                DataBaseSchema.EventosTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE + " TEXT," +
                DataBaseSchema.EventosTable.COLUMN_NAME_DESCRICPION + " TEXT," +
                DataBaseSchema.EventosTable.COLUMN_NAME_FECHA + " TEXT," +

                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " TEXT, " +
                DataBaseSchema.EventosTable.COLUMN_NAME_IDEVENTOS+ " TEXT "+
                ")";
        Log.i("Producthelper onCreate3", CREATE_EVENTO_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_EVENTO_TABLE);

        String CREATE_CUMPLEANOS_TABLE = "CREATE TABLE " +
                DataBaseSchema.CumpleanosTable.TABLE_NAME +
                "(" +
                DataBaseSchema.CumpleanosTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_NOMBRE + " TEXT," +
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_FECHA + " TEXT," +
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_TIPO + " TEXT," +
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_TELEFONO +" TEXT " +
                ")";
        Log.i("Producthelper onCreate4", CREATE_CUMPLEANOS_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_CUMPLEANOS_TABLE);

        String CREATE_CONTACTOEMERGENCIA_TABLE = "CREATE TABLE " +
                DataBaseSchema.ContactoEmergenciaTable.TABLE_NAME +
                "(" +
                DataBaseSchema.ContactoEmergenciaTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.ContactoEmergenciaTable.COLUMN_NAME_NOMBRE + " TEXT," +
                DataBaseSchema.ContactoEmergenciaTable.COLUMN_NAME_TELEFONO + " TEXT " +
                ")";
        Log.i("Producthelper onCreate5", CREATE_CONTACTOEMERGENCIA_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_CONTACTOEMERGENCIA_TABLE);

        String CREATE_MONITOREOSUENO_TABLE = "CREATE TABLE " +
                DataBaseSchema.MonitoreoSuenoTable.TABLE_NAME +
                "(" +
                DataBaseSchema.MonitoreoSuenoTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.MonitoreoSuenoTable.COLUMN_NAME_FECHA + " TEXT," +
                DataBaseSchema.MonitoreoSuenoTable.COLUMN_NAME_HORAS + " TEXT " +
                ")";
        Log.i("Producthelper onCreate6", CREATE_MONITOREOSUENO_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_MONITOREOSUENO_TABLE);

        String CREATE_INFOPERSONAL_TABLE = "CREATE TABLE " +
                DataBaseSchema.InfoPersonalTable.TABLE_NAME +
                "(" +
                DataBaseSchema.InfoPersonalTable._ID + " INTEGER PRIMARY KEY," +
                DataBaseSchema.InfoPersonalTable.COLUMN_NAME_NOMBRE + " TEXT," +
                DataBaseSchema.InfoPersonalTable.COLUMN_NAME_APODO + " TEXT," +
                DataBaseSchema.InfoPersonalTable.COLUMN_NAME_FECHANACIMIENTO + " TEXT," +
                DataBaseSchema.InfoPersonalTable.COLUMN_NAME_CIUDAD + " TEXT," +
                DataBaseSchema.InfoPersonalTable.COLUMN_NAME_PAIS + " TEXT," +
                DataBaseSchema.InfoPersonalTable.COLUMN_NAME_FOTO + " BLOB " +
                ")";
        Log.i("Producthelper onCreate7", CREATE_INFOPERSONAL_TABLE);
        // Ejecuta la instrucción de sql.
        db.execSQL(CREATE_INFOPERSONAL_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DELETE_MEDICAMENTO_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.MedicamentoTable.TABLE_NAME;
        db.execSQL(DELETE_MEDICAMENTO_TABLE);

        String DELETE_TOMARMEDICAMENTO_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.TomarMedicamentoTable.TABLE_NAME;
        db.execSQL(DELETE_TOMARMEDICAMENTO_TABLE);

        String DELETE_EVENTOS_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.EventosTable.TABLE_NAME;
        db.execSQL(DELETE_EVENTOS_TABLE);

        String DELETE_CUMPLEANOS_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.CumpleanosTable.TABLE_NAME;
        db.execSQL(DELETE_CUMPLEANOS_TABLE);

        String DELETE_CONTACTOEMERGENCIA_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.ContactoEmergenciaTable.TABLE_NAME;
        db.execSQL(DELETE_CONTACTOEMERGENCIA_TABLE);

        String DELETE_MONITOREOSUENO_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.MonitoreoSuenoTable.TABLE_NAME;
        db.execSQL(DELETE_MONITOREOSUENO_TABLE);

        String DELETE_INFOPERSONAL_TABLE = "DROP TABLE IF EXISTS " + DataBaseSchema.InfoPersonalTable.TABLE_NAME;
        db.execSQL(DELETE_INFOPERSONAL_TABLE);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
