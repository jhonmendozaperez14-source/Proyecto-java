package clases;

public class Alumno {
<<<<<<< HEAD
 public int hora;
 public int minuto;
=======
	// Estados del alumno
	public static final int REGISTRADO = 0;
	public static final int MATRICULADO = 1;
	public static final int RETIRADO = 2;
>>>>>>> branch 'master' of https://github.com/jhonmendozaperez14-source/Proyecto-java.git

	// Atributos privados
	private int codAlumno;
	private String nombres, apellidos, dni;
	private int edad, celular, estado;

	// Constructor para un alumno nuevo: el estado siempre inicia en 0 (registrado)
	public Alumno(int codAlumno, String nombres, String apellidos, String dni, int edad, int celular) {
		this(codAlumno, nombres, apellidos, dni, edad, celular, REGISTRADO);
	}

	// Constructor completo (se usa al cargar los datos desde el archivo de texto)
	public Alumno(int codAlumno, String nombres, String apellidos, String dni, int edad, int celular, int estado) {
		this.codAlumno = codAlumno;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.edad = edad;
		this.celular = celular;
		this.estado = estado;
	}

	// Métodos de acceso público: set/get
	public int getCodAlumno() {
		return codAlumno;
	}

	public void setCodAlumno(int codAlumno) {
		this.codAlumno = codAlumno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getCelular() {
		return celular;
	}

	public void setCelular(int celular) {
		this.celular = celular;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	// Texto del estado: 0 = registrado, 1 = matriculado, 2 = retirado
	public String getEstadoTexto() {
		switch (estado) {
		case MATRICULADO:
			return "1 - Matriculado";
		case RETIRADO:
			return "2 - Retirado";
		default:
			return "0 - Registrado";
		}
	}
}