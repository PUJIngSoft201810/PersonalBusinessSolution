package com.nise.jbookproject.Modulos;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String idU;
    private String idref;
    private String email;
    private String nombre;
    private String apellido;
    private String identificacion;

    public Usuario(String id, String email, String nombre, String apellido, String identificacion) {
        this.idU = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }
    public Usuario() {

    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String getIdref() {
        return idref;
    }

    public void setIdref(String idref) {
        this.idref = idref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + idU + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", identificacion='" + identificacion + '\'' +
                '}';
    }
}
