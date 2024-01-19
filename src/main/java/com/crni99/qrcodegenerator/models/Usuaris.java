package com.crni99.qrcodegenerator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Usuaris {
    @Id
    @Column(name = "dni")
    private String dni;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "cognom")
    private String cognom;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "numero_telefono")
    private String numeroTelefono;
    @Basic
    @Column(name = "password")
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarisByUserDni")
    private Collection<Tickets> ticketsByDni;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuaris usuaris = (Usuaris) o;

        if (dni != null ? !dni.equals(usuaris.dni) : usuaris.dni != null) return false;
        if (nom != null ? !nom.equals(usuaris.nom) : usuaris.nom != null) return false;
        if (cognom != null ? !cognom.equals(usuaris.cognom) : usuaris.cognom != null) return false;
        if (email != null ? !email.equals(usuaris.email) : usuaris.email != null) return false;
        if (numeroTelefono != null ? !numeroTelefono.equals(usuaris.numeroTelefono) : usuaris.numeroTelefono != null)
            return false;
        if (password != null ? !password.equals(usuaris.password) : usuaris.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dni != null ? dni.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (cognom != null ? cognom.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (numeroTelefono != null ? numeroTelefono.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public Collection<Tickets> getTicketsByDni() {
        return ticketsByDni;
    }

    public void setTicketsByDni(Collection<Tickets> ticketsByDni) {
        this.ticketsByDni = ticketsByDni;
    }
}
