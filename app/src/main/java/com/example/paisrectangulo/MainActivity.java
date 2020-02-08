package com.example.paisrectangulo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paisrectangulo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;


public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/all.json", datos,
                MainActivity.this, MainActivity.this);
        ws.execute("");

        ListView lstOpciones = (ListView)findViewById(R.id.lvnoticia);
        lstOpciones.setOnItemClickListener(this);

        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void processFinish(String result) throws JSONException {

        ArrayList<Pais> paises_lista= new ArrayList<Pais>();
        JSONObject jsonObj1 = new JSONObject(result);
        JSONObject jsonObj2 = jsonObj1.getJSONObject("Results");
        Iterator<?> iterator = jsonObj2.keys();
        while (iterator.hasNext()){
            String key =(String)iterator.next();
            JSONObject jsonPais = jsonObj2.getJSONObject(key);
            Pais pais = new Pais();

            pais.setTitulo(jsonPais.getString("Name"));
            JSONObject jsonRectangle = jsonPais.getJSONObject("GeoRectangle");

            pais.setWest(jsonRectangle.getString("West"));
            pais.setEast(jsonRectangle.getString("East"));
            pais.setNorth(jsonRectangle.getString("North"));
            pais.setSouth(jsonRectangle.getString("South"));

            JSONArray jsonGeoCenter = jsonPais.getJSONArray("GeoPt");
            pais.setLat(jsonGeoCenter.getDouble(0));
            pais.setLongi(jsonGeoCenter.getDouble(1));

            JSONObject jsonCodigo = jsonPais.getJSONObject("CountryCodes");
            pais.setUrl("http://www.geognos.com/api/en/countries/flag/"+jsonCodigo.getString("iso2")+".png");
            paises_lista.add(pais);
        }
        AdaptadorPais adaptadornoticias = new AdaptadorPais(this, paises_lista);

        ListView lstOpciones = (ListView)findViewById(R.id.lvnoticia);
        lstOpciones.setAdapter(adaptadornoticias);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

        Intent intent= new Intent(MainActivity.this, Mapa.class);

        intent.putExtra("latlong",((Pais)adapterView.getItemAtPosition(i)).getLatlong());
        intent.putExtra("norte",((Pais)adapterView.getItemAtPosition(i)).getNorth());
        intent.putExtra("sur",((Pais)adapterView.getItemAtPosition(i)).getSouth());
        intent.putExtra("oeste",((Pais)adapterView.getItemAtPosition(i)).getWest());
        intent.putExtra("este",((Pais)adapterView.getItemAtPosition(i)).getEast());
        intent.putExtra("lat",((Pais)adapterView.getItemAtPosition(i)).getLat());
        intent.putExtra("longi",((Pais)adapterView.getItemAtPosition(i)).getLongi());
        intent.putExtra("url",((Pais)adapterView.getItemAtPosition(i)).getUrl());
        startActivity(intent);

    }

    public void getPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1) {
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }
}
