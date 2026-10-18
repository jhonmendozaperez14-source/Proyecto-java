package controlador;

import java.util.ArrayList;
import modelo.Matricula;

public class ArregloMatricula {

    // Constante para el código correlativo inicial según especificación del PDF
    public static final int CORRELATIVO_INICIAL = 100001;

    // Atributo privado que gestiona el ArrayList de matrículas en memoria
    private ArrayList<Matricula> mat;

    // Constructor que inicializa el ArrayList
    public ArregloMatricula() {
        mat = new ArrayList<Matricula>();
    }

    // Operaciones básicas de gestión del ArrayList (estándar CIBERTEC)
    public void adicionar(Matricula x) {
        mat.add(x);
    }

    public int tamanio() {
        return mat.size();
    }

    public Matricula obtener(int i) {
        return mat.get(i);
    }

    public void eliminar(Matricula x) {
        mat.remove(x);
    }

    // Busca una matrícula por su número correlativo (numMatricula)
    public Matricula buscar(int numMatricula) {
        for (int i = 0; i < tamanio(); i++) {
            if (obtener(i).getNumMatricula() == numMatricula) {
                return obtener(i);
            }
        }
        return null;
    }

    // Busca una matrícula por código de alumno (para validar matrícula única por alumno)
    public Matricula buscarPorAlumno(int codAlumno) {
        for (int i = 0; i < tamanio(); i++) {
            if (obtener(i).getCodAlumno() == codAlumno) {
                return obtener(i);
            }
        }
        return null;
    }

    // Verifica si algún alumno está matriculado en un curso específico
    public boolean existeCursoMatriculado(int codCurso) {
        for (int i = 0; i < tamanio(); i++) {
            if (obtener(i).getCodCurso() == codCurso) {
                return true;
            }
        }
        return false;
    }

    // Genera el código correlativo autogenerado (inicia en 100001)
    public int codigoCorrelativo() {
        if (tamanio() == 0) {
            return CORRELATIVO_INICIAL;
        }
        return obtener(tamanio() - 1).getNumMatricula() + 1;
    }

    // Método de acceso a la lista completa
    public ArrayList<Matricula> getListaMatriculas() {
        return mat;
    }
}