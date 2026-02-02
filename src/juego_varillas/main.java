package juego_varillas;

public class main {

	public static void main(String[] args) {
		System.out.println("Iniciando juego...");
		String[][] initialState = new String[3][4];
		
		initialState[0][0] = "R";
		initialState[0][1] = "R";
		initialState[0][2] = "V";
		initialState[0][3] = "V";

		initialState[1][0] = "A";
		initialState[2][0] = "A";
		
		JuegoColores game = new JuegoColores(initialState);
		game.mostrarEstado();
	}

}
