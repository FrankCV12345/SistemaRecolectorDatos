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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.srd.recursos.ambiente;
import com.example.srd.recursos.registroAmbiente;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.srd.recursos.recursos.CONTEXTLOGIN;
import static com.example.srd.recursos.recursos.CONTEXTLUXOMETRO;
import static com.example.srd.recursos.recursos.*;

public class listaAmbientes extends AppCompatActivity {
    private RequestQueue mQueque;
    private ListView lstViewAmbientes;
    private TextView textViewtitulo;
    private List<ambiente> lstaAmbientes = new ArrayList<>();
    private List<String>    lstaAmbientesString = new ArrayList<>();
    ProgressBar progresCircule;

    registroAmbiente rg ;
    String  nombrePiso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ambientes);
        Toolbar toolbar = findViewById(R.id.toolbarSRD);
        setSupportActionBar(toolbar);
        mQueque = Volley.newRequestQueue(getApplicationContext());
        lstViewAmbientes = findViewById(R.id.listaViewAmbientes);
        textViewtitulo = findViewById(R.id.textViewTituloAmbientes);
        nombrePiso = getIntent().getStringExtra("nombrePiso");
        textViewtitulo.setText("Lista ambientes en :"+nombrePiso);
        int idPiso = getIntent().getIntExtra("idPiso",0);
        consumirWSListaAmbientePorPiso(idPiso);
        progresCircule = findViewById(R.id.progressCircular);
        lstViewAmbientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int idAmbiente = lstaAmbientes.get(position).getIdAnbiente();
                    consumirWSregistro(idAmbiente, position);


            }
        });

    }



    private void consumirWSListaAmbientePorPiso(int piso){
        if(piso != 0){

            String URL = getIPSERVICIO()+getRercuroRelacionPisoAmbiente(piso);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0 ; i<response.length();i++ ){
                        try {
                            JSONObject jsonObjectAmbiente =  response.getJSONObject(i);
                            lstaAmbientes.add(new ambiente(jsonObjectAmbiente.getString("nombreAmbiente"),jsonObjectAmbiente.getInt("idAnbiente"),jsonObjectAmbiente.getInt("idPisoDeAmbiente")));
                            lstaAmbientesString.add(jsonObjectAmbiente.getString("nombreAmbiente"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    lstViewAmbientes.setAdapter( new ArrayAdapter<String>(
                            getApplicationContext(),
                            R.layout.list_item,
                            lstaAmbientesString));
                    progresCircule.setVisibility(View.INVISIBLE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueque.add(request);
        }else{
            Toast.makeText(getApplicationContext(),"el id llego como 0",Toast.LENGTH_SHORT).show();
        }
    }

    private void consumirWSregistro(int idambiente, final int position){
        String URL = getIPSERVICIO()+ getRecursoRegistrodatosambiente()+"/"+idambiente;
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String nombreAmbiente = lstaAmbientes.get(position).getNombreAmbiente();
                    if(response.getInt("statuusHttp") == 200){
                        Gson gson = new Gson();
                         registroAmbiente rA = gson.fromJson(response.toString(),registroAmbiente.class);

                        Intent intent = new Intent(getApplicationContext(),ActualizaRegistroAmbiente.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("registroAmbiente",rA);
                        intent.putExtras(bundle);
                        intent.putExtra("nombrePiso",nombrePiso);
                        intent.putExtra("nombreAmbiente",nombreAmbiente);
                        startActivity(intent);

                    }else{
                        Intent intent = new Intent(getApplicationContext(),RegistroDatosAmbiente.class);
                        intent.putExtra("nombrePiso",nombrePiso);
                        intent.putExtra("nombreAmbiente",nombreAmbiente);
                        intent.putExtra("idAmbiente",lstaAmbientes.get(position).getIdAnbiente());
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        mQueque.add(request);
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
