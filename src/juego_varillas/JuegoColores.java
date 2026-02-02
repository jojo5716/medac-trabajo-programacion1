package juego_varillas;

public class JuegoColores {
	private static final int NUM_RODS = 3;
	private static final int MAX_BLOCKS_BY_ROD= 4;
	
	private final String[][] rods;
	private final int[] rodSizes;
	
	public JuegoColores(String[][] initialState) {
		this.rods = new String[NUM_RODS][MAX_BLOCKS_BY_ROD];
		this.rodSizes = new int[NUM_RODS];
		
		for (int i = 0; i < NUM_RODS; i++) {
			int size = 0;
			for (int j = 0; j < MAX_BLOCKS_BY_ROD; j++) {
				this.rods[i][j] = initialState[i][j];
				if (initialState[i][j] != null) {
					size += 1;
				}
			}
			
			this.rodSizes[i] = size;
		}
	}
	
	public void mostrarEstado() {
		for (int i = 0; i < NUM_RODS; i++) {
			System.out.print("Varilla " + ( i + 1) + ": [");
			for (int j = 0; j < rodSizes[i]; j++) {
				System.out.print(rods[i][j]);
				if (j < rodSizes[i] - 1) {
					System.out.print(" ");
				}
			}
			System.out.println("]");
		}
	}
}
