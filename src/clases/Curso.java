package clases;

public class Curso {
    
    private int codCurso;
    private String asignatura;
    private int ciclo; 
    private int créditos;
    private int horas;

    public Curso(int codCurso, String asignatura, int ciclo, int créditos, int horas) {
        this.codCurso = codCurso;
        this.asignatura = asignatura;
        this.ciclo = ciclo;
        this.créditos = créditos;
        this.horas = horas;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public int getCréditos() {
        return créditos;
    }

    public void setCréditos(int créditos) {
        this.créditos = créditos;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
}