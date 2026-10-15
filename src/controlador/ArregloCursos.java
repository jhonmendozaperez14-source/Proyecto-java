package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import clases.Curso;

public class ArregloCursos {

    private ArrayList<Curso> listaCursos;

    public ArregloCursos() {
        listaCursos = new ArrayList<Curso>();
        cargarCursos();
    }

    public int tamanio() {
        return listaCursos.size();
    }

    public Curso obtener(int i) {
        return listaCursos.get(i);
    }

    public void adicionar(Curso x) {
        int i = 0;
        while (i < listaCursos.size() && listaCursos.get(i).getCodCurso() < x.getCodCurso()) {
            i++;
        }
        listaCursos.add(i, x);
        grabarCursos();
    }

    public void eliminar(Curso x) {
        listaCursos.remove(x);
        grabarCursos();
    }

    public Curso buscar(int codCurso) {
        for (int i = 0; i < listaCursos.size(); i++) {
            if (listaCursos.get(i).getCodCurso() == codCurso) {
                return listaCursos.get(i);
            }
        }
        return null;
    }

    public void grabarCursos() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("cursos.txt"));
            for (int i = 0; i < tamanio(); i++) {
                Curso c = obtener(i);
                pw.println(c.getCodCurso() + ";" + 
                           c.getAsignatura() + ";" + 
                           c.getCiclo() + ";" + 
                           c.getCreditos() + ";" + 
                           c.getHoras());
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarCursos() {
        try {
            File archivo = new File("cursos.txt");
            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] s = linea.split(";");
                    if (s.length == 5) {
                        int cod = Integer.parseInt(s[0].trim());
                        String asignatura = s[1].trim();
                        int ciclo = Integer.parseInt(s[2].trim());
                        int creditos = Integer.parseInt(s[3].trim());
                        int horas = Integer.parseInt(s[4].trim());
                        
                        Curso c = new Curso(cod, asignatura, ciclo, creditos, horas);
                        listaCursos.add(c);
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}