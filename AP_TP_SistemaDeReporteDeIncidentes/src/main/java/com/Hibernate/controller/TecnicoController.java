package com.Hibernate.controller;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.Hibernate.modelo.Tecnico;

import jakarta.persistence.criteria.CriteriaQuery;

public class TecnicoController {
	
	public Tecnico crearTecnico(String nombre, String mail) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
			Tecnico tecnico = new Tecnico(nombre, mail);
					session.beginTransaction();
					session.persist(tecnico);
					session.getTransaction().commit();
					sessionFactory.close();
					System.out.println("Técnico creado sactifatoriamente ");
					return tecnico;
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al crear el técnico");
			return null;
		}finally {
			session.close();
			sessionFactory.close();
		}
	}
	
	public void eliminarTecnico(Integer id) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Tecnico tecnico = session.get(Tecnico.class, id);
					session.remove(tecnico);
					session.getTransaction().commit();
					sessionFactory.close();

			System.out.println("Tecnico eliminado sactifatoriamente ");
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar el tecnico");
		}finally {
			session.close();
			sessionFactory.close();
		}
	}
	public Tecnico verTecnico(Integer id) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Tecnico tecnico= session.get(Tecnico.class, id);
					session.getTransaction().commit();
					sessionFactory.close();
					System.out.println("\nTécnico: " + tecnico.getNombre() + "\n id: " + tecnico.getId() + "\n mail: " + tecnico.getMail() + "\n especialidades " + tecnico.getEspecialidades().toString());
					return tecnico;
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error. Técnico con id "+ id +" no encontrado.");
			return null;
		}finally {
			session.close();
			sessionFactory.close();
		}
	}

	public Tecnico verTecnicoNombreyMail(String nombre, String mail) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Tecnico> criteriaQuery = criteriaBuilder.createQuery(Tecnico.class);
			Root<Tecnico> root = criteriaQuery.from(Tecnico.class);

			criteriaQuery.where(
					criteriaBuilder.equal(root.get("nombre"), nombre),
					criteriaBuilder.equal(root.get("mail"), mail)
			);

			Tecnico tecnico = session.createQuery(criteriaQuery).uniqueResult();

			if (tecnico != null) {
				System.out.println("Técnico encontrado:");
				System.out.println("Nombre: " + tecnico.getNombre());
				System.out.println("ID: " + tecnico.getId());
				System.out.println("Email: " + tecnico.getMail());
				System.out.println("Especialidades " + tecnico.getEspecialidades().toString());
			
			} else {
				System.out.println("Error. Técnico con nombre " + nombre + " y mail " + mail + " no encontrado.");
			}

			session.getTransaction().commit();
			return tecnico;
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			System.out.println("Error al intentar encontrar el Técnico por nombre y email.");
			return null;
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void agregarEspecialidad(Integer idTecnico, String especialidad) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Tecnico tecnico = session.get(Tecnico.class, idTecnico);
			if (tecnico != null) {
				tecnico.agregarEspecialidad(especialidad);
				session.merge(tecnico);
				System.out.println("Especialidad " + especialidad + " agregada al técnico " + tecnico.getNombre());
			} else {
				System.out.println("Técnico con ID " + idTecnico + " no encontrado.");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void eliminarEspecialidad(Integer idTecnico, String especialidad) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Tecnico tecnico = session.get(Tecnico.class, idTecnico);
			if (tecnico != null) {
				tecnico.eliminarEspecialidad(especialidad);
				session.merge(tecnico);
				System.out.println("Especialidad " + especialidad + " eliminada del técnico " + tecnico.getNombre());
			} else {
				System.out.println("Técnico con ID " + idTecnico + " no encontrado.");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}
	public boolean disponibilidadTecnico(Integer id) {
		SessionFactory sessionFactory = new 
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Tecnico.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					Tecnico tecnico= session.get(Tecnico.class, id);
					session.getTransaction().commit();
					sessionFactory.close();
					System.out.println("Tecnico: "+ tecnico.getNombre() + " id: "+ tecnico.getId() +". Disponabilidad: "+ (tecnico.isDisponible() ? "Libre" : "Ocupado"));
					return tecnico.isDisponible();
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar ver las disponibilidad del técnico");
			return false;
		}finally {
			session.close();
			sessionFactory.close();
		}
	}
	public void listadoTecnicos() {
		SessionFactory sessionFactory = new 
				Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Tecnico.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
					session.beginTransaction();
					CriteriaQuery <Tecnico> cq= session.getCriteriaBuilder().createQuery(Tecnico.class);
					cq.from(Tecnico.class);
					List<Tecnico> tecnicos = session.createQuery(cq).getResultList();
					
					System.out.println("LISTA DE TECNICOS \n--------------------------\n");
					for(Tecnico t :tecnicos) {
						System.out.println(" Nombre: "+ t.getNombre() + "\n");
						System.out.println(" ID: " +t.getId()+ "\n" );
						System.out.println(" Especialidad: "+ (t.getEspecialidades().isEmpty() ? "Sin especialidades" : t.getEspecialidades().toString())  + "\n");
						System.out.println(" Mail: " +t.getMail()+"\n");
						System.out.println((t.isDisponible()? " Está disponible":" No está disponible")+"\n------------------------------------\n");
					}
							
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar leer la lista de Tecnicos");
		}finally {
			session.close();
			sessionFactory.close();
		}
	
}

	public void listadoEspecialidades(Integer id) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();

			Tecnico tecnico = session.get(Tecnico.class, id);

			if (tecnico != null) {
				List<String> especialidades = tecnico.getEspecialidades();

				if (!especialidades.isEmpty()) {
					System.out.println("ESPECIALIDADES DEL TÉCNICO \n--------------------------\n");
					System.out.println(" Técnico: " + tecnico.getNombre());
					System.out.println((tecnico.isDisponible()? " Está disponible":" No está disponible"));
					for (String especialidad : especialidades) {
						System.out.println(" Especialidad : " + especialidad);
					}
				} else {
					System.out.println("El técnico no tiene especialidades asignadas.");
				}
			} else {
				System.out.println(" Técnico con ID " + id + " no encontrado.\n Verifique los datos ingresados");
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			System.out.println("Error al intentar obtener la lista de especialidades del técnico.");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void tecnicosDisponibles() {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Tecnico> criteriaQuery = criteriaBuilder.createQuery(Tecnico.class);
			Root<Tecnico> root = criteriaQuery.from(Tecnico.class);

			criteriaQuery.where(criteriaBuilder.isTrue(root.get("disponible")));

			List<Tecnico> tecnicos = session.createQuery(criteriaQuery).getResultList();

			System.out.println("TÉCNICOS DISPONIBLES \n--------------------------\n");
			for (Tecnico tecnico : tecnicos) {
				System.out.println(" Nombre: " + tecnico.getNombre() + " \n");
				System.out.println(" ID: " + tecnico.getId() + " \n");
				System.out.println(" Especialidad: " + (tecnico.getEspecialidades().isEmpty() ? "Sin especialidades" : tecnico.getEspecialidades().toString()) + " \n");
				System.out.println( "Mail: " +tecnico.getMail());
				System.out.println((tecnico.isDisponible()? " Está disponible":" No está disponible")+"\n------------------------------------\n");
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			System.out.println(" Error al intentar obtener la lista de Técnicos disponibles.");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}
	
	
	public void notificarTecnico(Integer id) {
		System.out.println("Técnico notificado");
	}

	public void cambiarDisponible(Integer id) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tecnico.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Tecnico tecnico = session.get(Tecnico.class, id);
			if (tecnico != null) {
				tecnico.setDisponible(!tecnico.isDisponible());
				session.merge(tecnico);
				System.out.println("Estado de disponibilidad cambiado para el técnico " + tecnico.getNombre());
				System.out.println("Nuevo estado: " + (tecnico.isDisponible() ? "Disponible" : "No disponible"));
			} else {
				System.out.println(" Técnico con ID " + id + " no encontrado.\n Verifique los datos ingresados");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

}
