package com.example.srd.recursos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.srd.R;
import com.example.srd.lstaPisosDesarpobados;
import com.example.srd.luxometros;

public class recursos {
    private static final String IP = "192.168.1.117:8070";
    private static  final String IPRECURSOSERVICIO = "http://"+IP+"/WS_sistemaRecolectorDatosV2/";
    private static final String IPSERVICIO = "http://"+IP+"/WS_sistemaRecolectorDatosV2/api";
    private static final String RECURSO_PISO = "/piso";
    private static final String RECURSO_AMBIENTE= "/ambiente";
    private static final String RECURSO_USER= "/login";
    private static final String RECURSO_LUMINARIA= "/luminaria";
    private static final String RECURSO_SENSOR= "/sensor";
    private static final String RECURSO_REGISTRODATOSAMBIENTE= "/registroAmbiente";
    private static final String RECURSO_LISTAPISOSDESAPROBADOS= "/AmbientesNoAprobados";
    private static final String RECURSO_LUXOMETRO= "/luxometro";
    private static final String RECURSO_DETALLE= "/buscaDetalles";



    public static   Context CONTEXTLOGIN = null;
    public static  Context CONTEXTLUXOMETRO = null;


    public static String getIPSERVICIO(){
        return IPSERVICIO;
    }

    public static String getRecursoPiso(){
        return RECURSO_PISO;
    }
    public static String getRecursoAmbiente(){
        return RECURSO_AMBIENTE;
    }
    public static String getRecursoUser(){ return RECURSO_USER;}
    public static String getRecursoLuminaria(){ return RECURSO_LUMINARIA;}
    public static String getRecursoSensor(){ return RECURSO_SENSOR;}
    public static  String getIprecursoservicio(){return IPRECURSOSERVICIO;};

    public static String getRercuroRelacionPisoAmbiente(int idpiso){
        return "/piso/"+idpiso+"/ambientes";
    }
    public static String getRecursoRegistrodatosambiente(){return RECURSO_REGISTRODATOSAMBIENTE;}
    public static String getRecursoListapisosdesaprobados(){return RECURSO_LISTAPISOSDESAPROBADOS;}
    public static String getRecursoLuxometro(){return RECURSO_LUXOMETRO;}
    public static  String getRecursoDetalle(){return RECURSO_DETALLE;}

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public  static int convertEditTextToInt(EditText editText){
        String valEditText = editText.getText().toString();
        int numero  ;
            if(valEditText.equals("") || valEditText.equals(" ")){
                numero =0;
            }else{
                numero = Integer.parseInt(valEditText);
            }
        return numero;
    }
    public  static  double convertEditTextDouble( EditText editText){
        String valEditText = editText.getText().toString();
        double numero  ;
        if(valEditText.equals("") || valEditText.equals(" ")){
            numero =0;
        }else{
            numero = Double.parseDouble(valEditText);
        }
        return numero;
    }



}
