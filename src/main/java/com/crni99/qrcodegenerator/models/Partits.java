package com.crni99.qrcodegenerator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

@Entity
public class Partits {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "data")
    private Date data;
    @Basic
    @Column(name = "hora")
    private Time hora;
    @Basic
    @Column(name = "lloc")
    private String lloc;
    @Basic
    @Column(name = "aforo_maxim")
    private Integer aforoMaxim;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @JsonIgnore
    @OneToMany(mappedBy = "partitsByPartitId")
    private Collection<Tickets> ticketsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getLloc() {
        return lloc;
    }

    public void setLloc(String lloc) {
        this.lloc = lloc;
    }

    public Integer getAforoMaxim() {
        return aforoMaxim;
    }

    public void setAforoMaxim(Integer aforoMaxim) {
        this.aforoMaxim = aforoMaxim;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partits partits = (Partits) o;

        if (id != partits.id) return false;
        if (data != null ? !data.equals(partits.data) : partits.data != null) return false;
        if (hora != null ? !hora.equals(partits.hora) : partits.hora != null) return false;
        if (lloc != null ? !lloc.equals(partits.lloc) : partits.lloc != null) return false;
        if (aforoMaxim != null ? !aforoMaxim.equals(partits.aforoMaxim) : partits.aforoMaxim != null) return false;
        if (nombre != null ? !nombre.equals(partits.nombre) : partits.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (hora != null ? hora.hashCode() : 0);
        result = 31 * result + (lloc != null ? lloc.hashCode() : 0);
        result = 31 * result + (aforoMaxim != null ? aforoMaxim.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public Collection<Tickets> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Tickets> ticketsById) {
        this.ticketsById = ticketsById;
    }
}
