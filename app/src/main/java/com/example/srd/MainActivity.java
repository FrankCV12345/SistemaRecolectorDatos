package com.example.srd;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.srd.recursos.Usuario;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.IntSummaryStatistics;

import static com.example.srd.recursos.recursos.CONTEXTLOGIN;
import static com.example.srd.recursos.recursos.getDefaults;
import static com.example.srd.recursos.recursos.getIPSERVICIO;
import static com.example.srd.recursos.recursos.getRecursoUser;
import static com.example.srd.recursos.recursos.setDefaults;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText editTextNombreUsuario,editTextPassword;
    Button  buttonIngresa,buttonLimipia;
    String nomUsu;
    String password;
    /*String url = "http://192.168.1.36:8080/WS_sistemaRecolectorDatosV2/api/";
    String recurso = "user";
    ProgressDialog progreso;*/
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            CONTEXTLOGIN = this;
            if (getDefaults("idUser",CONTEXTLOGIN) != null){
                startActivity(new Intent(getApplicationContext(),luxometros.class));
            }

            editTextNombreUsuario = findViewById(R.id.EditTextNomUsu);
            editTextPassword = findViewById(R.id.editTextPasword);
            buttonIngresa = findViewById(R.id.buttonIngresar);
            buttonLimipia = findViewById(R.id.buttonLimpiar);
            request = Volley.newRequestQueue(getApplicationContext());
        buttonIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 validaLogin();
            }
        });
        buttonLimipia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNombreUsuario.setText("");
                editTextPassword.setText("");
            }
        });

    }
    private boolean validaLogin() {
        nomUsu = editTextNombreUsuario.getText().toString();
        password = editTextPassword.getText().toString();
        boolean estado = false ;
        if(nomUsu.equals("") || password.equals("") ){
            Toast.makeText(getApplicationContext(),"FALTA INGREDAR DATOS",Toast.LENGTH_SHORT).show();
            //estado = false;
        }else {
            String urlLogin = getIPSERVICIO()+getRecursoUser()+"?nombre="+nomUsu+"&password="+password;
            urlLogin = urlLogin.replace(" ","%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,urlLogin,null,this,this);
            request.add(jsonObjectRequest);
        }
        return estado;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"error!!!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario usuario = new Usuario();
        JSONObject rptaUser = response;
        usuario.setNombreUser(rptaUser.optString("nombreUser"));
        usuario.setIdUser( Integer.parseInt( rptaUser.optString("iduser")));
        if(usuario.getNombreUser().equals("none")){
            Toast.makeText(getApplicationContext(),"No se pudo iniciar sesion " ,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext()," BIENVENIDO : "+ usuario.getNombreUser() ,Toast.LENGTH_SHORT).show();
            setDefaults("nombreUser",nomUsu,getApplicationContext());
            setDefaults("idUser",""+usuario.getIdUser(),getApplicationContext());
            Intent intent = new Intent(getApplicationContext(),luxometros.class);
            startActivity(intent);
        }
    }
}
