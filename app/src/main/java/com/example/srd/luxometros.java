package com.example.srd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.srd.recursos.recursos.*;
import com.example.srd.recursos.luxometro;
public class luxometros extends AppCompatActivity {
    ImageView imageViewLuxmetro;
    RequestQueue mqueque;
    Spinner spinnerLuxometro;
    List<String> lstaStringLuxometro = new ArrayList<>();
    List<luxometro> lstaLuxometroModelo = new ArrayList<>();
    luxometro lux;
    Button confirmaLuxometro;
    ProgressBar progresCircule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxometros);
        CONTEXTLUXOMETRO = this;
        mqueque = Volley.newRequestQueue(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbarSRD);
        setSupportActionBar(toolbar);
        confirmaLuxometro = findViewById(R.id.btnConfirmaLuxometro);
        imageViewLuxmetro = findViewById(R.id.imageViewLuxometro);
        spinnerLuxometro = findViewById(R.id.spinnerLuxometros);
        progresCircule = findViewById(R.id.progressCircular);
        spinnerLuxometro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 lux = lstaLuxometroModelo.get(position);
                 Picasso.with(getApplicationContext()).load(getIprecursoservicio()+lux.getUrlimgLuxometro()).into(imageViewLuxmetro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        confirmaLuxometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaults("idLuxometro",""+lux.getIdluxometro(),getApplicationContext());
                Toast.makeText(getApplicationContext(),"Luxometro "+lux.getNombreluxometro()+" agregado correctamente",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),listaPisosActivity.class));
                }
        });
        consumirWSLuxometro();
    }

    private void consumirWSLuxometro() {
      String URL = getIPSERVICIO() + getRecursoLuxometro();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int  i = 0 ;  i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        luxometro lux = new luxometro(jsonObject.getInt("idluxometro"),jsonObject.getString("nombreluxometro"),jsonObject.getString("urlimgLuxometro"));
                        lstaLuxometroModelo.add(lux);
                        lstaStringLuxometro.add(jsonObject.getString("nombreluxometro"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                spinnerLuxometro.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaStringLuxometro));
                progresCircule.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
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
