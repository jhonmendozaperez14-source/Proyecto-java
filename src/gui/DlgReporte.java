package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import clases.Alumno;
import controlador.ArregloAlumnos;

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

        JButton btnAlumnos = new JButton("Alumnos");
        JButton btnCursos = new JButton("Cursos");
        JButton btnMatriculas = new JButton("Matriculas");
        JButton btnRetiros = new JButton("Retiros");

        contentPanel.add(btnAlumnos);
        contentPanel.add(btnCursos);
        contentPanel.add(btnMatriculas);
        contentPanel.add(btnRetiros);

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtSalida);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        btnAlumnos.addActionListener(e -> reporteAlumnos());
        btnCursos.addActionListener(e -> reporteCursos());
        btnMatriculas.addActionListener(e -> reporteMatriculas());
        btnRetiros.addActionListener(e -> reporteRetiros());
    }

    private void reporteAlumnos() {	
    	ArregloAlumnos aa = new ArregloAlumnos();

    	int registrados = 0;
    	int matriculados = 0;
    	int retirados = 0;

    	txtSalida.setText("");

    	imprimir("========================================");
    	imprimir("         REPORTE DE ALUMNOS");
    	imprimir("========================================");
    	imprimir();

    	for(int i = 0; i < aa.tamanio(); i++) {

    	    Alumno a = aa.obtener(i);

    	    if(a.getEstado() == Alumno.REGISTRADO)
    	        registrados++;

    	    if(a.getEstado() == Alumno.MATRICULADO)
    	        matriculados++;

    	    if(a.getEstado() == Alumno.RETIRADO)
    	        retirados++;

    	    imprimir("Código    : " + a.getCodAlumno());
    	    imprimir("Nombres   : " + a.getNombres());
    	    imprimir("Apellidos : " + a.getApellidos());
    	    imprimir("Estado    : " + a.getEstadoTexto());
    	    imprimir("----------------------------------------");
    	}

    	imprimir();
    	imprimir("RESUMEN GENERAL");
    	imprimir("----------------------------------------");
    	imprimir("Cantidad total de alumnos : " + aa.tamanio());
    	imprimir("Alumnos registrados       : " + registrados);
    	imprimir("Alumnos matriculados      : " + matriculados);
    	imprimir("Alumnos retirados         : " + retirados);
    	imprimir("========================================");
    	}
    

    private void reporteCursos() {
        txtSalida.setText(
                "REPORTE DE CURSOS\n\n"
              + "Aquí aparecerán los cursos registrados");
    }

    private void reporteMatriculas() {
        txtSalida.setText(
                "REPORTE DE MATRÍCULAS\n\n"
              + "Aquí aparecerán las matrículas registradas");
    }

    private void reporteRetiros() {
        txtSalida.setText(
                "REPORTE DE RETIROS\n\n"
              + "Aquí aparecerán los retiros registrados");
    }
    
    //clase imprimir
    private void imprimir() {
        txtSalida.append("\n");
    }

    private void imprimir(String s) {
        txtSalida.append(s + "\n");
    }
    
}
