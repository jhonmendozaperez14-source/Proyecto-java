package controlador;

import java.util.ArrayList;
import clases.Curso;

public class ArregloCursos {

    private ArrayList<Curso> listaCursos;

    public ArregloCursos() {
        listaCursos = new ArrayList<Curso>();
        
    }

    
    public int tamanio() {
        return listaCursos.size();
    }

    
    public Curso obtener(int i) {
        return listaCursos.get(i);
    }

    
    public void adicionar(Curso c) {
        int i = 0;
        while (i < listaCursos.size() && listaCursos.get(i).getCodCurso() < c.getCodCurso()) {
            i++;
        }
        listaCursos.add(i, c);
        actualizarArchivo(); 
    }

    
    public Curso buscar(int codCurso) {
        for (int i = 0; i < listaCursos.size(); i++) {
            if (listaCursos.get(i).getCodCurso() == codCurso) {
                return listaCursos.get(i);
            }
        }
        return null;
    }

    
    public void eliminar(Curso c) {
        listaCursos.remove(c);
        actualizarArchivo();
    }

    public void actualizarArchivo() {
        
    }
}
