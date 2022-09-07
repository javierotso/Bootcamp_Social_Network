package es.imatia.socialnetwork;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		HashMap<String, User> userList = new HashMap<>();

		byte option = -1;

		do {
			System.out.print("\n____________ MENÚ PRINCIPAL ____________\n");
			System.out.print("\n\t1. Crear usuario\n\t2. Seleccionar usuario existente\n\t0. Salir"
					+ "\n¿Que acción desea realizar?: ");
			try {
				option = sc.nextByte();
				switch (option) {
				case 1:
					createUser(userList);
					break;
				case 2:
					User selectedUser = selectUser(userList);
					if (selectedUser != null) {
						userMenu(selectedUser, userList);
					}
					break;
				case 0:
					System.out.println("\n.......... S A L I E N D O ..........\n");
					break;
				default:
					System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.");
				}
			} catch (Exception e) {
				System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.");
				option = -1;
			}
		} while (option != 0);

		sc.close();
	}

	public static void createUser(HashMap<String, User> userList) {
		Scanner sc = new Scanner(System.in);
		String userName;
		System.out.print("\nIndique el nombre de usuario: ");
		userName = sc.nextLine();
		if ((new User(userName).addUser(userList))) {
			System.out.print("\nUsuario creado correctamente.\n");
		} else {
			System.out.print("\n¡Error! Ya existe un usuario con ese nombre de usuario.\n");
		}

	}

	public static User selectUser(HashMap<String, User> userList) {
		User user = null;
		Scanner sc = new Scanner(System.in);
		if (!userList.isEmpty()) {
			System.out.print("\nIndique el nombre de usuario: ");
			String userName = sc.nextLine();
			if (userList.containsKey(userName)) {
				user = userList.get(userName);
			} else {
				System.out.print("\n¡ERROR! No existe ningún usuario con ese nombre de usuario.\n");
			}
		} else {
			System.out.print("\n¡Error! No existe ningún usuario todavía, cree una primero.\n");
		}
		return user;
	}

	public static void userMenu(User user, HashMap<String, User> userList) {
		byte userOption = -1;
		Scanner sc = new Scanner(System.in);
		while (userOption != 0) {
			System.out.print("\n__________ Menú de Usuario __________\n");
			System.out.print("\n\t1. Publicar un nuevo post\n\t2. Publicar un nuevo comentario"
					+ "\n\t3. Dejar de seguir a un usuario\n\t4. Seguir a un usuario.\n\t5. Eliminar un post"
					+ "\n\t6. Eliminar un comentario\n\t7. Listar posts\n\t8. Listar tus comentarios"
					+ "\n\t9. Eliminar usuario\n\t10.Mostrar comentarios\n\t0. Volver al menú principal"
					+ "\n¿Que acción desea realizar?: ");
			try {
				userOption = sc.nextByte();
				switch (userOption) {
				case 1:
					newPost(user);
					break;
				case 2:
					break;
				case 3:
					unfollowUser(user);
					break;
				case 4:
					followUser(userList, user);
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					System.out.print(user.showPostList());
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					break;
				case 0:
					System.out.println("\n.......... Volviendo al menú principal ..........");
					break;
				default:
					System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.");
				}
			} catch (Exception e) {
				System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.");
				userOption = -1;
			}
		}
	}

	public static void newPost(User user) {
		Scanner sc = new Scanner(System.in);
		boolean created = false;
		while (!created) {
			System.out
					.print("\n\t1. Imagen\n\t2. Text\n\t3. Video\n\t0. Cancelar\n¿Que tipo de post deseas publicar?: ");
			try {
				int postOption = sc.nextByte();
				sc.nextLine();
				switch (postOption) {
				case 1:
					System.out.print("\nIntroduzca el título de la imagen: ");
					String picTitle = sc.nextLine();
					System.out.print("\nIntroduzca el alto de la imagen: ");
					int height = readPositiveInt();
					System.out.print("\nIntroduzca el ancho de la imagen: ");
					int width = readPositiveInt();
					if (user.getUserPosts().add(new Picture(picTitle, height, width))) {
						System.out.print("\nPost creado correctamente.\n");
					} else {
						System.out.print("\nHa ocurrido un error, inténtelo de nuevo más tarde.\n");
					}
					created = true;
					break;
				case 2:
					System.out.print("\nIntroduzca el cuerpo del post: ");
					String body = sc.nextLine();
					if (user.getUserPosts().add(new Text(body))) {
						System.out.print("\nPost creado correctamente.\n");
					} else {
						System.out.print("\nHa ocurrido un error, inténtelo de nuevo más tarde.\n");
					}
					created = true;
					break;
				case 3:
					System.out.print("\nIntroduzca el título del video: ");
					String videoTitle = sc.nextLine();
					System.out.print("\nIntroduzca la calidad del vídeo (720, 1080...): ");
					int videoQuality = readPositiveInt();
					System.out.print("\nIntroduzca la duración del vídeo (en segundos): ");
					int videoDuration = readPositiveInt();
					if (user.getUserPosts().add(new Video(videoDuration, videoQuality, videoTitle))) {
						System.out.print("\nPost creado correctamente.\n");
					} else {
						System.out.print("\nHa ocurrido un error, inténtelo de nuevo más tarde.\n");
					}
					created = true;
					break;
				case 0:
					System.out.print("\nOperación cancelada\n");
					created = true;
					break;
				default:
					System.out.print("\nOpción seleccionada no válida\n");
				}
			} catch (Exception e) {
				sc.nextLine();
				System.out.print("\nOpción seleccionada no válida\n");
			}
		}
	}

	public static int readPositiveInt() {
		int num = -1;
		Scanner sc = new Scanner(System.in);
		while (num < 0) {
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				num = -1;
				sc.nextLine();
			}
			if (num < 0) {
				System.out.print("\n¡Error! Introduzca un número entero positivo: ");
			}
		}
		return num;
	}

	public static void followUser(HashMap<String, User> userList, User user) {
		Scanner sc = new Scanner(System.in);
		if (!user.getFollowedList().isEmpty()) {
			for (String name : userList.keySet()) {
				if (!user.getUserName().equals(name) && !user.getFollowedList().containsKey(name)) {
					System.out.print("\n\t" + name);
				}
			}
			String userName = sc.nextLine();
			user.followUser(userList, userName);
		}
	}

	public static void unfollowUser(User user) {
		Scanner sc = new Scanner(System.in);
		if (!user.getFollowedList().isEmpty()) {
			HashMap<String, User> followedList = user.getFollowedList();
			for (String name : followedList.keySet()) {
				System.out.print("\n\t" + name);
			}
			System.out.print("\n¿Que usuario deseas dejar de seguir?: ");
			String userName = sc.nextLine();
			user.unfollowUser(userName);
		} else {
			System.out.print("\nTodavía no sigues a ningún usuario.\n");
		}
	}
}