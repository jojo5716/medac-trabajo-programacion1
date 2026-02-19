package juego_varillas;

import java.util.Scanner;

public class main {
	private static String[][] initializeDefaultGame() {
		String[][] initialState = new String[3][4];
		
		initialState[0][0] = "R";
		initialState[0][1] = "R";
		initialState[0][2] = "V";
		initialState[0][3] = "V";

		initialState[1][0] = "A";

		initialState[2][0] = "A";
		
		return initialState;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		GestorArchivos gestorArchivos = new GestorArchivos();
		String[][] initialState = null;
		
		gestorArchivos.escribir("¿Deseas cargar una partida desde un fichero? (S/N): ");
		boolean shouldLoadFromFile = sc.nextLine().trim().toUpperCase().equals("S");
		
		if (shouldLoadFromFile) {
			gestorArchivos.escribir("Introduce el nombre del fichero (ejemplo: partida1.txt): ");
			String fileName = sc.nextLine().trim();
			initialState = gestorArchivos.iniciarGrabacion(fileName);
			
			if (initialState == null) {
				gestorArchivos.escribir("Hubo un error al cargar la partida desde el fichero.");
				gestorArchivos.escribir("Cargando partida por defecto...");
				initialState = initializeDefaultGame();
			}
		} else {
			initialState = initializeDefaultGame();
		}
		
		gestorArchivos.escribir("Iniciando juego...");
		
		JuegoColores game = new JuegoColores(initialState, gestorArchivos);
		
		JuegoManager manager = new JuegoManager(game, gestorArchivos);
		
		manager.iniciarJuego();
	}
}
