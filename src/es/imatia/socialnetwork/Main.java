package es.imatia.socialnetwork;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		HashMap<String, User> userList = new HashMap<>();

		Utils.startTask(userList);

		int option = -1;

		do {
			System.out.print("\n____________ MENÚ PRINCIPAL ____________\n");
			System.out.print("\n\t1. Crear usuario\n\t2. Seleccionar usuario existente\n\t0. Salir"
					+ "\n¿Que acción desea realizar?: ");
			try {
				option = Utils.readPositiveInt();
				switch (option) {
				case 1:
					Utils.createUser(userList);
					break;
				case 2:
					User selectedUser = Utils.selectUser(userList);
					if (selectedUser != null) {
						Utils.userMenu(selectedUser, userList);
					}
					break;
				case 0:
					System.out.println("\n.......... S A L I E N D O ..........\n");
					break;
				default:
					System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.\n");
				}
			} catch (Exception e) {
				System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.");
				option = -1;
			}
		} while (option != 0);
	}

	
}