package com.impulso.impulsobackoffice.departamento.domain.model;

import java.util.List;

public class Departamento {
    private int id;
    private String nombre;
    private List<Municipio> municipios;
    
    public Departamento(int id, String nombre, List<Municipio> municipios) {
        this.id = id;
        this.nombre = nombre;
        this.municipios = municipios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    
}
