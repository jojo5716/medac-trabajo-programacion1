package juego_varillas;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;


public class GestorArchivos {
	private static final String OUTPUT_FILE = "partida_guardada.txt";
		
	private void appendLine(String message) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(OUTPUT_FILE, true));
			out.println(message);
		} catch(Exception e) {
			System.err.println("Error al escribir en el fichero: " + e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public void escribir(String mensaje) {
		System.out.print(mensaje);

		this.appendLine(mensaje);
	}

	public void clearFile() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(OUTPUT_FILE, true));
			
		} catch(Exception e) {
			System.err.println("Error al borrar el contenido del fichero: " + e.getMessage());
		} finally {
			if (out != null) {
				out.close();	
			}
		}
	}
	
	public String[][] iniciarGrabacion(String inputFile) {
		String[][] state = new String[3][4];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFile));
			String line;
			int rod = 0;
			
			while ((line = br.readLine()) != null && rod < 3) {
				line = line.trim();
				if (line.isEmpty()) {
					rod += 1;
					continue;
				}
				
				String[] tokens = line.split("\\s+");
				int limit = Math.min(tokens.length, 4);
				
				for (int i = 0; i < limit; i ++) {
					state[rod][i] = tokens[i];
				}
				
				rod += 1;
			}
		} catch(Exception e) {			
			System.err.println("Error al cargar el fichero: " + e.getMessage());
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();	
				} catch (IOException e) {
					System.err.println("Hubo un error al cerrar el fichero: " +e.getMessage());
				}
				
			}
		}
		
		return state;
	}
}
