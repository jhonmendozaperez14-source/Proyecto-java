package clases;

// Clase Retiro (Registro | Retiro)
public class Retiro {
	// Constante de clase: el primer número de retiro es 200001
	public static final int NUM_INICIAL = 200001;

	// Atributos privados
	private int numRetiro, numMatricula;
	private String fecha, hora; // fecha "dd/mm/aaaa" y hora "hh:mm:ss"

	// Constructor
	public Retiro(int numRetiro, int numMatricula, String fecha, String hora) {
		this.numRetiro = numRetiro;
		this.numMatricula = numMatricula;
		this.fecha = fecha;
		this.hora = hora;
	}

	// Métodos de acceso público: set/get
	public int getNumRetiro() {
		return numRetiro;
	}

	public void setNumRetiro(int numRetiro) {
		this.numRetiro = numRetiro;
	}

	public int getNumMatricula() {
		return numMatricula;
	}

	public void setNumMatricula(int numMatricula) {
		this.numMatricula = numMatricula;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}