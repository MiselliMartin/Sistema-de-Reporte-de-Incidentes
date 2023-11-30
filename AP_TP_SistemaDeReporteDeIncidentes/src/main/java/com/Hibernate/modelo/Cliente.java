package com.Hibernate.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="clientes")
public class Cliente {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "cuit")
	private String cuit;
	@Column(name = "razonSocial")
    private String razonSocial;
	@Column(name = "servicios")
	private List<String> servicios;
	
    public Cliente(){

    }
    public Cliente(String razonSocial, String cuit) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
		this.servicios = new ArrayList<>();
    }

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    public String getCuit() {
        return cuit;
    }

    public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public void setCuit(String cuit) {
        this.cuit = cuit;
    }

	public void agregarServicio(String servicio) {
		this.servicios.add(servicio);
	}

	public void eliminarServicio(String servicio) {
		this.servicios.remove(servicio);
	}

	public List<String> getServicios() {
		return this.servicios;
	}
	
}
