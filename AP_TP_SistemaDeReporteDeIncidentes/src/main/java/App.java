/*
Quedó pendiente hacer los enums Estado, TipoProblema y Especialidades
*/

import com.Hibernate.modelo.AreaComercial;
import com.Hibernate.modelo.AreaRRHH;
import com.Hibernate.modelo.MesaAyuda;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		System.out.println("SISTEMA DE REPORTE DE INCIDENTES \nCREADA POR MARTÍN MISELLI Y MARÍA KATHERINE CARLOS SALVATIERRA");
		System.out.println("Bienvenido a Trabajo Práctico Java Intermedio");

		Scanner scanner = new Scanner(System.in);

		while (true) {
			manuPricipal();

			String opcion = scanner.nextLine();

			switch (opcion) {
				case "1":
					System.out.println("Realizando trámites relacionados con clientes...");
					@SuppressWarnings("unused") AreaComercial 
					areaComercial = new AreaComercial();
					break;
				case "2":
					System.out.println("Realizando trámites relacionados con técnicos...");
					@SuppressWarnings("unused") AreaRRHH 
					areaRRHH = new AreaRRHH();
					break;
				case "3":
					System.out.println("Realizando trámites relacionados con incidentes o servicios...");
					@SuppressWarnings("unused") MesaAyuda 
					mesaAyuda = new MesaAyuda();
					break;
				case "0":
					System.out.println("Saliendo del programa. Muchas gracias por formar parte de nuestra familia.");
					System.exit(0);
				default:
					System.out.println("Ups! Opción inválida.");
			}
		}
	}

	private static void manuPricipal() {
		System.out.println("**********************************************************************");
		System.out.println("Menú:");
		System.out.println("1. Trámites relacionados con clientes");
		System.out.println("2. Trámites relacionados con técnicos");
		System.out.println("3. Trámites relacionados con incidentes o servicios");
		System.out.println("0. Salir");
		System.out.println("**********************************************************************");
		System.out.print("Ingrese la opción: ");
	}
}
