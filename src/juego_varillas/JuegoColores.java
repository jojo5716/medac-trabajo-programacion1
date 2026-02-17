package juego_varillas;

public class JuegoColores {
	private static final int NUM_RODS = 3;
	private static final int MAX_BLOCKS_BY_ROD= 4;
	private static final int MAX_HISTORY= 100;
	
	private final String[][] rods;
	private final int[] rodSizes;
	private final String[][][] rodHistory;
	private final int[][] sizeHistory;
	private int historyCounter;


	public JuegoColores(String[][] initialState) {
		this.rods = new String[NUM_RODS][MAX_BLOCKS_BY_ROD];
		this.rodSizes = new int[NUM_RODS];
		this.rodHistory = new String[MAX_HISTORY][NUM_RODS][MAX_BLOCKS_BY_ROD];
		this.sizeHistory = new int[MAX_HISTORY][NUM_RODS];
		this.historyCounter = 0;
		
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
	
	private boolean allValuesAreEquals(int rodId) {
		int size = this.rodSizes[rodId];
		if (size <= 1) {
			return true;
		}
		
		String firstColor = this.rods[rodId][0];
		for (int i = 0; i < size; i++) {
			if (!(this.rods[rodId][i].equals(firstColor))) {
				return false;
			}
		}
		
		return true;
	}
	
	private void saveHistoryState() {
		if (this.historyCounter >= MAX_HISTORY) {
			System.out.println("El historico se ha llenado.");
			return;
		}
		
		for (int i=0; i < NUM_RODS; i++ ) {
			for (int j=0; j < MAX_BLOCKS_BY_ROD; j++ ) {
				this.rodHistory[historyCounter][i][j] = this.rods[i][j];
			}
		}
		
		for (int i=0; i< NUM_RODS; i++) {
			this.sizeHistory[this.historyCounter][i] = this.rodSizes[i];
		}
		
		this.historyCounter += 1;
	}
	
	
	public void undoMovement() {
		if (this.historyCounter <= 0) {
			System.out.println("No hay ningun movimiento para deshacer");
			return;
		}
		
		this.historyCounter -= 1;
		
		for (int i=0; i < NUM_RODS; i++ ) {
			for (int j=0; j < MAX_BLOCKS_BY_ROD; j++ ) {
				this.rods[i][j] = this.rodHistory[this.historyCounter][i][j];
			}
		}
		
		for (int i=0; i< NUM_RODS; i++) {
			this.rodSizes[i] = this.sizeHistory[this.historyCounter][i];
		}

		System.out.println("Se ha deshecho el último movimiento.");		
	}
	
	public void mostrarEstado() {
		System.out.println("\t Estado actual:");
		for (int i = 0; i < NUM_RODS; i++) {
			System.out.print("\t\t Varilla " + ( i + 1) + ": [");
			for (int j = 0; j < rodSizes[i]; j++) {
				System.out.print(rods[i][j]);
				if (j < rodSizes[i] - 1) {
					System.out.print("  ");
				}
			}
			System.out.println("]");
		}
	}
		
	private int getFreeSpace(int rod) {
		return this.MAX_BLOCKS_BY_ROD - this.rodSizes[rod];
	}
	
	private boolean validateMovement(int rod_from, int rod_to) { 
		var success = false; 
		if (rod_from < 0 || rod_from >= this.NUM_RODS || rod_to < 0 || rod_to >= this.NUM_RODS) { 
			System.out.println("Movimiento inválido: La varilla no existe."); 
			return success; 
		}
		
		if (rod_from == rod_to) { 
			System.out.println("Movimiento inválido: El destino no puede ser el mismo origen."); 
			return success; 
		}
		
		if (this.rodSizes[rod_from] == 0) { 
			System.out.println("No hay bloques por mover."); 
			return success; 
		}

		if (this.getFreeSpace(rod_to) == 0) {
			System.out.println("\t Varilla destino llena.");
			return success; 
		}
		
		success = true;
		return success; 
	}
	
	public void mover(int rod_from, int rod_to) {
	
		if(!(this.validateMovement(rod_from, rod_to))) {
			return;
		}
		
		int topFrom = this.rodSizes[rod_from] - 1;
		String color = this.rods[rod_from][topFrom];

		int consecutiveBlocks = 0;
		for (int i = topFrom; i >= 0; i--) {
			if (this.rods[rod_from][i] != null && this.rods[rod_from][i].equals(color)) {
				consecutiveBlocks += 1;
			} else {
				break;
			}
		}
		
		int blocksToMove = Math.min(this.getFreeSpace(rod_to), consecutiveBlocks);
		if (blocksToMove == 0) {
			System.out.println("\t No se puede mover. Varilla destino llena");
			return;
		}
		
		this.saveHistoryState();
		for (int i = 0; i < blocksToMove; i++) {
			int idFrom = this.rodSizes[rod_from] - 1;
			int idTo = this.rodSizes[rod_to];

			this.rods[rod_to][idTo] = this.rods[rod_from][idFrom];
			this.rods[rod_from][idFrom] = null;
			
			this.rodSizes[rod_from] -= 1;
			this.rodSizes[rod_to] += 1;
		}
		
		System.out.println("\t Movidos " + blocksToMove + " bloques " + color + 
				" de varilla " + (rod_from + 1) + " a varilla " + (rod_to + 1));
				
	}
	
	public boolean isGameFinished() {
		for (int i = 0; i< this.NUM_RODS; i++) {
			if (!(this.allValuesAreEquals(i))) {
				return false;
			}
		}
		
		return true;
	}
}
