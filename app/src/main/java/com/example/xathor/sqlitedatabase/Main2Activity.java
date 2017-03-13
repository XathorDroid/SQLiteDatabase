package com.example.xathor.sqlitedatabase;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import MisClases.MyDbHelper;

public class Main2Activity extends AppCompatActivity {

    private Button btnOk, btnVolver;
    private EditText etNombre, etCodigo;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase myDb;

    private static final String BDNAME = "PruebaDB";
    private static final String TABLENAME = "Prueba";
    private static final int BDVERSION = 1;

    private int bandera;
    private String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        declaraciones();
        ocultarVistas();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = "", codigo = "", toastMsg = "";
                if(etCodigo.getVisibility() == View.VISIBLE) {
                    codigo = etCodigo.getText().toString();

                    if (etNombre.getVisibility() == View.VISIBLE) {
                        nombre = etNombre.getText().toString();
                    }
                }

                toastMsg = accionesBD(nombre, codigo);

                if(bandera != 2) {
                    Toast.makeText(Main2Activity.this, toastMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.close();
                finish();
            }
        });

        if(bandera == 5) {
            Toast.makeText(this, accionesBD(null, null), Toast.LENGTH_SHORT).show();
        }
    }

    public void declaraciones() {
        btnOk = (Button)findViewById(R.id.btnAceptar);
        btnVolver = (Button)findViewById(R.id.btnVolver);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etCodigo = (EditText)findViewById(R.id.etCodigo);

        myDbHelper = new MyDbHelper(Main2Activity.this, BDNAME, null, BDVERSION);
        myDb = myDbHelper.getWritableDatabase();
    }

    public void ocultarVistas() {
        Intent intent = getIntent();
        bandera = intent.getExtras().getByte("bandera");
        switch(bandera) {
            case 1:
                etNombre.setVisibility(View.VISIBLE);
                etCodigo.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
                break;
            case 2:
                etNombre.setVisibility(View.GONE);
                etCodigo.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
                break;
            case 3:
                etNombre.setVisibility(View.VISIBLE);
                etCodigo.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
                break;
            case 4:
                etNombre.setVisibility(View.GONE);
                etCodigo.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
                break;
            case 5:
                etNombre.setVisibility(View.GONE);
                etCodigo.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
                break;
        }
    }

    public String accionesBD(String nombre, final String codigo) {
        msg = "";

        switch(bandera) {
            case 1:
                ContentValues registroIns = new ContentValues();
                registroIns.put("codigo", codigo);
                registroIns.put("nombre", nombre);

                myDb.insert(TABLENAME, null, registroIns);

                msg = "Registro de "+nombre+" con Código "+codigo+" realizado!";
                break;
            case 2:
                AlertDialog.Builder dialogDel = new AlertDialog.Builder(Main2Activity.this);
                dialogDel.setIcon(R.drawable.delete);
                dialogDel.setTitle(getString(R.string.msgDel));
                dialogDel.setMessage(getString(R.string.msgDelText)+" "+codigo+"?");
                dialogDel.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogDel.setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String clauseDel = "codigo = '"+codigo+"'";
                        myDb.delete(TABLENAME, clauseDel, null);

                        Toast.makeText(Main2Activity.this, "Se ha borrado el Nombre con Código "+codigo, Toast.LENGTH_SHORT).show();
                    }
                });
                dialogDel.create().show();
                break;
            case 3:
                String clauseMod = "codigo = '"+codigo+"'";

                ContentValues registroMod = new ContentValues();
                registroMod.put("codigo", codigo);
                registroMod.put("nombre", nombre);

                myDb.update(TABLENAME, registroMod, clauseMod, null);

                msg = "Modificación Realizada al Nombre de Código "+codigo;
                break;
            case 4:
                String clauseUno = "codigo = '"+codigo+"'";
                String[] datosUno = {"nombre"};
                Cursor cursosUno = myDb.query(TABLENAME, datosUno, clauseUno, null, null, null, null);
                if(cursosUno.moveToFirst()) {
                    msg = "Con el Código "+codigo+" tenemos a: "+cursosUno.getString(0);
                }
                break;
            case 5:
                String[] datosVarios = {"nombre"};
                Cursor cursorVarios = myDb.query(TABLENAME, datosVarios, null, null, null, null, null);
                if(cursorVarios.moveToFirst()) {
                    do {
                        if(cursorVarios.getPosition() == 0) {
                            msg += "Nombre: " + cursorVarios.getString(0);
                        } else {
                            msg += "\nNombre: " + cursorVarios.getString(0);
                        }
                    } while(cursorVarios.moveToNext());
                }
                break;
        }
        return msg;
    }

}