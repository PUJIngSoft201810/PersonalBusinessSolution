package com.nise.jbookproject.Modulos;

import java.util.Date;

public class Reserva {
    String idReserva;
    String idUsuario;
    String idRecurso;
    String recurso;
    Boolean activa;
    Date fecha_inicio;
    Date fecha_fin;

    public Reserva() {
    }

    public Reserva(String idReserva, String idUsuario, String idRecurso, String recurso, boolean activa,Date fecha_inicio, Date fecha_fin) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idRecurso = idRecurso;
        this.recurso = recurso;
        this.activa = activa;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", idUsuario=" + idUsuario +
                ", idRecurso=" + idRecurso +
                ", recurso='" + recurso + '\'' +
                ", activa=" + activa +
                '}';
    }
}

