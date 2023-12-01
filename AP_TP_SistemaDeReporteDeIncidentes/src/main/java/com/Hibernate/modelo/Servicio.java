package com.Hibernate.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios")
public class Servicio {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "especialidad")
	private List<String> especialidades;

	public Servicio() {

	}

	public Servicio(String nombre) {
		this.nombre = nombre;
		this.especialidades = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<String> especialidades) {
		this.especialidades = especialidades;
	}

	public void agregarEspecialidad(String especialidad) {
		if (this.especialidades == null) {
			this.especialidades = new ArrayList<>();
		}

		this.especialidades.add(especialidad);
	}

	public void eliminarEspecialidad(String especialidad) {
		String espe = especialidad.toUpperCase();
		this.especialidades.remove(espe);
	}
}
