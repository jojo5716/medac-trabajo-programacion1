package juego_varillas;

public class main {
	private static String[][] initializeGame() {
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
		System.out.println("Iniciando juego...");
		String[][] initialState = initializeGame();
		
		JuegoColores game = new JuegoColores(initialState);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 1...");
		game.mover(0, 2);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 2...");
		game.mover(0, 2);
		game.mostrarEstado();
		
		
		System.out.println("Haciendo movimiento 3...");
		game.mover(0, 2);
		game.mostrarEstado();
	}
}
