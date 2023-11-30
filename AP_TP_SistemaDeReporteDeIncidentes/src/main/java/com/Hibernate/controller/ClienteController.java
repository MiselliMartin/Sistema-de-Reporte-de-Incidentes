package com.Hibernate.controller;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.Hibernate.modelo.Cliente;
import jakarta.persistence.criteria.CriteriaQuery;

public class ClienteController {

	public Cliente crearCliente(String razonSocial, String CUIT) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			Cliente cliente = new Cliente(razonSocial, CUIT);
			session.beginTransaction();
			session.persist(cliente);
			session.getTransaction().commit();
			sessionFactory.close();

			System.out.println("Cliente creado sactifatoriamente ");
			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al crear cliente");
			return null;
		} finally {
			session.close();
			sessionFactory.close();

		}
	}

	public void eliminarCliente(Integer id) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Cliente cliente = session.get(Cliente.class, id);
			session.remove(cliente);
			session.getTransaction().commit();
			sessionFactory.close();

			System.out.println("Cliente eliminado sactifatoriamente ");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar el cliente");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public Cliente verCliente(Integer id) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Cliente cliente = session.get(Cliente.class, id);

			if (cliente == null) {
				// Cliente no encontrado
				return null;
			}

			session.getTransaction().commit();
			System.out.println(
					"\nCliente: " + cliente.getRazonSocial() + "\n id: " + cliente.getId() + "\n cuit: " + cliente.getCuit());
			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar ver cliente");
		} finally {
			session.close();
			sessionFactory.close();
		}
		return null;
	}

	public Cliente encontrarClientePorRazonSocialYCUIT(String razonSocial, String cuit) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();

			// Consulta para encontrar un cliente por razonSocial y CUIT
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
			Root<Cliente> root = query.from(Cliente.class);
			query.select(root).where(builder.equal(root.get("razonSocial"), razonSocial),
					builder.equal(root.get("cuit"), cuit));

			Cliente cliente = session.createQuery(query).uniqueResult();

			session.getTransaction().commit();

			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar encontrar cliente por razonSocial y CUIT.");
		} finally {
			session.close();
			sessionFactory.close();
		}

		return null; // Retorna null si hay algún error
	}

	public void listadoClientes() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			CriteriaQuery<Cliente> cq = session.getCriteriaBuilder().createQuery(Cliente.class);
			cq.from(Cliente.class);
			List<Cliente> clientes = session.createQuery(cq).getResultList();

			System.out.println("");
			System.out.println("LISTA DE CLIENTES \n--------------------------\n");
			for (Cliente c : clientes) {
				System.out.println("RAZON SOCIAL: " + c.getRazonSocial() + "\n");
				System.out.println("CUIT: " + c.getCuit() + "\n");
				System.out.println("ID: " + c.getId() + "\n------------------------------------\n");
			}
			sessionFactory.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar leer la lista de clientes");
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	public void actualizarCliente(Integer id, String nuevaRazonSocial, String nuevoCUIT) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Cliente cliente = session.get(Cliente.class, id);

			if (cliente == null) {
				System.out.println("Cliente no encontrado.");
				return;
			}

			// Actualiza los datos del cliente con los nuevos valores proporcionados
			cliente.setRazonSocial(nuevaRazonSocial);
			cliente.setCuit(nuevoCUIT);

			// Guarda los cambios en la base de datos
			session.getTransaction().commit();
			System.out.println("Cliente actualizado satisfactoriamente.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar actualizar cliente.");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	public void agregarServicio(Integer id, String nuevoServicio) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Cliente cliente = session.get(Cliente.class, id);
			if (cliente != null) {
				cliente.agregarServicio(nuevoServicio);
				session.merge(cliente);
				System.out.println("Servicio: " + nuevoServicio + " agregado");
			} else {
				System.out.println("Servicio con ID " + id + " no encontrado.");
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

	public void eliminarServicio(Integer id, String servicioAEliminar) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class).buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Cliente cliente = session.get(Cliente.class, id);
			if (cliente != null) {
				cliente.eliminarServicio(servicioAEliminar);
				session.merge(cliente);
				System.out.println("Servicio: " + servicioAEliminar + " eliminado");
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
	
	public void listadoServicios(Integer id) {
		SessionFactory sessionFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class)
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();

			Cliente cliente = session.get(Cliente.class, id);

			if (cliente != null) {
				List<String> servicios = cliente.getServicios();

				if (!servicios.isEmpty()) {
					System.out.println("SERVICIO DE CLIENTE ID: "+cliente.getId()+" \n--------------------------\n");
					for (String servicio : servicios) {
						System.out.println("Servicio : " + servicio);
					}
				} else {
					System.out.println("El cliente no tiene servicios.");
				}
			} else {
				System.out.println("Cliente con ID " + id + " no encontrado.");
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			System.out.println("Error al intentar obtener la lista de servicios del cliente.");
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

}
