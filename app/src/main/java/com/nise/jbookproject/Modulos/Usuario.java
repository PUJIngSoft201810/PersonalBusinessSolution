package com.nise.jbookproject.Modulos;

import java.io.Serializable;

public class Usuario implements Serializable{
    String id;
    String idFireUsr;
    private String email;
    private String nombre;
    private String apellido;
    private String identificacion;

    public String getIdFireUsr() {
        return idFireUsr;
    }

    public void setIdFireUsr(String idFireUsr) {
        this.idFireUsr = idFireUsr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario(String id, String email, String nombre, String apellido, String identificacion) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", identificacion='" + identificacion + '\'' +
                '}';
    }
}
