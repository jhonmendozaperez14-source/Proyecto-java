package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import clases.Alumno;

public class ArregloAlumnos {
	// Código del primer alumno; los siguientes son correlativos (202010002, ...)
	public static final int CODIGO_INICIAL = 202010001;
	private static final String ARCHIVO = "alumnos.txt";

	// Atributo privado
	private ArrayList<Alumno> alu;

	// Constructor: crea la lista y carga los datos grabados anteriormente
	public ArregloAlumnos() {
		alu = new ArrayList<Alumno>();
		cargarAlumnos();
	}

	// Operaciones públicas básicas
	public void adicionar(Alumno x) {
		alu.add(x);
	}

	public int tamanio() {
		return alu.size();
	}

	public Alumno obtener(int i) {
		return alu.get(i);
	}

	// Eliminación física: el alumno sale de la lista y, al grabar, del archivo
	public void eliminar(Alumno x) {
		alu.remove(x);
	}

	// Búsquedas
	public Alumno buscar(int codAlumno) {
		for (int i = 0; i < tamanio(); i++)
			if (obtener(i).getCodAlumno() == codAlumno)
				return obtener(i);
		return null;
	}

	public Alumno buscarDni(String dni) {
		for (int i = 0; i < tamanio(); i++)
			if (obtener(i).getDni().equals(dni))
				return obtener(i);
		return null;
	}

	// Código correlativo: el mayor código existente + 1 (o 202010001 si no hay alumnos)
	public int codigoCorrelativo() {
		int mayor = CODIGO_INICIAL - 1;
		for (int i = 0; i < tamanio(); i++)
			if (obtener(i).getCodAlumno() > mayor)
				mayor = obtener(i).getCodAlumno();
		return mayor + 1;
	}

	// Grabar: reescribe el archivo completo con los alumnos que existen en la lista
	public void grabarAlumnos() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO));
			for (int i = 0; i < tamanio(); i++) {
				Alumno x = obtener(i);
				pw.println(x.getCodAlumno() + ";" + x.getNombres() + ";" + x.getApellidos() + ";" + x.getDni() + ";"
						+ x.getEdad() + ";" + x.getCelular() + ";" + x.getEstado());
			}
			pw.close();
		} catch (Exception e) {
			System.out.println("Error al grabar alumnos: " + e.getMessage());
		}
	}

	// Cargar: lee el archivo (si existe) y llena la lista
	private void cargarAlumnos() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty())
					continue;
				String[] s = linea.split(";");
				int codAlumno = Integer.parseInt(s[0].trim());
				String nombres = s[1].trim();
				String apellidos = s[2].trim();
				String dni = s[3].trim();
				int edad = Integer.parseInt(s[4].trim());
				int celular = Integer.parseInt(s[5].trim());
				int estado = Integer.parseInt(s[6].trim());
				adicionar(new Alumno(codAlumno, nombres, apellidos, dni, edad, celular, estado));
			}
			br.close();
		} catch (Exception e) {
			// Si el archivo todavía no existe, la lista simplemente inicia vacía
		}
	}
}