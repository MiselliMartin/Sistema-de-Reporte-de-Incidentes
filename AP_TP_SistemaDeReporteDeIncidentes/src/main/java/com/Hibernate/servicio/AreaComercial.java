package com.Hibernate.servicio;

import com.Hibernate.controller.ClienteController;
import com.Hibernate.modelo.Cliente;

import java.util.Scanner;

public class AreaComercial {
	private Cliente cliente;
	private ClienteController clienteController = new ClienteController();

	Scanner scanner = new Scanner(System.in);

	public AreaComercial() {
		iniciarAreaComercial();
	}

	private void iniciarAreaComercial() {
		while (true) {
			menuAreaComercial();
			String opcion = scanner.nextLine();
			if (opcion.equals("1")) {
				identificacionCliente();
				if (this.cliente == null) {
					return;
				}
			} else if (opcion.equals("2")) {
				altaCliente();
			} else if (opcion.equals("0")) {
				return;
			} else {
				continue;
			}
			while (true && this.cliente != null) {
				menuServicio();
				String segundaOpcion = scanner.nextLine();
				switch (segundaOpcion) {
				case "1":
					agregarServicio();
					break;
				case "2":
					eliminarServicio();
					break;
				case "3":
					bajaCliente(this.cliente.getId());
					return;
				case "4":
					verServicios();
					break;
				case "5":
					System.out.println(
							"Recuerde que este es un listado confidencial y los datos solo lo pueden saber nuestros clientes.");
					listadoClientes();
					break;
				case "0":
					return;
				default:
					continue;
				}
			}
		}
	}

	private void menuServicio() {
		System.out.println("**********************************************************************");
		System.out.println("Para agregar un servicio presione 1:");
		System.out.println("Para eliminar un servicio presione 2:");
		System.out.println("Para darse de baja presione 3:");
		System.out.println("Para ver sus servicios presione 4:");
		System.out.println("Para ver un listado de los clientes presione 5:");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private void identificacionCliente() {
		opcionesCliente();
		String idONombre = scanner.nextLine();
		if (idONombre.equals("1")) {
			buscarPorID();
		} else if (idONombre.equals("2")) {
			buscarPorRazonSocialYCuit();
		}
	}

	private void buscarPorRazonSocialYCuit() {
		System.out.print("Por favor ingrese su razon social: ");
		String razonSocial = scanner.nextLine();
		System.out.print("Por favor ingrese su cuit: ");
		String cuit = scanner.nextLine();
		razonSocialyCuit(razonSocial, cuit);
	}

	private void buscarPorID() {
		System.out.println("Ingrese id: ");
		String idStr = scanner.nextLine();
		Integer id = Integer.valueOf(idStr);
		verCliente(id);
	}

	private void opcionesCliente() {
		System.out.println("**********************************************************************");
		System.out.println("Para identificarse mediante id presione 1");
		System.out.println("Para identificarse mediante razón social y cuit presione 2");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private void menuAreaComercial() {
		System.out.println("**********************************************************************");
		System.out.println("Bienvenide al área comercial");
		System.out.println("Si usted ya es cliente presione 1:");
		System.out.println("Para darse de alta presione 2:");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private Cliente altaCliente() {
		System.out.print("Ingrese por favor su razon social: ");
		String razonSocial = scanner.nextLine();
		System.out.print("Ingrese por favor su cuit: ");
		String cuit = scanner.nextLine();
		this.cliente = clienteController.crearCliente(razonSocial, cuit);
		return cliente;
	}

	private void bajaCliente(Integer id) {
		Cliente cliente = clienteController.verCliente(id);
		System.out.print("Como medida de seguridad ingrese su cuit tal como figura en su factura: ");
		String cuitIngresado = scanner.nextLine();
		if (cliente.getCuit().equals(cuitIngresado)) {
			clienteController.eliminarCliente(id);
		} else {
			System.out.println("Error de seguridad. Cliente no eliminado");
		}
	}

	private Cliente verCliente(Integer id) {
		Cliente cliente = clienteController.verCliente(id);
		this.cliente = cliente;
		if (this.cliente == null) {
			System.out.println("Cliente no encontrado. Corrobore sus datos.");
		}
		return cliente;
	}

	public Cliente razonSocialyCuit() {
		System.out.print("Ingrese por favor su razon social: ");
		String razonSocial = scanner.nextLine();
		System.out.print("Ingrese por favor su cuit: ");
		String cuit = scanner.nextLine();
		Cliente cliente = clienteController.encontrarClientePorRazonSocialYCUIT(razonSocial, cuit);
		this.cliente = cliente;
		if (cliente == null) {
			System.out.println("Cliente no encontrado. Corrobore sus datos.");
			return null;
		}
		return cliente;
	}

	private Cliente razonSocialyCuit(String razonSocial, String cuit) {
		Cliente cliente = clienteController.encontrarClientePorRazonSocialYCUIT(razonSocial, cuit);
		this.cliente = cliente;
		if (cliente == null) {
			System.out.println("Cliente no encontrado. Corrobore sus datos.");
			return null;
		}
		return cliente;
	}

	private void agregarServicio() {
		System.out.print("Ingrese el servicio a agregar: ");
		String servicio = scanner.nextLine();
		clienteController.agregarServicio(this.cliente.getId(), servicio);
	}

	private void eliminarServicio() {
		System.out.print("Ingrese el servicio a eliminar: ");
		String servicio = scanner.nextLine();
		clienteController.eliminarServicio(this.cliente.getId(), servicio);
	}

	private void verServicios() {
		if (this.cliente != null) {
			clienteController.listadoServicios(this.cliente.getId());
		} else {
			System.out.println("cliente no exitente\n Verifique los datos ingresados");
		}
	}

	private void listadoClientes() {
		clienteController.listadoClientes();
	}
}