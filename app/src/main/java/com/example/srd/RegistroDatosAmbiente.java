package com.example.srd;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.srd.recursos.luminaria;
import com.example.srd.recursos.medicion;
import com.example.srd.recursos.piso;
import com.example.srd.recursos.registroAmbiente;
import static com.example.srd.recursos.recursos.*;

import com.example.srd.recursos.registroLuminariasInAmbiente;
import com.example.srd.recursos.registroSensorInAmbiente;
import com.example.srd.recursos.sensor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.srd.recursos.recursos.*;

public class RegistroDatosAmbiente extends AppCompatActivity {
    private    Spinner spinnerEstadoLumi,spinnerTipoLuminaria,spinnerestadoSensor,spinnerTipoSensor;

    //LISTAS DE EL REGISTRO DE  AMBIENTE
    List<medicion> lstaMediciones = new ArrayList<>();
    List<registroLuminariasInAmbiente> lstaLuminarias = new ArrayList<>();
    List<registroSensorInAmbiente> lstSensoresInAmbiente = new ArrayList<>();
    // SPINNER STRINGS DE SENSORES  Y LUMINARIAS
    List<String> lstaLuminariasString = new ArrayList<>();
    List<String> lstaSensorString = new ArrayList<>();
    // OBJETOS DE LOS SPINNER
    List<luminaria> lstLuminaria = new ArrayList<>();
    List<sensor> lstaSensores = new ArrayList<>();
    // LSTAS DE EL REGISTRO DE AMBIENTE EN STRING PARA MOSTRAR
    List<String> LstaLuminariasInAmbieneString = new ArrayList<>();
    List<String> lstaMedicionesString = new ArrayList<>();
    List<String> lstaSensoresString = new ArrayList<>();


