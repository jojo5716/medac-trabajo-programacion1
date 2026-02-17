package juego_varillas;

import java.util.Scanner;

public class GameManager {
	private final Scanner sc;
	private final JuegoColores game;
	private final FileManagement fileManagement;
	
	
	public GameManager(JuegoColores game, FileManagement fileManagement) {
		this.sc = new Scanner(System.in);
		this.game = game;
		this.fileManagement = fileManagement;
	}
	
	private void showInstructions() {
		this.fileManagement.logMessage("Comandos: 1-3 para mover varillas, D=Deshacer, S=Salir");
	}
	
	public void initialiceGame() {
		this.fileManagement.clearFile();
		this.fileManagement.logMessage("Bienvenido al juego de colores");
		showInstructions();
		
		while (true) {
			game.mostrarEstado();
			
			if (game.isGameFinished()) {
				System.out.println("Juego terminado");
				break;
			}
			
			System.out.print("Selecciona varilla origen (1-3, D=Deshacer, S=Salir): ");
			String inputText = sc.nextLine().trim().toUpperCase();
			
			if (inputText.equals("S")) {
				this.fileManagement.logMessage("Saliendo del juego...");
				break;
			}
			
			if (inputText.equals("D")) {
				game.undoMovement();
				continue;
			}
			
			int rodFrom;
			try {
				rodFrom = Integer.parseInt(inputText);
			} catch (Exception e) {
				this.fileManagement.logMessage("Comando inválido.");
				continue;
			}
			
			this.fileManagement.logMessage("Selecciona la varilla destino (1-3):");
			String inputTo = sc.nextLine().trim();
			
			int rodTo;
			try {
				rodTo = Integer.parseInt(inputTo);
			} catch (Exception e) {
				this.fileManagement.logMessage("Opcion inválida.");
				continue;
			}
			
			game.mover(rodFrom - 1, rodTo - 1);
		}
	}
	
}
