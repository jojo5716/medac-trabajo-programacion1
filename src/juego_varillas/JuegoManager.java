package juego_varillas;

import java.util.Scanner;

public class JuegoManager {
	private final Scanner sc;
	private final JuegoColores game;
	private final GestorArchivos gestorArchivos;
	
	
	public JuegoManager(JuegoColores game, GestorArchivos gestorArchivos) {
		this.sc = new Scanner(System.in);
		this.game = game;
		this.gestorArchivos = gestorArchivos;
	}
	
	private void mostrarBienvenida() {
		this.gestorArchivos.escribir("Bienvenido al juego de colores");
		this.gestorArchivos.escribir("Comandos: 1-3 para mover varillas, D=Deshacer, S=Salir");
	}
	
	private boolean procesarComando() {
		this.gestorArchivos.escribir("Selecciona varilla origen (1-3, D=Deshacer, S=Salir): ");
		String texto_entrada = sc.nextLine().trim().toUpperCase();
		
		if (texto_entrada.equals("S")) {
			this.gestorArchivos.escribir("Saliendo del juego...");
			return false;
		}
		
		if (texto_entrada.equals("D")) {
			game.deshacer();
			return true;
		}
		
		int varilla_origen;
		try {
			varilla_origen = Integer.parseInt(texto_entrada);
		} catch (Exception e) {
			this.gestorArchivos.escribir("Comando inválido.");
			return true;
		}
		
		this.gestorArchivos.escribir("Selecciona la varilla destino (1-3): ");
		String texto_varilla_destino = sc.nextLine().trim();
		
		int varilla_destino;
		try {
			varilla_destino = Integer.parseInt(texto_varilla_destino);
		} catch (Exception e) {
			this.gestorArchivos.escribir("Opcion inválida.");
			return true;
		}
		
		this.procesarMovimiento(varilla_origen, varilla_destino);
		
		return true;
	}
	
	public void procesarMovimiento(int rodFrom, int rodTo) {
		game.mover(rodFrom - 1, rodTo - 1);
	}
	
	private boolean verificarVictoria() {
		return game.juegoCompletado();
	}
	
	public void iniciarJuego() {
		boolean continuar = true;
		this.gestorArchivos.clearFile();
		
		mostrarBienvenida();
		
		while (continuar) {
			game.mostrarEstado();
			
			if (this.verificarVictoria()) {
				System.out.println("Juego terminado");
				break;
			}

			continuar = this.procesarComando();
		}
	}
	
}