    ListView lstviewMediciones,lstaviewLuminarias,listViewSensores;
    RequestQueue mQueQue;
    Button btnGuardaRegistro,btnCancela,btnAddmedicion,btnAddLuminarias,addSensor;
    EditText editTextJson,editTextfecha,editTextalturaMedi, nomAmbiente, nombrepiso,
            editTextResultadoMedicion,editTextDescripcionmedicion,editTextcantidaLuminaria,editTextCantidadSensores;
    String jsonRegistro=null;
    int nroMedicion = 0;
    boolean estadoLuminaria = false,estadoSensor= false;
    int idLuminaria  = 0 ,idsensor = 0;
    int idAmbiente= 0;
    registroAmbiente rgtroAmbiente = new registroAmbiente();
    JSONObject jsonRegistroAmbiente = new JSONObject();
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_datos_ambiente);
        if(getDefaults("idLuxometro",CONTEXTLUXOMETRO) == null){
            Toast.makeText(getApplicationContext(),"Debe selecionar un luxometro antes de registrar datos",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),luxometros.class));
        }
        final Toolbar toolbar = findViewById(R.id.toolbarSRD);
        setSupportActionBar(toolbar);
        mQueQue = Volley.newRequestQueue(getApplicationContext());
        // ASIGNO LOS BTNS A SUS RESPECTIVOS EN EL XML
            spinnerEstadoLumi = findViewById(R.id.spinnerEstatoLum);
            spinnerTipoLuminaria = findViewById(R.id.spinnerLuminarias);
            spinnerTipoSensor = findViewById(R.id.spinnerSensores);
            spinnerestadoSensor = findViewById(R.id.spinnerEstadoSensores);
            btnGuardaRegistro = findViewById(R.id.btnguardarRegistro);
            btnAddmedicion = findViewById(R.id.btnAddMedicion);
            btnAddLuminarias = findViewById(R.id.btnAddLuminaria);
            addSensor = findViewById(R.id.btnaddSensores);
            editTextfecha = findViewById(R.id.fecha);
            editTextalturaMedi = findViewById(R.id.alturaMedicion);
            editTextResultadoMedicion = findViewById(R.id.ResultMedicion);
            editTextDescripcionmedicion = findViewById(R.id.DescripcionMedicion);
            editTextcantidaLuminaria = findViewById(R.id.cantLuminaria);
            editTextCantidadSensores = findViewById(R.id.cantSensores);
            editTextJson= findViewById(R.id.editpruebaJson);
            lstviewMediciones = findViewById(R.id.lstaViewMediciones);
            lstaviewLuminarias = findViewById(R.id.listViewLuminarias);
            listViewSensores = findViewById(R.id.listViewSensores);
            btnCancela = findViewById(R.id.cancelar);
        idAmbiente = (int) getIntent().getIntExtra("idAmbiente",0);
        String nombreAmbiente = getIntent().getStringExtra("nombreAmbiente");
        String nombrePiso = getIntent().getStringExtra("nombrePiso");
        nomAmbiente =findViewById(R.id.nombreAmbiente);
        nombrepiso =findViewById(R.id.nombrePiso);
        nomAmbiente.setText(nombreAmbiente);
        nombrepiso.setText(nombrePiso);
        ArrayAdapter<CharSequence> adapterEstadosSensor = ArrayAdapter.createFromResource(getApplicationContext(),R.array.estados,R.layout.spinner_item_estado);
        spinnerestadoSensor.setAdapter(adapterEstadosSensor);
        ArrayAdapter<CharSequence> adapterestadosLum =  ArrayAdapter.createFromResource(getApplicationContext(),R.array.estados,R.layout.spinner_item_estado);
        spinnerEstadoLumi.setAdapter(adapterestadosLum);
        spinnerEstadoLumi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    estadoLuminaria = true;
                }else if(position == 1){
                    estadoLuminaria = false;
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
        spinnerTipoSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idsensor = lstaSensores.get(position).getIdsensor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerestadoSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    estadoSensor = true;
                }else if(position == 1){
                    estadoSensor = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listarLuminarias();
        listarSensor();

        btnGuardaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextfecha.getText().toString().equals("") || editTextalturaMedi.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"ALGUNO CAMPOS ESTAN VACIOS",Toast.LENGTH_SHORT).show();
                }else {
                    Gson gson = new Gson();
                    rgtroAmbiente.setIdUus(Integer.parseInt( getDefaults("idUser",CONTEXTLOGIN)));
                    rgtroAmbiente.setIdAmbiente(idAmbiente);
                    rgtroAmbiente.setIdLuxometro(Integer.parseInt(getDefaults("idLuxometro",CONTEXTLUXOMETRO)));
                    rgtroAmbiente.setFecha(editTextfecha.getText().toString());
                    rgtroAmbiente.setAlturaMedicion( Double.parseDouble(String.valueOf(editTextalturaMedi.getText())));
                    rgtroAmbiente.setLstaMediciones(lstaMediciones);
                    rgtroAmbiente.setLstregluminariasInAmbiente(lstaLuminarias);
                    rgtroAmbiente.setLstregistroSensorInAmbiente(lstSensoresInAmbiente);
                    jsonRegistro = gson.toJson(rgtroAmbiente);

                    if(rgtroAmbiente.getLstregluminariasInAmbiente().size() > 0 || rgtroAmbiente.getLstaMediciones().size() > 0 || rgtroAmbiente.getLstregistroSensorInAmbiente().size() > 0 ){

                        try {
                            RegistraDatosAmbiente();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"ERROR JSON"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"falta agregar campos",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        btnCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),listaPisosActivity.class));
            }
        });

        btnAddmedicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nro =(nroMedicion+=1);
                double  result = 0 ;
                if(!editTextResultadoMedicion.getText().toString().equals("")){
                    result = Double.parseDouble( editTextResultadoMedicion.getText().toString());
                    String valdescrip = editTextDescripcionmedicion.getText().toString();
                    lstaMedicionesString.add("Nro :"+nro+"- "+result+" | "+valdescrip);
                    String descrip = (valdescrip.equals("")) ? " ":valdescrip;
                    lstviewMediciones.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item_estado,lstaMedicionesString));
                    lstaMediciones.add(new medicion(nro,result,descrip));
                    editTextResultadoMedicion.setText("");

                }else{
                    Toast.makeText(getApplicationContext(),"debe ingresar un resultado de medicion",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnAddLuminarias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantLum = 0;
                if(!editTextcantidaLuminaria.getText().toString().equals("")){
                    cantLum = Integer.parseInt( editTextcantidaLuminaria.getText().toString());
                    lstaLuminarias.add(new registroLuminariasInAmbiente(idLuminaria,cantLum,estadoLuminaria));
                    LstaLuminariasInAmbieneString.add("Nomb. Lum : "+spinnerTipoLuminaria.getSelectedItem().toString()+" Cant. "+ cantLum+" Estado : "+Estado(estadoLuminaria));
                    lstaviewLuminarias.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item_estado,LstaLuminariasInAmbieneString));

                } else{
                    Toast.makeText(getApplicationContext(),"falta agregar datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        addSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantSensore =  0;
                if(!editTextCantidadSensores.getText().toString().equals("")){
                    cantSensore = Integer.parseInt( editTextCantidadSensores.getText().toString());
                    lstSensoresInAmbiente.add(new registroSensorInAmbiente(idsensor,cantSensore,estadoSensor));
                    lstaSensoresString.add("Nom. Sensor "+spinnerTipoSensor.getSelectedItem().toString()+" Cant : "+cantSensore+" Estado : "+Estado(estadoSensor));
                    listViewSensores.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item_estado,lstaSensoresString));

                }else{
                    Toast.makeText(getApplicationContext(),"Falta agregar datos",Toast.LENGTH_SHORT).show();
                }
            }
        });


        editTextfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia  = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int anio = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(RegistroDatosAmbiente.this , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextfecha.setText((month +1)+"/"+dayOfMonth+"/"+year);
                    }
                }, dia, mes,anio);
                datePickerDialog.show();
            }
        });
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
                spinnerTipoSensor.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaSensorString));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        mQueQue.add(request);
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
                        lstaLuminariasString.add(jsonObjectLuminaria.getString("abrebiasion"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerTipoLuminaria.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_estado,lstaLuminariasString));
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        mQueQue.add(request);
    }
    private void RegistraDatosAmbiente() throws JSONException, ParseException {
        String URL = getIPSERVICIO()+getRecursoRegistrodatosambiente();
        JSONObject jsonRegistrosend = new JSONObject(jsonRegistro);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonRegistrosend,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Toast.makeText(getApplicationContext(), "Registrado correctamente con el "+jsonObject.getString("respuesta"),Toast.LENGTH_SHORT).show();
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

        mQueQue.add(request);
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

    private String Estado(boolean estado){
        if(estado == true){
            return "Operativo";
        }else {
            return "Inoperativo";
        }
    }

}
