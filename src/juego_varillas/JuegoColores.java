package juego_varillas;

public class JuegoColores {
	private static final int NUMERO_VARILLAS = 3;
	private static final int CAPACIDAD_MAXIMA = 4;
	private static final int MAX_HISTORY = 100;
	
	private final GestorArchivos fileManagement;
	private final String[][] varillas;
	private final int[] tamanosVarillas;
	private final String[][][] historial;
	private final int[][] historialTamanos;
	private int contadorHistorial;


	public JuegoColores(String[][] estadoInicial, GestorArchivos fileManagement) {
		this.varillas = new String[NUMERO_VARILLAS][CAPACIDAD_MAXIMA];
		this.tamanosVarillas = new int[NUMERO_VARILLAS];
		this.historial = new String[MAX_HISTORY][NUMERO_VARILLAS][CAPACIDAD_MAXIMA];
		this.historialTamanos = new int[MAX_HISTORY][NUMERO_VARILLAS];
		this.contadorHistorial = 0;
		this.fileManagement = fileManagement;
		
		for (int i = 0; i < NUMERO_VARILLAS; i++) {
			int size = 0;
			for (int j = 0; j < CAPACIDAD_MAXIMA; j++) {
				this.varillas[i][j] = estadoInicial[i][j];
				if (estadoInicial[i][j] != null) {
					size += 1;
				}
			}

			this.tamanosVarillas[i] = size;
		}
	}
	
	private boolean todosIguales(int varillaId) {
		int tamano = this.tamanosVarillas[varillaId];
		if (tamano <= 1) {
			return true;
		}
		
		String primerColor = this.varillas[varillaId][0];
		for (int i = 0; i < tamano; i++) {
			if (!(this.varillas[varillaId][i].equals(primerColor))) {
				return false;
			}
		}
		
		return true;
	}
	
	private void guardarEstado() {
		if (this.contadorHistorial >= MAX_HISTORY) {
			this.fileManagement.escribir("El historico se ha llenado.");
			return;
		}
		
		for (int i=0; i < NUMERO_VARILLAS; i++ ) {
			for (int j=0; j < CAPACIDAD_MAXIMA; j++ ) {
				this.historial[contadorHistorial][i][j] = this.varillas[i][j];
			}
		}
		
		for (int i=0; i< NUMERO_VARILLAS; i++) {
			this.historialTamanos[this.contadorHistorial][i] = this.tamanosVarillas[i];
		}
		
		this.contadorHistorial += 1;
	}
	
	
	public void deshacer() {
		if (this.contadorHistorial <= 0) {
			this.fileManagement.escribir("No hay ningun movimiento para deshacer");
			return;
		}
		
		this.contadorHistorial -= 1;
		
		for (int i=0; i < NUMERO_VARILLAS; i++ ) {
			for (int j=0; j < CAPACIDAD_MAXIMA; j++ ) {
				this.varillas[i][j] = this.historial[this.contadorHistorial][i][j];
			}
		}
		
		for (int i=0; i< NUMERO_VARILLAS; i++) {
			this.tamanosVarillas[i] = this.historialTamanos[this.contadorHistorial][i];
		}

		this.fileManagement.escribir("Se ha deshecho el último movimiento.");		
	}
	
	public void mostrarEstado() {
		System.out.println("\t Estado actual:");
		for (int i = 0; i < NUMERO_VARILLAS; i++) {
			System.out.print("\t\t Varilla " + ( i + 1) + ": [");
			for (int j = 0; j < tamanosVarillas[i]; j++) {
				System.out.print(varillas[i][j]);
				if (j < tamanosVarillas[i] - 1) {
					System.out.print("  ");
				}
			}
			System.out.println("]");
		}
	}
		
	private int obtieneEspaciosLibres(int varilla) {
		return this.CAPACIDAD_MAXIMA - this.tamanosVarillas[varilla];
	}
	
	private boolean validarMovimiento(int varilla_origen, int varilla_destino) { 
		var success = false; 
		if (varilla_origen < 0 || varilla_origen >= this.NUMERO_VARILLAS || varilla_destino < 0 || varilla_destino >= this.NUMERO_VARILLAS) { 
			this.fileManagement.escribir("Movimiento inválido: La varilla no existe."); 
			return success; 
		}
		
		if (varilla_origen == varilla_destino) { 
			this.fileManagement.escribir("Movimiento inválido: El destino no puede ser el mismo origen."); 
			return success; 
		}
		
		if (this.tamanosVarillas[varilla_origen] == 0) { 
			this.fileManagement.escribir("No hay bloques por mover."); 
			return success; 
		}

		if (this.obtieneEspaciosLibres(varilla_destino) == 0) {
			this.fileManagement.escribir("\t Varilla destino llena.");
			return success; 
		}
		
		success = true;
		return success; 
	}
	
	public void mover(int varilla_origen, int varilla_destino) {
	
		if(!(this.validarMovimiento(varilla_origen, varilla_destino))) {
			return;
		}
		
		int topOrigen = this.tamanosVarillas[varilla_origen] - 1;
		String color = this.varillas[varilla_origen][topOrigen];

		int bloquesConsecutivos = 0;
		for (int i = topOrigen; i >= 0; i--) {
			if (this.varillas[varilla_origen][i] != null && this.varillas[varilla_origen][i].equals(color)) {
				bloquesConsecutivos += 1;
			} else {
				break;
			}
		}
		
		int bloquesAMover = Math.min(this.obtieneEspaciosLibres(varilla_destino), bloquesConsecutivos);
		if (bloquesAMover == 0) {
			this.fileManagement.escribir("\t No se puede mover. Varilla destino llena");
			return;
		}
		
		this.guardarEstado();
		for (int i = 0; i < bloquesAMover; i++) {
			int idOrigen = this.tamanosVarillas[varilla_origen] - 1;
			int idDestino = this.tamanosVarillas[varilla_destino];

			this.varillas[varilla_destino][idDestino] = this.varillas[varilla_origen][idOrigen];
			this.varillas[varilla_origen][idOrigen] = null;
			
			this.tamanosVarillas[varilla_origen] -= 1;
			this.tamanosVarillas[varilla_destino] += 1;
		}
		
		this.fileManagement.escribir("\t Movidos " + bloquesAMover + " bloques " + color + 
				" de varilla " + (varilla_origen + 1) + " a varilla " + (varilla_destino + 1));
				
	}
	
	public boolean juegoCompletado() {
		for (int i = 0; i< this.NUMERO_VARILLAS; i++) {
			if (!(this.todosIguales(i))) {
				return false;
			}
		}
		
		return true;
	}
}
