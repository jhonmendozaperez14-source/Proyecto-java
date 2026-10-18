package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import clases.Matricula;

public class ArregloMatricula {

    public static final int CORRELATIVO_INICIAL = 100001;

    private ArrayList<Matricula> listaMatriculas;

    public ArregloMatricula() {
        listaMatriculas = new ArrayList<Matricula>();
        cargarMatriculas();
    }

    public int tamanio() {
        return listaMatriculas.size();
    }

    public Matricula obtener(int i) {
        return listaMatriculas.get(i);
    }

    public void adicionar(Matricula x) {
        listaMatriculas.add(x);
        grabarMatriculas();
    }

    public void eliminar(Matricula x) {
        listaMatriculas.remove(x);
        grabarMatriculas();
    }

    public Matricula buscar(int numMatricula) {
        for (int i = 0; i < listaMatriculas.size(); i++) {
            if (listaMatriculas.get(i).getNumMatricula() == numMatricula) {
                return listaMatriculas.get(i);
            }
        }
        return null;
    }

    public Matricula buscarPorAlumno(int codAlumno) {
        for (int i = 0; i < listaMatriculas.size(); i++) {
            if (listaMatriculas.get(i).getCodAlumno() == codAlumno) {
                return listaMatriculas.get(i);
            }
        }
        return null;
    }

    public boolean existeCursoMatriculado(int codCurso) {
        for (int i = 0; i < listaMatriculas.size(); i++) {
            if (listaMatriculas.get(i).getCodCurso() == codCurso) {
                return true;
            }
        }
        return false;
    }

    public int codigoCorrelativo() {
        int mayor = CORRELATIVO_INICIAL - 1;
        for (int i = 0; i < listaMatriculas.size(); i++) {
            if (listaMatriculas.get(i).getNumMatricula() > mayor) {
                mayor = listaMatriculas.get(i).getNumMatricula();
            }
        }
        return mayor + 1;
    }

    public ArrayList<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }

    public void grabarMatriculas() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("matriculas.txt"));
            for (int i = 0; i < tamanio(); i++) {
                Matricula m = obtener(i);
                pw.println(m.getNumMatricula() + ";" +
                           m.getCodAlumno() + ";" +
                           m.getCodCurso() + ";" +
                           m.getFecha() + ";" +
                           m.getHora());
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarMatriculas() {
        try {
            File archivo = new File("matriculas.txt");
            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] s = linea.split(";");
                    if (s.length == 5) {
                        int numMatricula = Integer.parseInt(s[0].trim());
                        int codAlumno = Integer.parseInt(s[1].trim());
                        int codCurso = Integer.parseInt(s[2].trim());
                        String fecha = s[3].trim();
                        String hora = s[4].trim();

                        Matricula m = new Matricula(numMatricula, codAlumno, codCurso, fecha, hora);
                        listaMatriculas.add(m);
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}