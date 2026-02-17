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
		
		System.out.println("Deshaciendo ultimo movimiento...");
		game.undoMovement();
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 1...");
		game.mover(0, 2);
		game.mostrarEstado();
		
		System.out.println("Deshaciendo ultimo movimiento...");
		game.undoMovement();
		game.mostrarEstado();

		
		System.out.println("Haciendo movimiento 2...");
		game.mover(0, 2);
		game.mostrarEstado();
		
		
		System.out.println("Haciendo movimiento 3...");
		game.mover(0, 2);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 4...");
		game.mover(2, 0);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 5...");
		game.mover(2, 1);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 6...");
		game.mover(1, 0);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 7...");
		game.mover(1, 2);
		game.mostrarEstado();
		
		System.out.println("Haciendo movimiento 8...");
		game.mover(0, 1);
		game.mostrarEstado();
		
		if (game.isGameFinished()) {
			System.out.println("Juego terminado.");
		} else {
			System.out.println("Juego no terminado.");
		}
	}
}
