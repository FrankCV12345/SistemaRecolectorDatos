package com.example.srd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.srd.recursos.recursos.*;

import java.util.ArrayList;
import java.util.List;

public class lstaPisosDesarpobados extends AppCompatActivity {
    private ListView listViewPisosDesaprobados;
    private List<String> listaPisoDesaprobadosString = new ArrayList<>();
    private RequestQueue mQueque;
    ProgressBar progresCircule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsta_pisos_desarpobados);
        mQueque = Volley.newRequestQueue(getApplicationContext());
        listViewPisosDesaprobados = findViewById(R.id.listViewPisosDesaprobados);
        progresCircule = findViewById(R.id.progressCircular);
        Toolbar toolbar = findViewById(R.id.toolbarSRD);
        setSupportActionBar(toolbar);
        lstaPisosNoAprobados();
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


    private void lstaPisosNoAprobados(){
        String  URL = getIPSERVICIO()+getRecursoListapisosdesaprobados();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int  i =0 ; i < response.length(); i++){
                    try {
                        JSONObject jsonAmbienteNoAprobado = response.getJSONObject(i);
                        listaPisoDesaprobadosString.add("Nom. Ambiente : "+jsonAmbienteNoAprobado.getString("nombreAmbiente") +" Nomb. Piso : "+jsonAmbienteNoAprobado.getString("nombrePiso") + " Prom. " +jsonAmbienteNoAprobado.getDouble("promedio") );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listViewPisosDesaprobados.setAdapter( new ArrayAdapter<String>( getApplicationContext(),R.layout.spinner_item_estado,listaPisoDesaprobadosString));
                progresCircule.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mQueque.add(request);
    }
}
