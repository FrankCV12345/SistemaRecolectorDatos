package com.example.srd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.srd.recursos.luminaria;
import com.example.srd.recursos.medicion;
import com.example.srd.recursos.registroAmbiente;
import com.example.srd.recursos.registroLuminariasInAmbiente;
import com.example.srd.recursos.registroSensorInAmbiente;
import com.example.srd.recursos.sensor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import static com.example.srd.recursos.recursos.CONTEXTLOGIN;
import static com.example.srd.recursos.recursos.CONTEXTLUXOMETRO;
import static com.example.srd.recursos.recursos.convertEditTextDouble;
import static com.example.srd.recursos.recursos.convertEditTextToInt;
import static com.example.srd.recursos.recursos.getIPSERVICIO;
import static com.example.srd.recursos.recursos.getRecursoLuminaria;
import static com.example.srd.recursos.recursos.getRecursoRegistrodatosambiente;
import static com.example.srd.recursos.recursos.getRecursoSensor;
import static com.example.srd.recursos.recursos.setDefaults;

public class ActualizaRegistroAmbiente extends AppCompatActivity {
    EditText editTextFecha,editTextNombrePiso,editTextNommbreAmbiente,
            editTextAlturaMedicion,editTextResultMedi,editTextDescripcionMedi,
            editTextCantLuminaria,editpruebaJson,editTextCantSensores;
    ListView listViewMediciones, listViewLuminarias, listViewSensores;
    Spinner spinnerTipoLuminaria , spinnerTiposensor,spinnerEstadoLumi,spinnerEstadoSensor;
    Button btnAddMedicion,btnAddLuminaria,btnAddSensor,btnActualizar,btnCancelar;
    List<luminaria> lstLuminaria = new ArrayList<>();
    List<sensor> lstaSensores = new ArrayList<>();
    List<String> lstaLuminariasStringSpinner = new ArrayList<>();
    List<String> lstaMedicionesString = new ArrayList<>();
    List<String> lstaLuminariasString = new ArrayList<>();
    List<String> lstaSensorString = new ArrayList<>();
    List<String> lstaSensorStringlstView = new ArrayList<>();
    ProgressBar progresCircule;
    registroAmbiente ra = null;
    boolean estadoSensor,estadoLuminaria;
    int nromedicion = 0,idLuminaria,idSensor;
    RequestQueue mqueque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualiza_registro_ambiente);
        Bundle RegistroRecibido = getIntent().getExtras();


        Toolbar toolbar = findViewById(R.id.toolbarSRD);
        setSupportActionBar(toolbar);
        mqueque = Volley.newRequestQueue(getApplicationContext());
        editTextFecha = findViewById(R.id.fecha);
        editTextNombrePiso = findViewById(R.id.nombrePiso);
        editTextNommbreAmbiente = findViewById(R.id.nombreAmbiente);
        editTextAlturaMedicion = findViewById(R.id.alturaMedicion);
        editTextCantLuminaria = findViewById(R.id.cantLuminaria);
        editTextCantSensores = findViewById(R.id.cantSensores);
        listViewMediciones = findViewById(R.id.lstaViewMediciones);
        listViewLuminarias = findViewById(R.id.listViewLuminarias);
        listViewSensores = findViewById(R.id.listViewSensores);
        spinnerTipoLuminaria = findViewById(R.id.spinnerLuminarias);
        spinnerTiposensor = findViewById(R.id.spinnerSensores);
        spinnerEstadoLumi = findViewById(R.id.spinnerEstatoLum);
        spinnerEstadoSensor = findViewById(R.id.spinnerEstadoSensores);
        btnAddMedicion = findViewById(R.id.btnAddMedicion);
        btnAddLuminaria = findViewById(R.id.btnAddLuminaria);
        btnAddSensor = findViewById(R.id.btnaddSensores);
        btnActualizar = findViewById(R.id.ActualizaRegistro);
        btnCancelar = findViewById(R.id.cancelar);
        editTextResultMedi = findViewById(R.id.ResultMedicion);
        editTextDescripcionMedi = findViewById(R.id.DescripcionMedicion);
        progresCircule = findViewById(R.id.progressCircular);
        //editpruebaJson = findViewById(R.id.editpruebaJson);

        if(RegistroRecibido != null){
            ra = (registroAmbiente) RegistroRecibido.getSerializable("registroAmbiente");
            editTextFecha.setText(ra.getFecha());
            editTextAlturaMedicion.setText(""+ra.getAlturaMedicion());
            editTextNommbreAmbiente.setText(getIntent().getStringExtra("nombreAmbiente"));
            editTextNombrePiso.setText( getIntent().getStringExtra("nombrePiso"));
            llenlaListViewMediciones();
            listarLuminarias();
            listarSensor();
            progresCircule.setVisibility(View.INVISIBLE);
        }

        btnAddMedicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextResultMedi.getText().toString().equals("")){
                    nromedicion+=1;

                    if(editTextDescripcionMedi.getText().toString().equals("")){
                        medicion med =new medicion(
                                nromedicion,
                                convertEditTextDouble(editTextResultMedi)
                                ," ");

                        List<medicion> lstaMed = ra.getLstaMediciones();
                        lstaMed.add(med);
                        ra.setLstaMediciones(lstaMed);
                        lstaMedicionesString.add("Nro : "+med.getNroMedicionl()+" Result : "+med.getResultado()+" Descrip : "+med.getDescripcion());
                    }else{
                        medicion med =new medicion(
                                nromedicion,
                                Double.parseDouble( editTextResultMedi.getText().toString())
                                ,editTextDescripcionMedi.getText().toString());

                        List<medicion> lstaMed = ra.getLstaMediciones();
                        lstaMed.add(med);
                        ra.setLstaMediciones(lstaMed);
                        lstaMedicionesString.add("Nro : "+med.getNroMedicionl()+" Result : "+med.getResultado()+" Descrip : "+med.getDescripcion());

                    }
                    editTextResultMedi.setText("");
                    editTextDescripcionMedi.setText("");
                    listViewMediciones.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaMedicionesString));

                }else{
                    Toast.makeText(getApplicationContext(),"falta completar campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ArrayAdapter<CharSequence> adapterestadosLum =  ArrayAdapter.createFromResource(getApplicationContext(),R.array.estados,R.layout.spinner_item_estado);
        spinnerEstadoLumi.setAdapter(adapterestadosLum);

        spinnerEstadoLumi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    estadoLuminaria = true;
                }else if(position == 1){
                    estadoLuminaria  = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTipoLuminaria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idLuminaria = lstLuminaria.get(position).getIdluminaria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAddLuminaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<registroLuminariasInAmbiente> lstaRgtoLuminarias = ra.getLstregluminariasInAmbiente();
                int cantidad = convertEditTextToInt(editTextCantLuminaria);
                registroLuminariasInAmbiente rgLum = new registroLuminariasInAmbiente(idLuminaria,cantidad,estadoLuminaria);
                lstaRgtoLuminarias.add(rgLum);
                ra.setLstregluminariasInAmbiente(lstaRgtoLuminarias);
                lstaLuminariasString.add("Lumin : "+spinnerTipoLuminaria.getSelectedItem().toString()+" estado: "+Estado(rgLum.isEstado())+" Cant :"+rgLum.getCantidad());
                listViewLuminarias.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaLuminariasString));
                editTextCantLuminaria.setText("");
            }
        });

        spinnerTiposensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idSensor = lstaSensores.get(position).getIdsensor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapterEstadosSensor = ArrayAdapter.createFromResource(getApplicationContext(),R.array.estados,R.layout.spinner_item_estado);
        spinnerEstadoSensor.setAdapter(adapterEstadosSensor);
        spinnerEstadoSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    estadoSensor = true;
                }else if(position == 1){
                    estadoSensor  = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAddSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<registroSensorInAmbiente> lstaRegSensore = ra.getLstregistroSensorInAmbiente();
                int cantidad = convertEditTextToInt(editTextCantSensores);
                registroSensorInAmbiente rgSen = new registroSensorInAmbiente(idSensor,cantidad,estadoSensor);
                lstaRegSensore.add(rgSen);
                ra.setLstregistroSensorInAmbiente(lstaRegSensore);
                lstaSensorStringlstView.add("Tip. Sens. :"+spinnerTiposensor.getSelectedItem().toString()+" cant : "+rgSen.getCantidad()+" estado : "+Estado(rgSen.isEstado()));
                listViewSensores.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaSensorStringlstView));
                editTextCantSensores.setText("");
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                ra.setFecha(editTextFecha.getText().toString());
                ra.setAlturaMedicion(Double.parseDouble( editTextAlturaMedicion.getText().toString()));
                String jsnoRegistro = gson.toJson(ra,registroAmbiente.class);
                if(editTextFecha.getText().toString().equals("") || editTextAlturaMedicion.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"ALGUNO CAMPOS ESTAN VACIOS",Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        ActualizarDatosAmbiente(jsnoRegistro);
                        ra = null;
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),listaPisosActivity.class));
            }
        });
    }

    private void llenlaListViewMediciones(){
        List<medicion> mediciones = ra.getLstaMediciones();
        for(int i =0 ; i< mediciones.size();i ++){
            lstaMedicionesString.add("nro :"+mediciones.get(i).getNroMedicionl()+" Result : "+mediciones.get(i).getResultado()+" Descrip : "+mediciones.get(i).getDescripcion());
            nromedicion = mediciones.get(i).getNroMedicionl();
        }
        listViewMediciones.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaMedicionesString));
    }

    private void llenaListViewLuminarias(){
        List<registroLuminariasInAmbiente> luminarias = ra.getLstregluminariasInAmbiente();
        for(int i =0 ; i < luminarias.size(); i ++ ){
            String nombreLum = "nada";
            for(int w =0 ; w< lstLuminaria.size();w++){
                if( lstLuminaria.get(w).getIdluminaria() == luminarias.get(i).getIdLum()){
                    nombreLum = lstLuminaria.get(w).getAbrebiasion();
                    lstaLuminariasString.add("Nomb : "+nombreLum +" Estado : "+Estado(luminarias.get(i).isEstado())+" Cant :"+luminarias.get(i).getCantidad());
                }
            }
            }
        listViewLuminarias.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),R.layout.spinner_item_estado,lstaLuminariasString

        ));
    }

    private String Estado(boolean estado){
        if(estado == true){
            return "Operativo";
        }else {
            return "Inoperativo";
        }
    }
    private void listarLuminarias() {
        String URL = getIPSERVICIO()+getRecursoLuminaria();
        JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i< response.length(); i++){
                    try {
                        JSONObject jsonObjectLuminaria =  response.getJSONObject(i);
                        lstLuminaria.add(new luminaria(jsonObjectLuminaria.getInt("idluminaria"),jsonObjectLuminaria.getString("abrebiasion")));
                        lstaLuminariasStringSpinner.add(jsonObjectLuminaria.getString("abrebiasion"));
                       } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                llenaListViewLuminarias();

                spinnerTipoLuminaria.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaLuminariasStringSpinner));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        mqueque.add(request);

    }
    private void llenaListViewSensor(){
        List<registroSensorInAmbiente> lstaSensoresInAmiente = ra.getLstregistroSensorInAmbiente();
        for(int i = 0 ; i < lstaSensoresInAmiente.size(); i++){
            for(int a =0 ;a < lstaSensores.size(); a++ ){
                if( lstaSensoresInAmiente.get(i).getIdSensor() ==lstaSensores.get(a).getIdsensor()){
                    System.out.println(lstaSensoresInAmiente.get(i).getIdSensor() +" == "+lstaSensores.get(a).getIdsensor());
                    lstaSensorStringlstView.add(lstaSensores.get(i).getAbreviasion()+" Estado : "+Estado(lstaSensoresInAmiente.get(i).isEstado())+" Cant : "+lstaSensoresInAmiente.get(i).getCantidad());
                }
            }
        }
        listViewSensores.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaSensorStringlstView));
    }
    private void listarSensor() {
        String URL = getIPSERVICIO()+getRecursoSensor();
        JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i< response.length(); i++){
                    try {
                        JSONObject jsonObjectSensor =  response.getJSONObject(i);
                        lstaSensores.add(new sensor(jsonObjectSensor.getInt("idsensor"),jsonObjectSensor.getString("abreviasion")));
                        lstaSensorString.add(jsonObjectSensor.getString("abreviasion"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                llenaListViewSensor();
                spinnerTiposensor.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaSensorString));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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

    private void ActualizarDatosAmbiente( String jsonRegistro) throws JSONException, ParseException {
        String URL = getIPSERVICIO()+getRecursoRegistrodatosambiente();
        JSONObject jsonRegistrosend = new JSONObject(jsonRegistro);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, jsonRegistrosend,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Toast.makeText(getApplicationContext(), "Actualizado correctamente con el "+jsonObject.getString("respuesta"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        mqueque.add(request);
    }
}
