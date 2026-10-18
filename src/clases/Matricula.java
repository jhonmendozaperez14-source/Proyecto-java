package clases;

public class Matricula {
	
	public static final int NUM_INICIAL = 100001;

    // Atributos privados segÃºn el documento oficial
    private int numMatricula; // Correlativo que inicia en 100001
    private int codAlumno;
    private int codCurso;
    private String fecha;     // Formato "dd/mm/aaaa"
    private String hora;      // Formato "hh:mm:ss"

    // Constructor completo
    public Matricula(int numMatricula, int codAlumno, int codCurso, String fecha, String hora) {
        this.numMatricula = numMatricula;
        this.codAlumno = codAlumno;
        this.codCurso = codCurso;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor por defecto
    public Matricula() {
    }

    // MÃ©todos Getters y Setters
    public int getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    public int getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
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