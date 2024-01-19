package com.crni99.qrcodegenerator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Tickets {
    @Id
    @Column(name = "ticket_id")
    private String ticketId;
    @Basic
    @Column(name = "user_dni")
    private String userDni;
    @Basic
    @Column(name = "partit_id")
    private Integer partitId;
    @Basic
    @Column(name = "data_compra")
    private Timestamp dataCompra;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_dni", referencedColumnName = "dni", nullable = false, insertable = false, updatable = false)
    private Usuaris usuarisByUserDni;
    @ManyToOne
    @JoinColumn(name = "partit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Partits partitsByPartitId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserDni() {
        return userDni;
    }

    public void setUserDni(String userDni) {
        this.userDni = userDni;
    }

    public Integer getPartitId() {
        return partitId;
    }

    public void setPartitId(Integer partitId) {
        this.partitId = partitId;
    }

    public Timestamp getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Timestamp dataCompra) {
        this.dataCompra = dataCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tickets tickets = (Tickets) o;

        if (ticketId != null ? !ticketId.equals(tickets.ticketId) : tickets.ticketId != null) return false;
        if (userDni != null ? !userDni.equals(tickets.userDni) : tickets.userDni != null) return false;
        if (partitId != null ? !partitId.equals(tickets.partitId) : tickets.partitId != null) return false;
        if (dataCompra != null ? !dataCompra.equals(tickets.dataCompra) : tickets.dataCompra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketId != null ? ticketId.hashCode() : 0;
        result = 31 * result + (userDni != null ? userDni.hashCode() : 0);
        result = 31 * result + (partitId != null ? partitId.hashCode() : 0);
        result = 31 * result + (dataCompra != null ? dataCompra.hashCode() : 0);
        return result;
    }

    public Usuaris getUsuarisByUserDni() {
        return usuarisByUserDni;
    }

    public void setUsuarisByUserDni(Usuaris usuarisByUserDni) {
        this.usuarisByUserDni = usuarisByUserDni;
    }

    public Partits getPartitsByPartitId() {
        return partitsByPartitId;
    }

    public void setPartitsByPartitId(Partits partitsByPartitId) {
        this.partitsByPartitId = partitsByPartitId;
    }
}
