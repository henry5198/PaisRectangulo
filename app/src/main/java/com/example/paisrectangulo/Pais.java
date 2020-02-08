package com.example.paisrectangulo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Pais {
    private String titulo;
    private String subtitulo;
    private String url;
    private String west="", east="", north="", south="", latlong="";

    private Double lat=0.0, longi=0.0;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public Pais() throws JSONException {
        //titulo = a.getString("Name").toString();
        subtitulo = "";
        //url = "http://www.geognos.com/api/en/countries/flag/"+a.getString("iso2").toString();




    }

    public static ArrayList<Pais> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Pais> noticias = new ArrayList<>();

        /*JSONObject jresults = datos.getJSONObject("Results");
        Iterator<?> iterator = jresults.keys();
        while (iterator.hasNext()){

            noticias.add(new Pais(datos.getJSONObject(i)));
        }*/
        return noticias;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
