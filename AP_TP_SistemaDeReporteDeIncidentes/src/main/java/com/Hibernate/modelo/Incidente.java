package com.Hibernate.modelo;

//import java.util.Date;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="incidentes")
public class Incidente {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "idCliente")
    private Integer idCliente;
    @Column(name = "idTecnicoAsignado")
    private Integer idTecnicoAsignado;
    @Column(name = "estado")
    private String estado;
    @Column(name = "idServicio")
    private Integer idServicio;
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;


    public Incidente() {
    }

    public Incidente(Integer idCliente, Integer tecnicoAsignado, Integer idServicio, String descripcion) {
        this.idCliente = idCliente;
        this.idTecnicoAsignado = tecnicoAsignado;
        this.estado = "Pendiente";
        this.idServicio = idServicio;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdTecnicoAsignado() {
        return idTecnicoAsignado;
    }

    public void setIdTecnicoAsignado(Integer idTecnicoAsignado) {
        this.idTecnicoAsignado = idTecnicoAsignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
