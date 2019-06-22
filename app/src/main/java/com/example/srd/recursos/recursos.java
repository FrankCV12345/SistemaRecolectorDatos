package com.example.srd.recursos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class recursos {
    private static final String IPSERVICIO = "http://192.168.1.36:8080/WS_sistemaRecolectorDatosV2/api";
    private static final String RECURSO_PISO = "/piso";
    private static final String RECURSO_AMBIENTE= "/ambiente";
    private static final String RECURSO_USER= "/user";
    public static   Context CONTEXTLOGIN = null;


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
}
