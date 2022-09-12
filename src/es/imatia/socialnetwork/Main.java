package es.imatia.socialnetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		HashMap<String, User> userList = new HashMap<>();

		startTask(userList);

		int option = -1;

		do {
			System.out.print("\n____________ MENÚ PRINCIPAL ____________\n");
			System.out.print("\n\t1. Crear usuario\n\t2. Seleccionar usuario existente\n\t0. Salir"
					+ "\n¿Que acción desea realizar?: ");
			try {
				option = readPositiveInt();
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
					System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.\n");
				}
			} catch (Exception e) {
				System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.");
				option = -1;
			}
		} while (option != 0);
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
		int userOption = -1;
		Scanner sc = new Scanner(System.in);
		while (userOption != 0) {
			System.out.print("\n__________ Menú de Usuario __________\n");
			System.out.print("\n\t1. Publicar un nuevo post\n\t2. Publicar un nuevo comentario"
					+ "\n\t3. Dejar de seguir a un usuario\n\t4. Seguir a un usuario.\n\t5. Eliminar un post"
					+ "\n\t6. Eliminar un comentario\n\t7. Listar posts\n\t8. Listar tus comentarios"
					+ "\n\t9. Eliminar usuario\n\t10. Mostrar comentarios de post\n\t11. Mostrar muro"
					+ "\n\t12. Sugerencias de amistad\n\t0. Volver al menú principal\n¿Que acción desea realizar?: ");
			try {
				userOption = readPositiveInt();
				switch (userOption) {
				case 1:
					newPost(user);
					break;
				case 2:
					if (!user.getFollowedPostList().isEmpty()) {
						publishComment(user);
					} else {
						System.out.print("\nTodavía no hay post\n");
					}
					break;
				case 3:
					unfollowUser(user);
					break;
				case 4:
					followUser(userList, user);
					break;
				case 5:
					deletePost(user);
					break;
				case 6:
					if(!user.getUserCommentList(userList).isEmpty()) {
						System.out.print(user.showUserCommentList(userList) + "\n¿Que número de comentario deseas eliminar?: ");
						Comment comment = user.getUserCommentList(userList).get(readPositiveInt());
						if(comment != null) {
							if(user.deleteComment(userList, comment)) {
								System.out.print("\nSe ha eliminado el comentario.\n");
							} else {
								System.out.print("\nNo ha sido posible realizar la acción.\n");
							}
						}
					} else {
						System.out.print("\nTodavía no has publicado ningún comentario\n");
					}
					
					break;
				case 7:
					System.out.print(user.showOwnPostList());
					break;
				case 8:
					System.out.print(user.showUserCommentList(userList));
					break;
				case 9:
					System.out.print(
							"\nSe eliminarán todos tus post, comentarios, lista de seguidores... Escriba CONFIRMAR para proceder: ");
					if (sc.nextLine().equalsIgnoreCase("confirmar")) {
						if (user.deleteUser(userList)) {
							System.out.print("\nUsuario eliminado correctamente.\n");
							userOption = 0;
						} else {
							System.out.print("\nNo ha sido posible realizar la acción.\n");
						}
					}
					break;
				case 10:
					if (!user.getFollowedPostList().isEmpty()) {
						showCommentList(user);
					} else {
						System.out.print("\nTodavía no hay post\n");
					}
					break;
				case 11:
					System.out.print(user.showWall());
					break;
				case 12:
					System.out.print(user.friendsSuggestion(userList));
					break;
			
				case 0:
					System.out.println("\n.......... Volviendo al menú principal ..........");
					break;
				default:
					System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.\n");
				}
			} catch (Exception e) {
				System.out.print("\n¡ERROR! Por favor, selecciona una opción de la lista.\n");
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
				int postOption = readPositiveInt();
				switch (postOption) {
				case 1:
					System.out.print("\nIntroduzca el título de la imagen: ");
					String picTitle = sc.nextLine();
					System.out.print("\nIntroduzca el alto de la imagen: ");
					int height = readPositiveInt();
					System.out.print("\nIntroduzca el ancho de la imagen: ");
					int width = readPositiveInt();
					if (user.getUserPosts().add(new Image(picTitle, height, width))) {
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

	public static void deletePost(User user) {
		if (!user.getUserPosts().isEmpty()) {
			System.out.print(user.showOwnPostList());
			System.out.print("\n¿Indique el número de post que desea eliminar (0 para cancelar): ");
			if (user.deletePost(readPositiveInt())) {
				System.out.print("\nPost eliminado correctamente\n");
			} else {
				System.out.print("\nPost seleccionado no válido\n");
			}
		} else {
			System.out.print("\nTodavía no tienes ningún post\n");
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
			}
			if (num < 0) {
				System.out.print("\n¡Error! Introduzca un número entero positivo: ");
			}
			sc.nextLine();
		}
		return num;
	}

	public static void publishComment(User user) {
		Scanner sc = new Scanner(System.in);
		System.out.print(user.showFollowedPostList());
		System.out.print("\n¿Indique el número de post en el que desea publicar el comentario (0 para cancelar): ");
		Post selectedPost = user.getFollowedPostList().get(readPositiveInt());
		if (selectedPost != null) {
			System.out.print("\nIntroduzca el comentario: ");
			String body = sc.nextLine();
			if (selectedPost.publishComment(body, user)) {
				System.out.print("\nComentario publicado correctamente.\n");
			} else {
				System.out.print("\nNo se ha podido publicar el comentario.\n");
			}
		} else {
			System.out.print("\nPost seleccionado no válido\n");
		}
	}
	
	public static void showCommentList(User user) {
		Scanner sc = new Scanner(System.in);
		System.out.print(user.showFollowedPostList());
		System.out.print("\n¿Indique el número de post en el que desea ver los comentarios (0 para cancelar): ");
		Post selectedPost = user.getFollowedPostList().get(readPositiveInt());
		if (selectedPost != null) {
			System.out.print(selectedPost.showCommentList());
		} else {
			System.out.print("\nPost seleccionado no válido\n");
		}
	}
	

	public static void followUser(HashMap<String, User> userList, User user) {
		Scanner sc = new Scanner(System.in);
		if (userList.size() > user.getFollowedList().size()) {
			System.out.print("\nLista de usuarios a los que no sigues: ");
			for (String name : userList.keySet()) {
				if (!user.getUserName().equals(name) && !user.getFollowedList().containsKey(name)) {
					System.out.print("\n\t" + name);
				}
			}
			System.out.print("\n¿A quién quieres seguir?: ");
			String userName = sc.nextLine();
			if (user.followUser(userList, userName)) {
				System.out.print("\nAhora sigues a " + userName + "\n");
			} else {
				System.out.print("\nNo existe el usuario seleccionado\n");
			}
		} else {
			System.out.print("\nYa sigues a todos los usuarios disponibles\n");
		}
	}

	public static void unfollowUser(User user) {
		Scanner sc = new Scanner(System.in);
		if (!user.getFollowedList().isEmpty()) {
			System.out.print("\nLista de usuarios a los que sigues: ");
			HashMap<String, User> followedList = user.getFollowedList();
			for (String name : followedList.keySet()) {
				System.out.print("\n\t" + name);
			}
			System.out.print("\n¿Que usuario deseas dejar de seguir?: ");
			String userName = sc.nextLine();
			if (user.unfollowUser(userName)) {
				System.out.print("\nYa no sigues a " + userName + "\n");
			} else {
				System.out.print("\nNo se ha podido realizar la acción\n");
			}
		} else {
			System.out.print("\nTodavía no sigues a ningún usuario.\n");
		}
	}
	
	public static void startTask(HashMap<String, User> userList) {
		// Usuarios
		userList.put("pepe", new User("pepe"));
		userList.put("manolita", new User("manolita"));
		userList.put("juan", new User("juan"));
		userList.put("julia", new User("julia"));
		userList.put("sandra", new User("sandra"));
		userList.put("diego", new User("diego"));
		userList.put("c", new User("carlos"));

		// seguidores de pepe
		userList.get("pepe").getFollowedList().put("manolita", userList.get("manolita"));
		userList.get("pepe").getFollowedList().put("diego", userList.get("diego"));

		// seguidores de manolita
		userList.get("manolita").getFollowedList().put("juan", userList.get("juan"));
		userList.get("manolita").getFollowedList().put("diego", userList.get("diego"));

		// post de pepe
		Post imagpepe = new Image(LocalDateTime.of(2022, 5, 4, 22, 35), new ArrayList<Comment>(), "Selfie.jpg", 400,
				450);
		Post textpepe = new Text(LocalDateTime.of(2022, 4, 1, 13, 42), new ArrayList<Comment>(),
				"Hoy salí de acampada");

		// comentarios de diego y manolita en el post de pepe
		Comment commenttextpepediego = new Comment("Menuda suerte", LocalDateTime.of(2022, 4, 2, 23, 42),
				userList.get("diego"));
		Comment commenttextpepemanolita = new Comment("Espero que no lloviese", LocalDateTime.of(2022, 4, 2, 22, 37),
				userList.get("manolita"));

		textpepe.getCommentList().add(commenttextpepediego);
		textpepe.getCommentList().add(commenttextpepemanolita);

		userList.get("pepe").addPost(textpepe);
		userList.get("pepe").addPost(imagpepe);

	}
}