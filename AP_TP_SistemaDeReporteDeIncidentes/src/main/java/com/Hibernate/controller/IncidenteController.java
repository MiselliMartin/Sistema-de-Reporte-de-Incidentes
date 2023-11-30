package com.Hibernate.controller;

import java.util.List;

import com.Hibernate.modelo.Tecnico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.Hibernate.modelo.Incidente;

import jakarta.persistence.criteria.CriteriaQuery;

public class IncidenteController {
	public Incidente crearIncidente(Integer idCliente, Integer tecnicoAsignado,Integer idServicio, String descripcion) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Incidente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
			Incidente incidente = new Incidente(idCliente, tecnicoAsignado, idServicio, descripcion);
					session.beginTransaction();
					session.persist(incidente);
					session.getTransaction().commit();
					sessionFactory.close();
					System.out.println("Incidente creado sactifatoriamente.");
					return incidente;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al crear incidente.");
			return null;
		}finally {
			session.close();
			sessionFactory.close();

		}
	}
	
	public void eliminarIncidente(Integer id) {
		SessionFactory sessionFactory = new 
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Incidente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Incidente incidente = session.get(Incidente.class, id);
					session.remove(incidente);
					session.getTransaction().commit();
					sessionFactory.close();

			System.out.println("Incidente eliminado sactifatoriamente ");
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar el incidente");
		}finally {
			session.close();
			sessionFactory.close();
		}
	}
	public Incidente verIncidente(Integer id) {
		SessionFactory sessionFactory = new
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Incidente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Incidente incidente= session.get(Incidente.class, id);
					session.getTransaction().commit();
					sessionFactory.close();
					System.out.println("\nIncidente id: "+id+ "\n id cliente: " + incidente.getIdCliente() + "\n fecha: " + incidente.getFecha());
					return incidente;
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error. Incidente " + id + " no encontrado.");
			return null;
		}finally {
			session.close();
			sessionFactory.close();
		}
	}
	public String estadoIncidente(Integer id) {
		SessionFactory sessionFactory = new 
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Incidente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Incidente incidente= session.get(Incidente.class, id);
					session.getTransaction().commit();
					sessionFactory.close();
					System.out.println("Incidente con id: " + id + " estado: " + incidente.getEstado());
					return incidente.getEstado();
							
		}catch (Exception e) {
			e.printStackTrace();
			return "Error al intentar ver el estado del incidente";
		}finally {
			session.close();
			sessionFactory.close();
		}
	}
	public void ListadoIncidentes() {
		SessionFactory sessionFactory = new 
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Incidente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					CriteriaQuery <Incidente> cq= session.getCriteriaBuilder().createQuery(Incidente.class);
					cq.from(Incidente.class);
					List<Incidente> incidentes = session.createQuery(cq).getResultList();
					
					System.out.println("");
					System.out.println("LISTA DE INCIDENTES \n--------------------------\n");
					for(Incidente i :incidentes) {
						System.out.println("ID: " +i.getId() + "\n");
						System.out.println("ID Tecnico a cargo: " +i.getIdTecnicoAsignado() + "\n");
						System.out.println("ID Cliente: "+i.getIdCliente() + "\n");
						System.out.println("Descripcion: "+i.getDescripcion());
						System.out.println("Fecha: " + i.getFecha() + "\n");
						System.out.println("Estado: " +i.getEstado()+"\n------------------------------------\n");
					}
					sessionFactory.close();
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar leer la lista de incidentes");
		}finally {
			session.close();
			sessionFactory.close();
		}
	
}
	
	
	public void cambiarEstadoIncidente(Integer id, String estado) {
		SessionFactory sessionFactory = new 
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Incidente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Incidente incidente = session.get(Incidente.class, id);
					incidente.setEstado(estado);
					session.persist(incidente);
					session.getTransaction().commit();
					sessionFactory.close();

			System.out.println("Estado cambiado exitosamente. ");
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al cambiar el estado del incidente");
		}
	}

	public void asignarTecnico(Integer id, Integer idTecnico) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Incidente.class)
				.addAnnotatedClass(Tecnico.class) // Asegúrate de agregar la clase Tecnico
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();

			// Cargar el incidente
			Incidente incidente = session.get(Incidente.class, id);
			if (incidente == null) {
				System.out.println("Incidente no encontrado con ID: " + id);
				return;
			}

			// Cargar el técnico
			Tecnico tecnico = session.get(Tecnico.class, idTecnico);
			if (tecnico == null) {
				System.out.println("Técnico no encontrado con ID: " + idTecnico);
				return;
			}

			// Asignar el técnico al incidente
			incidente.setIdTecnicoAsignado(tecnico.getId());

			session.getTransaction().commit();
			System.out.println("Técnico asignado exitosamente al incidente.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al asignar el técnico al incidente");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}
	
}
 