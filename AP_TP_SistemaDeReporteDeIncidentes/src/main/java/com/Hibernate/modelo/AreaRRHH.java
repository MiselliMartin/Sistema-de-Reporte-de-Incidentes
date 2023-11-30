package com.Hibernate.modelo;

import com.Hibernate.controller.TecnicoController;

import java.util.Scanner;

public class AreaRRHH {
	private Tecnico tecnico;

	private TecnicoController tecnicoController = new TecnicoController();

	Scanner scanner = new Scanner(System.in);

	public AreaRRHH() {
		iniciarAreaRRHH();
	}

	private void iniciarAreaRRHH() {
		while (true) {
			menuRRHH();
			String opcion = scanner.nextLine();
			if (opcion.equals("1")) {
				identificacionTecnico();
				if (this.tecnico == null) {
					return;
				}
			} else if (opcion.equals("2")) {
				altaTecnico();
			} else if (opcion.equals("0")) {
				return;
			} else {
				continue;
			}
			while (true && this.tecnico != null) {
				menuEspecialidades();
				String segundaOpcion = scanner.nextLine();
				if (segundaOpcion.equals("1")) {
					agregarEspecialidad();
				} else if (segundaOpcion.equals("2")) {
					eliminarEspecialidad();
				} else if (segundaOpcion.equals("3")) {
					verEspecialidades();
				} else if (segundaOpcion.equals("4")) {
					cambiarDisponible();
				} else if (segundaOpcion.equals("5")) {
					bajaTecnico();
				} else if (segundaOpcion.equals("6")) {
					tecnicoDisponible(this.tecnico.getId());
				} else if (segundaOpcion.equals("7")) {
					listadoTecnicos();
				} else if (segundaOpcion.equals("8")) {
					listadoTecnicosDisponibles();
				} else if (segundaOpcion.equals("0")) {
					return;
				} else {
					continue;
				}

			}
		}
	}

	private void menuEspecialidades() {
		System.out.println("**********************************************************************");
		System.out.println("Para agregar una especialidad presione 1:");
		System.out.println("Para eliminar una especialidad presione 2:");
		System.out.println("Para ver un listado de especialidades presione 3:");
		System.out.println("Para cambiar su disponibilidad presione 4:");
		System.out.println("Para darse de baja presione 5:");
		System.out.println("Para saber su disponibilidad presione 6:");
		System.out.println("Para ver un listado de los técnicos presione 7:");
		System.out.println("Para ver un listado de los técnicos disponibles presione 8:");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private void identificacionTecnico() {
		opcionesTecnico();
		String idONombre = scanner.nextLine();
		if (idONombre.equals("1")) {
			buscarID();
		} else if (idONombre.equals("2")) {
			buscarNombreYMail();
		}
	}

	private void opcionesTecnico() {
		System.out.println("**********************************************************************");
		System.out.println("Para identificarse mediante id presione 1");
		System.out.println("Para identificarse mediante nombre y mail presione 2");
		System.out.println("Para salir presione 0:");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private void buscarNombreYMail() {
		System.out.print("Por favor ingrese el nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Por favor ingrese el mail: ");
		String mail = scanner.nextLine();
		verTecnicoNombreyMail(nombre, mail);
	}

	private void buscarID() {
		System.out.print("Ingrese id: ");
		String idstr = scanner.nextLine();
		Integer id = Integer.valueOf(idstr);
		verTecnico(id);
	}

	private void menuRRHH() {
		System.out.println("**********************************************************************");
		System.out.println("Bienvenide al área de Recursos Humanos");
		System.out.println("Si usted ya es técnico en nuestra empresa presione 1");
		System.out.println("Para volverse técnico presione 2");
		System.out.println("Para salir presione 0");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}

	private Tecnico verTecnico(Integer id) {
		this.tecnico = tecnicoController.verTecnico(id);
		return tecnico;
	}

	private Tecnico verTecnicoNombreyMail(String nombre, String mail) {
		this.tecnico = tecnicoController.verTecnicoNombreyMail(nombre, mail);
		return tecnico;
	}

	private Tecnico altaTecnico() {
		System.out.print("Ingrese por favor el nombre del técnico: ");
		String nombre = scanner.nextLine();
		System.out.print("Ingrese por favor el email: ");
		String mail = scanner.nextLine();
		this.tecnico = tecnicoController.crearTecnico(nombre, mail);
		return tecnico;
	}

	private void bajaTecnico() {
		System.out.print("Como medida de seguridad ingrese su mail tal como está registrado: ");
		String mailIngresado = scanner.nextLine();
		if (tecnico.getMail().equals(mailIngresado)) {
			tecnicoController.eliminarTecnico(this.tecnico.getId());
		} else {
			System.out.println("Error de seguridad. Técnico no eliminado");
		}
	}

	private void verEspecialidades() {
		tecnicoController.listadoEspecialidades(this.tecnico.getId());
	}

	private void agregarEspecialidad() {
		System.out.print("Ingrese su nueva especialidad: ");
		String especialidad = scanner.nextLine();
		tecnicoController.agregarEspecialidad(this.tecnico.getId(), especialidad);
	}

	public void eliminarEspecialidad(String especialidad) {
		tecnicoController.eliminarEspecialidad(this.tecnico.getId(), especialidad);
	}

	private void eliminarEspecialidad() {
		System.out.print("Ingrese la especialidad a eliminar: ");
		String especialidad = scanner.nextLine();
		tecnicoController.eliminarEspecialidad(this.tecnico.getId(), especialidad);
	}

	private void listadoTecnicos() {
		tecnicoController.listadoTecnicos();
	}

	private boolean tecnicoDisponible(Integer id) {
		return tecnicoController.disponibilidadTecnico(id);
	}

	private void listadoTecnicosDisponibles() {
		tecnicoController.tecnicosDisponibles();
	}

	public void agregarEspecialidad(String especialidad) {
		tecnicoController.agregarEspecialidad(this.tecnico.getId(), especialidad);
	}

	public void cambiarDisponible() {
		System.out.println();
		tecnicoController.cambiarDisponible(this.tecnico.getId());
	}
}
