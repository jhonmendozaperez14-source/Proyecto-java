package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import clases.Retiro;

// Arreglo de retiros: ArrayList + archivo retiros.txt
public class ArregloRetiros {
	private static final String ARCHIVO = "retiros.txt";
	private ArrayList<Retiro> retiros;

	// Al crearse carga retiros.txt (si existe)
	public ArregloRetiros() {
		retiros = new ArrayList<Retiro>();
		cargarRetiros();
	}

	public int tamanio() {
		return retiros.size();
	}

	public Retiro obtener(int i) {
		return retiros.get(i);
	}

	public void adicionar(Retiro x) {
		retiros.add(x);
	}

	public void eliminar(Retiro x) {
		retiros.remove(x);
	}

	// Busca un retiro por su número (null si no existe)
	public Retiro buscar(int numRetiro) {
		for (int i = 0; i < tamanio(); i++)
			if (obtener(i).getNumRetiro() == numRetiro)
				return obtener(i);
		return null;
	}

	// Busca el retiro de una matrícula (null si no tiene)
	public Retiro buscarMatricula(int numMatricula) {
		for (int i = 0; i < tamanio(); i++)
			if (obtener(i).getNumMatricula() == numMatricula)
				return obtener(i);
		return null;
	}

	// Número correlativo: 200001 si está vacío, si no el mayor + 1
	public int numeroCorrelativo() {
		int mayor = Retiro.NUM_INICIAL - 1;
		for (int i = 0; i < tamanio(); i++)
			if (obtener(i).getNumRetiro() > mayor)
				mayor = obtener(i).getNumRetiro();
		return mayor + 1;
	}

	// ---------------------------------------------------------------
	// Archivo de texto: numRetiro;numMatricula;fecha;hora
	// ---------------------------------------------------------------
	public void grabarRetiros() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO));
			for (int i = 0; i < tamanio(); i++) {
				Retiro x = obtener(i);
				pw.println(x.getNumRetiro() + ";" + x.getNumMatricula() + ";" + x.getFecha() + ";" + x.getHora());
			}
			pw.close();
		} catch (Exception e) {
			System.out.println("Error al grabar retiros: " + e.getMessage());
		}
	}

	private void cargarRetiros() {
		try {
			File f = new File(ARCHIVO);
			if (!f.exists())
				return;
			BufferedReader br = new BufferedReader(new FileReader(f));
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty())
					continue;
				String[] s = linea.split(";");
				adicionar(new Retiro(Integer.parseInt(s[0].trim()), Integer.parseInt(s[1].trim()), s[2].trim(),
						s[3].trim()));
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Error al cargar retiros: " + e.getMessage());
		}
	}
}