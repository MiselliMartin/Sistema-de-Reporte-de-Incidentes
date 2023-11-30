package com.Hibernate.controller;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.Hibernate.modelo.Servicio;

import jakarta.persistence.criteria.CriteriaQuery;

public class ServicioController {
	public Servicio CrearServicio(String nombre) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			Servicio sevicio = new Servicio(nombre);
			session.beginTransaction();
			session.persist(sevicio);
			session.getTransaction().commit();
			sessionFactory.close();
			System.out.println("Servicio " + nombre + " creado sactifatoriamente.");
			return sevicio;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al crear servicio.");
			return null;
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void eliminarServicio(Integer id) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Servicio servicio = session.get(Servicio.class, id);
			session.remove(servicio);
			session.getTransaction().commit();
			sessionFactory.close();

			System.out.println("Servicio " + servicio.getNombre() + " eliminado sactifatoriamente");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar el servicio");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public Servicio verServicio(Integer id) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Servicio servicio = session.get(Servicio.class, id);
			session.getTransaction().commit();
			sessionFactory.close();
			System.out.println("Servicio " + servicio.getNombre());
			return servicio;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar leer servicio");
			return null;
		}
	}

	public Servicio verServicioNombre(String nombre) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Servicio> criteriaQuery = criteriaBuilder.createQuery(Servicio.class);
			Root<Servicio> root = criteriaQuery.from(Servicio.class);

			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), nombre));

			Servicio servicio = session.createQuery(criteriaQuery).uniqueResult();

			System.out.println("Servicio " + servicio.getNombre());
			session.getTransaction().commit();
			return servicio;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar leer servicio");
			return null;
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void agregarEspecialidad(Integer idServicio, String especialidad) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Servicio servicio = session.get(Servicio.class, idServicio);
			if (servicio != null) {
				servicio.agregarEspecialidad(especialidad);
				session.merge(servicio);
				System.out.println("Especialidad " + especialidad + " agregada a " + servicio.getNombre());
			} else {
				System.out.println("Servicio con ID " + idServicio + " no encontrado.");
			}
			transaction.commit();
		} catch (Exception e) {
			System.out.println("Error al intentar agregar especialidad de servicios");
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	public void eliminarEspecialidad(Integer idServicio, String especialidad) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Servicio servicio = session.get(Servicio.class, idServicio);
			if (servicio != null) {
				servicio.eliminarEspecialidad(especialidad);
				session.merge(servicio);
				System.out.println("Especialidad " + especialidad + " eliminada del servicio " + servicio.getNombre());
			} else {
				System.out.println("Servicio con ID " + idServicio + " no encontrado.");
			}
			transaction.commit();
		} catch (Exception e) {
			System.out.println("Error al intentar al eliminar especialidad de servicio");

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void listadoServicios() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Servicio.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			CriteriaQuery<Servicio> cq = session.getCriteriaBuilder().createQuery(Servicio.class);
			cq.from(Servicio.class);
			List<Servicio> servicios = session.createQuery(cq).getResultList();

			System.out.println("");
			System.out.println("LISTA DE SERVICIOS \n--------------------------\n");
			for (Servicio s : servicios) {
				System.out.println(" Id: " + s.getId() + "\n");
				System.out.println(" Servicio: " + s.getNombre() + "\n");
				System.out.println(" Especialidades: " + s.getEspecialidades() + "\n------------------------------------\n");
			}
			sessionFactory.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar leer la lista de servicios");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}
}
