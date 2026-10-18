package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import controlador.ArregloAlumnos;
import controlador.ArregloCursos;
import controlador.ArregloMatricula;

public class DlgReporte extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JTextArea txtSalida;

    public static void main(String[] args) {
        try {
            DlgReporte dialog = new DlgReporte();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DlgReporte() {
        setTitle("Reportes");
        setBounds(100, 100, 700, 500);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        JButton btnPendientes = new JButton("Matrícula pendiente");
        JButton btnVigentes = new JButton("Matrícula vigente");
        JButton btnPorCurso = new JButton("Matriculados por curso");

        contentPanel.add(btnPendientes);
        contentPanel.add(btnVigentes);
        contentPanel.add(btnPorCurso);

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        getContentPane().add(new JScrollPane(txtSalida), BorderLayout.CENTER);

        btnPendientes.addActionListener(e -> reportePendientes());
        btnVigentes.addActionListener(e -> reporteVigentes());
        btnPorCurso.addActionListener(e -> reportePorCurso());
    }

    //Cada botón llama a un método que hace el trabajo
    
    private void reportePendientes() {
        listarAlumnosPorEstado(Alumno.REGISTRADO, "ALUMNOS CON MATRÍCULA PENDIENTE");
    }

    private void reporteVigentes() {
        listarAlumnosPorEstado(Alumno.MATRICULADO, "ALUMNOS CON MATRÍCULA VIGENTE");
    }

    private void listarAlumnosPorEstado(int estado, String titulo) {
        ArregloAlumnos aa = new ArregloAlumnos();

        txtSalida.setText("");
        imprimir(titulo);
        imprimir("========================================");

        //imprime los alumnos con el estado pedido
        
        int cont = 0;
        for (int i = 0; i < aa.tamanio(); i++) {
            Alumno a = aa.obtener(i);
            
            if (a.getEstado() == estado) {
                imprimir("Código    : " + a.getCodAlumno());
                imprimir("Nombres   : " + a.getNombres());
                imprimir("Apellidos : " + a.getApellidos());
                imprimir("DNI       : " + a.getDni());
                imprimir("Edad      : " + a.getEdad());
                imprimir("Celular   : " + a.getCelular());
                imprimir("Estado    : " + a.getEstadoTexto());
                imprimir("----------------------------------------");
                //cuenta cuantos salieron
                cont++;
            }
        }
        if (cont == 0) imprimir("No hay alumnos en este estado.");
    }

    private void reportePorCurso() {
        ArregloCursos ac = new ArregloCursos();
        ArregloMatricula am = new ArregloMatricula();
        ArregloAlumnos aa = new ArregloAlumnos();

        txtSalida.setText("");
        imprimir("ALUMNOS MATRICULADOS POR CURSO");
        imprimir("========================================");

        for (int i = 0; i < ac.tamanio(); i++) {
            Curso c = ac.obtener(i);
            imprimir("CURSO: " + c.getCodCurso() + " - " + c.getAsignatura());

            int cont = 0;
            for (int j = 0; j < am.tamanio(); j++) {
                Matricula m = am.obtener(j);
                if (m.getCodCurso() == c.getCodCurso()) {
                    Alumno a = aa.buscar(m.getCodAlumno());
                    if (a != null && a.getEstado() == Alumno.MATRICULADO) {
                        imprimir("   - " + a.getNombres() + " " + a.getApellidos());
                        cont++;
                    }
                }
            }
            if (cont == 0) imprimir("   (sin alumnos matriculados)");
            imprimir("----------------------------------------");
        }
    }

    private void imprimir() {
        txtSalida.append("\n");
    }

    private void imprimir(String s) {
        txtSalida.append(s + "\n");
    }
}