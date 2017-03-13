package MisClases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String CREATEDB = "CREATE TABLE Prueba (codigo VARCHAR(20) PRIMARY KEY, nombre VARCHAR(20) NOT NULL)";
    private static final String DELETEDB = "DROP TABLE IF EXISTS Prueba";

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory cursor, int version) {
        super(context, name, cursor, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATEDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETEDB);
        db.execSQL(CREATEDB);
    }
}