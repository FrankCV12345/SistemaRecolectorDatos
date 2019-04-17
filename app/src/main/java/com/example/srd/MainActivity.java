package com.example.srd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextNombreUsuario,editTextPassword;
    Button  buttonIngresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNombreUsuario = findViewById(R.id.EditTextNomUsu);
        editTextPassword = findViewById(R.id.editTextPasword);
        buttonIngresa = findViewById(R.id.buttonIngresar);
        buttonIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean estado =  validaLogin();
                if(estado){
                    Intent intent = new Intent(getApplicationContext(),listaPisosActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    private boolean validaLogin() {

        String nomUsu = editTextNombreUsuario.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean estado = false ;
        if(nomUsu.equals("") || password.equals("") ){
            Toast.makeText(getApplicationContext(),"NO SE PERMITEN CAMPOS VACIOS", Toast.LENGTH_LONG).show();
            estado = false;
        }else {

            estado = true;
        }
        return estado;
    }
}
