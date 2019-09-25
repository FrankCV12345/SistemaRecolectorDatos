package com.example.srd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.srd.recursos.piso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.srd.recursos.recursos.getIPSERVICIO;
import static com.example.srd.recursos.recursos.*;

public class listaPisosActivity extends AppCompatActivity {
    private List<String> listapisosString = new ArrayList<>();
    private List<piso> lstaPisos = new ArrayList<>();
    private ListView listaviewPisos;
    private int i =0;
    private RequestQueue mQueque;
    private TextView textView;
    ProgressBar progresCircule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pisos);
            Toolbar toolbar = findViewById(R.id.toolbarSRD);
            setSupportActionBar(toolbar);
        textView = findViewById(R.id.textView2);
        listaviewPisos = findViewById(R.id.listviewListaPisos);
        progresCircule = findViewById(R.id.progressCircular);
        mQueque = Volley.newRequestQueue(getApplicationContext());
        consumirWSLsitaPisos();
        listaviewPisos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),listaAmbientes.class);
                intent.putExtra("idPiso",lstaPisos.get(position).getIdPiso());
                intent.putExtra("nombrePiso",lstaPisos.get(position).getNombrePiso());
                startActivity(intent);
            }
        });
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


    private void consumirWSLsitaPisos() {
        String URL = getIPSERVICIO()+getRecursoPiso();
        JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i< response.length(); i++){
                    try {
                        JSONObject jsonObjectPiso =  response.getJSONObject(i);
                        lstaPisos.add(new piso(jsonObjectPiso.getInt("idPiso"),jsonObjectPiso.getString("nombrePiso")));
                        listapisosString.add(jsonObjectPiso.getString("nombrePiso"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listaviewPisos.setAdapter( new ArrayAdapter<String>( getApplicationContext(),R.layout.list_item,listapisosString));
                progresCircule.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        mQueque.add(request);
    }
}
