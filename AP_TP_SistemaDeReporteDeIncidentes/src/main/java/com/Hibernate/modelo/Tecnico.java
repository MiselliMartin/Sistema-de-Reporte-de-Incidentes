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
@Table(name = "tecnicos")
public class Tecnico {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "especialidad")
	private List<String> especialidades;
	@Column(name = "disponible", columnDefinition = "BOOLEAN")
	private boolean disponible;
	@Column(name = "mail")
	private String mail;

	public Tecnico() {
	}

	public Tecnico(String nombre, String mail) {
		this.nombre = nombre;
		this.especialidades = new ArrayList<>();
		this.disponible = true;
		this.mail = mail;

	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
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

	public void agregarEspecialidad(String especialidad) {
		this.especialidades.add(especialidad);
	}

	public void eliminarEspecialidad(String especialidad) {
		this.especialidades.remove(especialidad);
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
