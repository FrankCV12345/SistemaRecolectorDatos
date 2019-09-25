package com.example.srd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.srd.recursos.recursos.*;

public class dicionario extends AppCompatActivity {
    EditText editTextNombreBuscar;
    TextView  TextViewResultado;
    Button btnBuscaLuminaria , btnBuscaSensor;
    RequestQueue mqueque;
    ImageView imageViewfoto;
    JSONObject jsonObjectDetalles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicionario);
        Toolbar toolbar = findViewById(R.id.toolbarSRD);
        setSupportActionBar(toolbar);
        editTextNombreBuscar = findViewById(R.id.editeTextNombreSensorLumina);
        btnBuscaLuminaria = findViewById(R.id.btnbuscaLuminaria);
        btnBuscaSensor = findViewById(R.id.btnBuscarSersor);
        imageViewfoto = findViewById(R.id.imageViewDicionario);
        TextViewResultado = findViewById(R.id.textViewResultNombreCompleto);
        mqueque = Volley.newRequestQueue(getApplicationContext());

        btnBuscaSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscaDeltalle(editTextNombreBuscar.getText().toString(),"S");
            }
        });

        btnBuscaLuminaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscaDeltalle(editTextNombreBuscar.getText().toString(),"L");
            }
        });


    }
    private void BuscaDeltalle(String v, final String definidor){
        final String URL = getIPSERVICIO()+getRecursoDetalle()+"?abrebiacion="+v+"&definidor="+definidor;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    System.out.println("URL : "+URL + " tamano de la respuesta: "+response.length());
                    if(response.length() > 0){

                        jsonObjectDetalles = response.getJSONObject(0);
                        if(definidor.equals("S")){
                            TextViewResultado.setText(jsonObjectDetalles.getString("nombreSensor"));
                            Picasso.with(getApplicationContext()).load(getIprecursoservicio()+jsonObjectDetalles.getString("urlImg")).into(imageViewfoto);
                        }else if (definidor.equals("L")){
                            TextViewResultado.setText(jsonObjectDetalles.getString("nombreLum"));
                            Picasso.with(getApplicationContext()).load(getIprecursoservicio()+jsonObjectDetalles.getString("urlimg")).into(imageViewfoto);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"NO HAY RESULTADOS",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mqueque.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.itemlstaPisosNoAprobados:
                startActivity(new Intent(getApplicationContext(),lstaPisosDesarpobados.class));
                break;
            case R.id.itemLuxometro:
                startActivity(new Intent(getApplicationContext(),luxometros.class));
                break;
            case R.id.itemLstaPisos :
                startActivity(new Intent(getApplicationContext(),listaPisosActivity.class));
                break;
            case R.id.itemCerrarSesion :
                setDefaults("nombreUser",null,CONTEXTLOGIN);
                setDefaults("idUser",null,CONTEXTLOGIN);
                setDefaults("idLuxometro",null,CONTEXTLUXOMETRO);
                startActivity(new Intent( getApplicationContext(),MainActivity.class));
                finish();
                break;
            case R.id.itemDiccionario :
                startActivity(new Intent(getApplicationContext(),dicionario.class));
                break;
            default:
                Toast.makeText(getApplicationContext(),"algo salio mal",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


}
