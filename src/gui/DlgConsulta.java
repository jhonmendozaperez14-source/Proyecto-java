
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import clases.Retiro;

import controlador.ArregloAlumnos;
import controlador.ArregloCursos;
import controlador.ArregloMatricula;
import controlador.ArregloRetiros;

public class DlgConsulta extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> cboTipo;
    private JTextField txtCodigo;
    private JTextArea txtResultado;

    private JButton btnBuscar;
    private JButton btnCerrar;

    private ArregloAlumnos aa = new ArregloAlumnos();
    private ArregloCursos ac = new ArregloCursos();
    private ArregloMatricula am = new ArregloMatricula();
    private ArregloRetiros ar = new ArregloRetiros();

    public DlgConsulta() {

        setTitle("Consulta de información");
        setBounds(100, 100, 650, 430);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout(8, 8));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(
                new FlowLayout(FlowLayout.LEFT));

        panelSuperior.add(new JLabel("Tipo de consulta:"));

        cboTipo = new JComboBox<String>();

        cboTipo.addItem("Alumno por código");
        cboTipo.addItem("Curso por código");
        cboTipo.addItem("Matrícula por número");
        cboTipo.addItem("Retiro por número");

        panelSuperior.add(cboTipo);

        panelSuperior.add(new JLabel("Código / número:"));

        txtCodigo = new JTextField(12);
        panelSuperior.add(txtCodigo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        panelSuperior.add(btnBuscar);

        getContentPane().add(
                panelSuperior, BorderLayout.NORTH);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(txtResultado);

        getContentPane().add(
                scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(this);

        panelInferior.add(btnCerrar);

        getContentPane().add(
                panelInferior, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnBuscar) {
            actionPerformedBtnBuscar(e);
        }

        if (e.getSource() == btnCerrar) {
            dispose();
        }
    }

    protected void actionPerformedBtnBuscar(ActionEvent e) {

        txtResultado.setText("");

        try {

            int codigo = Integer.parseInt(
                    txtCodigo.getText().trim());

            if (cboTipo.getSelectedIndex() == 0) {
                consultarAlumno(codigo);
            }

            if (cboTipo.getSelectedIndex() == 1) {
                consultarCurso(codigo);
            }

            if (cboTipo.getSelectedIndex() == 2) {
                consultarMatricula(codigo);
            }

            if (cboTipo.getSelectedIndex() == 3) {
                consultarRetiro(codigo);
            }

            if (txtResultado.getText().isEmpty()) {
                imprimir(
                        "No se encontró información para el código ingresado.");
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un código o número válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void consultarAlumno(int codigo) {

        Alumno a = aa.buscar(codigo);

        if (a == null) {
            return;
        }

        imprimir("========================================");
        imprimir("             DATOS DEL ALUMNO");
        imprimir("========================================");

        imprimir("Código    : " + a.getCodAlumno());
        imprimir("Nombres   : " + a.getNombres());
        imprimir("Apellidos : " + a.getApellidos());
        imprimir("DNI       : " + a.getDni());
        imprimir("Edad      : " + a.getEdad());
        imprimir("Celular   : " + a.getCelular());
        imprimir("Estado    : " + a.getEstadoTexto());

        if (a.getEstado() == Alumno.MATRICULADO
                || a.getEstado() == Alumno.RETIRADO) {

            Matricula m = am.buscarPorAlumno(codigo);

            if (m != null) {
                imprimir();
                imprimir("          CURSO DE LA MATRÍCULA");
                agregarCurso(ac.buscar(m.getCodCurso()));
            }
        }
    }

    private void consultarCurso(int codigo) {

        Curso c = ac.buscar(codigo);

        if (c == null) {
            return;
        }

        imprimir("========================================");
        imprimir("             DATOS DEL CURSO");
        imprimir("========================================");

        agregarCurso(c);
    }

    private void consultarMatricula(int numero) {

        Matricula m = am.buscar(numero);

        if (m == null) {
            return;
        }

        imprimir("========================================");
        imprimir("          DATOS DE LA MATRÍCULA");
        imprimir("========================================");

        imprimir("Número : " + m.getNumMatricula());
        imprimir("Fecha  : " + m.getFecha());
        imprimir("Hora   : " + m.getHora());

        Alumno a = aa.buscar(m.getCodAlumno());

        if (a != null) {

            imprimir();
            imprimir("                ALUMNO");

            imprimir("Código    : " + a.getCodAlumno());
            imprimir("Nombres   : "
                    + a.getNombres() + " " + a.getApellidos());
            imprimir("DNI       : " + a.getDni());
            imprimir("Estado    : " + a.getEstadoTexto());
        }

        Curso c = ac.buscar(m.getCodCurso());

        if (c != null) {

            imprimir();
            imprimir("                CURSO");

            agregarCurso(c);
        }
    }

    private void consultarRetiro(int numero) {

        Retiro r = ar.buscar(numero);

        if (r == null) {
            return;
        }

        imprimir("========================================");
        imprimir("             DATOS DEL RETIRO");
        imprimir("========================================");

        imprimir("N° Retiro    : " + r.getNumRetiro());
        imprimir("N° Matrícula : " + r.getNumMatricula());
        imprimir("Fecha        : " + r.getFecha());
        imprimir("Hora         : " + r.getHora());

        Matricula m = am.buscar(r.getNumMatricula());

        if (m != null) {

            imprimir();
            imprimir("          ALUMNO Y CURSO RELACIONADOS");

            Alumno a = aa.buscar(m.getCodAlumno());

            if (a != null) {

                imprimir("Alumno : "
                        + a.getNombres() + " " + a.getApellidos());

                imprimir("Código : " + a.getCodAlumno());
                imprimir("Estado : " + a.getEstadoTexto());
            }

            Curso c = ac.buscar(m.getCodCurso());

            if (c != null) {
                agregarCurso(c);
            }
        }
    }

    private void agregarCurso(Curso c) {

        if (c == null) {
            imprimir("Curso no encontrado.");
            return;
        }

        imprimir("Código     : " + c.getCodCurso());
        imprimir("Asignatura : " + c.getAsignatura());
        imprimir("Ciclo      : " + (c.getCiclo() + 1));
        imprimir("Créditos   : " + c.getCreditos());
        imprimir("Horas      : " + c.getHoras());
    }

    private void imprimir() {
        txtResultado.append("\n");
    }

    private void imprimir(String s) {
        txtResultado.append(s + "\n");
    }
}
