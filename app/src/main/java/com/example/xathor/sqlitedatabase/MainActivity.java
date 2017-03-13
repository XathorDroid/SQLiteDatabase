package com.example.xathor.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnInsertar, btnBorrar, btnModificar, btnVerUno, btnVerVarios;

    private final byte INSERTAR = 1;
    private final byte BORRAR = 2;
    private final byte MODIFICAR = 3;
    private final byte VERUNO = 4;
    private final byte VERVARIOS = 5;

    private byte bandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declaraciones();

        btnInsertar.setOnClickListener(btnClick);
        btnBorrar.setOnClickListener(btnClick);
        btnModificar.setOnClickListener(btnClick);
        btnVerUno.setOnClickListener(btnClick);
        btnVerVarios.setOnClickListener(btnClick);
    }

    public void declaraciones() {
        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnBorrar = (Button)findViewById(R.id.btnBorrar);
        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnVerUno = (Button)findViewById(R.id.btnVerUno);
        btnVerVarios = (Button)findViewById(R.id.btnVerVarios);
    }

    public View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnInsertar:
                    bandera = INSERTAR;
                    break;
                case R.id.btnBorrar:
                    bandera = BORRAR;
                    break;
                case R.id.btnModificar:
                    bandera = MODIFICAR;
                    break;
                case R.id.btnVerUno:
                    bandera = VERUNO;
                    break;
                case R.id.btnVerVarios:
                    bandera = VERVARIOS;
                    break;
            }
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra("bandera", bandera);
            startActivity(intent);
        }
    };

}