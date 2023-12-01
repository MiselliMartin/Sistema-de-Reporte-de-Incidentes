package com.Hibernate.servicio;

import com.Hibernate.controller.ClienteController;
import com.Hibernate.controller.IncidenteController;
import com.Hibernate.controller.ServicioController;
import com.Hibernate.controller.TecnicoController;
import com.Hibernate.modelo.Cliente;
import com.Hibernate.modelo.Incidente;
import com.Hibernate.servicio.AreaComercial;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class MesaAyuda {

	private Incidente incidente;
	private Cliente cliente;
	private IncidenteController incidenteController = new IncidenteController();
	private ServicioController servicioController = new ServicioController();
	private TecnicoController tecnicoController = new TecnicoController();
	private ClienteController clienteController = new ClienteController();
	private LocalDateTime fechaActual = LocalDateTime.now();
	private Scanner scanner = new Scanner(System.in);

	public MesaAyuda() {
		iniciarMesaAyuda();
	}

	public void asignarCliente(Cliente cliente) {
		this.cliente = cliente;
	};

	private void iniciarMesaAyuda() {
		// REPORTAR O PEDIR INFORMACION SOBRE INCIDENTE 1
		// ADMINISTRAR SERVICIOS 2
		// PARA DARSE DE ALTA O BAJA DE UN SERVICIO COMUNICARSE CON AREA COMERCIAL
		while (true) {
			manuMesaDeAyuda();
			String opcion = scanner.nextLine();
			switch (opcion) {
			case "1":
				manuIncidente();
				String opcion1 = scanner.nextLine();
				if (opcion1.equals("1")) {
					ingresarIncidente();
				} else if (opcion1.equals("2")) {
					reporteIncidente();
					if (this.incidente == null) {

					}
				} else if (opcion1.equals("3")) {
					cambiarEstadoIncidente();
				} else {
					System.out.println("Opción inválida.");
					manuIncidente();
					continue;
				}
				break;
			case "2":
				manuServicioYEspecialidad();
				String opcion2 = scanner.nextLine();
				if (opcion2.equals("1")) {
					crearServicio();
				} else if (opcion2.equals("2")) {
					eliminarServicio();
				} else if (opcion2.equals("3")) {
					agregarEspecialidadServicio();
				} else if (opcion2.equals("4")) {
					eliminarEspecialidadServicio();
				} else if (opcion2.equals("0")) {
					return;
				} else {
					continue;
				}
				break;
			case "3":
				@SuppressWarnings("unused")
                AreaComercial areaComercial = new AreaComercial();
				return;
			case "0":
				return;
			default:
				continue;
			}

		}
	}

	private void manuServicioYEspecialidad() {
		System.out.println("**********************************************************************");
		System.out.println("Para crear un nuevo servicio presione 1:");
		System.out.println("Para eliminar un servicio existente presione 2:");
		System.out.println("Para agregar una especialidad a un servicio presione 3:");
		System.out.println("Para eliminar una especialidad a un servicio presione 4:");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private void manuIncidente() {
		System.out.println("**********************************************************************");
		System.out.println("Para reportar un incidente presione 1:");
		System.out.println("Para pedir información sobre un incidente presione 2:");
		System.out.println("Para cambiar el estado de un incidente presione 3:");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private void manuMesaDeAyuda() {
		System.out.println("**********************************************************************");
		System.out.println("Bienvenide a la mesa de ayuda");
		System.out.println("Para reportar o pedir información sobre un incidente presione 1:");
		System.out.println("Para administrar un servicio presione 2:");
		System.out.println("Para darse de alta o baja de un servicio presione 3 y lo comunicaremos con el Área Comercial:");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	public void reporte(LocalDateTime fechaActual) {
		System.out.println("Incidente con id: " + this.incidente.getId());
		System.out.println("Descripción: " + this.incidente.getDescripcion());
		System.out.println("Tecnico asignado: " + tecnicoController.verTecnicoSinMostrar(this.incidente.getIdTecnicoAsignado()).getNombre());
		System.out.println("Cliente que reclamó: " + clienteController.verClienteSinMostrar(this.incidente.getIdCliente()).getRazonSocial());
		System.out.println("Estado: " + this.incidente.getEstado());
		System.out.println("Fecha de reclamo: " + this.incidente.getFecha());
		long diasDiferencia = ChronoUnit.DAYS.between(this.incidente.getFecha(), fechaActual);
		System.out.println("Días desde el reclamo: " + diasDiferencia);
	}

	private void reporteIncidente() {
		System.out.print("Ingrese el id de su incidente: ");
		String idStr = scanner.nextLine();
		Integer id = Integer.valueOf(idStr);
		this.incidente = verIncidente(id);
		if (this.incidente != null) {
			reporte(this.fechaActual);
		} else {
			System.out.println("ID de incidente no registrado");
		}
	}

	private void tecnicosDisponibles() {
		tecnicoController.tecnicosDisponibles();
	}

	private void listadoServicios() {
		servicioController.listadoServicios();
	}

	private void ingresarIncidente() {
		System.out.print("Por favor ingrese su razon social: ");
		String razonSocial = scanner.nextLine();
		System.out.print("Por favor ingrese su cuit: ");
		String cuit = scanner.nextLine();
		this.cliente = clienteController.encontrarClientePorRazonSocialYCUIT(razonSocial, cuit);
		tecnicosDisponibles();
		System.out.print("Elija e ingrese el id de uno de los técnicos disponibles: ");
		String idStr = scanner.nextLine();
		Integer tecnicoAsignado = Integer.valueOf(idStr);
		listadoServicios();
		System.out.print("Ingrese el id del servicio de su incidente: ");
		String idStr2 = scanner.nextLine();
		Integer idServicio = Integer.valueOf(idStr2);
		System.out.print("Describa su problema: ");
		String descripcion = scanner.nextLine();
		if (this.cliente != null && tecnicoAsignado != null && idServicio != null) {
			incidenteController.crearIncidente(this.cliente.getId(), tecnicoAsignado, idServicio, descripcion);
		} else {
			System.out.println("Error en los datos ingrasados\n Vuelva a intentarlo");
		}

	}

	private Incidente verIncidente(Integer id) {
		this.incidente = incidenteController.verIncidente(id);
		return incidente;
	}

	private void cambiarEstadoIncidente() {
		System.out.print("Ingrese id del incidente que quiere cambiar el estado: ");
		String idStr = scanner.nextLine();
		Integer id = Integer.valueOf(idStr);
		System.out.print("Ingrese el nuevo estado: ");
		String estado = scanner.nextLine();
		incidenteController.cambiarEstadoIncidente(id, estado);
	}

	private void crearServicio() {
		System.out.print("Ingrese el nombre del nuevo servicio: ");
		String nombre = scanner.nextLine();
		servicioController.CrearServicio(nombre);
	}

	private void eliminarServicio() {
		System.out.print("Ingrese el id del servicio a eliminar: ");
		System.out.print("Si no lo recuerda presione 0: ");
		String idStr = scanner.nextLine();
		Integer id = Integer.valueOf(idStr);
		if (id == 0) {
			listadoServicios();
			System.out.print("Ingrese el id a eliminar: ");
			String idStr2 = scanner.nextLine();
			id = Integer.valueOf(idStr2);
		}
		servicioController.eliminarServicio(id);
	}

	private void agregarEspecialidadServicio() {
		System.out.print("Ingrese el id del servicio: ");
		System.out.print("Si no lo recuerda presione 0: ");
		String idStr = scanner.nextLine();
		if (sonTodosDigitos(idStr)) {
			Integer id = Integer.valueOf(idStr);

			if (id == 0) {
				listadoServicios();
				System.out.print("Ingrese el id del servicio: ");
				idStr = scanner.nextLine();
				if (sonTodosDigitos(idStr)) {
					id = Integer.valueOf(idStr);
				} else {
					System.out.println("Solo se permite ingresar NUMEROS\nVuelva a intentarlo1");
					return;
				}
			}
			System.out.print("Ingrese la nueva especialidad: ");
			String especialidad = scanner.nextLine();
			servicioController.agregarEspecialidad(id, especialidad);
		} else {
			System.out.println("Solo se permite ingresar NUMEROS\nVuelva a intentarlo");
			return;
		}

	}

	private boolean sonTodosDigitos(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	private void eliminarEspecialidadServicio() {
		System.out.print("Ingrese el id del servicio: ");
		System.out.print("Si no lo recuerda presione 0: ");
		String idStr = scanner.nextLine();
		Integer id = Integer.valueOf(idStr);
		if (id == 0) {
			listadoServicios();
			System.out.print("Ingrese el id del servicio: ");
			idStr = scanner.nextLine();
			id = Integer.valueOf(idStr);
		}
		System.out.print("Ingrese la vieja especialidad: ");
		String especialidad = scanner.nextLine();
		servicioController.eliminarEspecialidad(id, especialidad);
	}
}
