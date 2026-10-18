package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import controlador.ArregloMatricula;

public class DlgMatricula extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    public static final int ADICIONAR = 0;
    public static final int CONSULTAR = 1;
    public static final int MODIFICAR = 2;
    public static final int ELIMINAR = 3;

    // Controles para los atributos de Matricula
    private JLabel lblNumMatricula;
    private JLabel lblCodAlumno;
    private JLabel lblCodCurso;
    private JLabel lblFecha;
    private JLabel lblHora;

    private JTextField txtNumMatricula;
    private JTextField txtCodAlumno;
    private JTextField txtCodCurso;
    private JTextField txtFecha;
    private JTextField txtHora;

    // Botones de acción y operación
    private JButton btnBuscar;
    private JButton btnAceptar;
    private JButton btnOpciones;
    private JButton btnAdicionar;
    private JButton btnConsultar;
    private JButton btnModificar;
    private JButton btnEliminar;

    // Tabla de matrículas
    private JScrollPane scrollPane;
    private JTable tblMatriculas;
    private DefaultTableModel modeloTabla;

    private ArregloMatricula am;
    private int tipoOperacion;




    public DlgMatricula(Frame padre, boolean modal) {
        super(padre, modal);
        am = new ArregloMatricula();

        setTitle("Registro - Matrícula");
        setBounds(100, 100, 700, 440);
        getContentPane().setLayout(null);

        lblNumMatricula = new JLabel("N° Matrícula:");
        lblNumMatricula.setBounds(20, 20, 95, 23);
        getContentPane().add(lblNumMatricula);

        txtNumMatricula = new JTextField();
        txtNumMatricula.setBounds(120, 20, 100, 23);
        txtNumMatricula.setEditable(false);
        getContentPane().add(txtNumMatricula);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(230, 20, 90, 23);
        btnBuscar.setEnabled(false);
        btnBuscar.addActionListener(this);
        getContentPane().add(btnBuscar);

        lblCodAlumno = new JLabel("Cód. Alumno:");
        lblCodAlumno.setBounds(20, 52, 95, 23);
        getContentPane().add(lblCodAlumno);

        txtCodAlumno = new JTextField();
        txtCodAlumno.setBounds(120, 52, 100, 23);
        txtCodAlumno.setEditable(false);
        getContentPane().add(txtCodAlumno);

        lblCodCurso = new JLabel("Cód. Curso:");
        lblCodCurso.setBounds(20, 84, 95, 23);
        getContentPane().add(lblCodCurso);

        txtCodCurso = new JTextField();
        txtCodCurso.setBounds(120, 84, 100, 23);
        txtCodCurso.setEditable(false);
        getContentPane().add(txtCodCurso);

        lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 116, 95, 23);
        getContentPane().add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(120, 116, 90, 23);
        txtFecha.setEditable(false);
        getContentPane().add(txtFecha);

        lblHora = new JLabel("Hora:");
        lblHora.setBounds(220, 116, 40, 23);
        getContentPane().add(lblHora);

        txtHora = new JTextField();
        txtHora.setBounds(260, 116, 80, 23);
        txtHora.setEditable(false);
        getContentPane().add(txtHora);

        // Botones de confirmación y opciones
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(355, 20, 110, 23);
        btnAceptar.setEnabled(false);
        btnAceptar.addActionListener(this);
        getContentPane().add(btnAceptar);

        btnOpciones = new JButton("Opciones");
        btnOpciones.setBounds(355, 52, 110, 87);
        btnOpciones.setEnabled(false);
        btnOpciones.addActionListener(this);
        getContentPane().add(btnOpciones);

        // Botones de operación
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(540, 20, 125, 23);
        btnAdicionar.addActionListener(this);
        getContentPane().add(btnAdicionar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(540, 52, 125, 23);
        btnConsultar.addActionListener(this);
        getContentPane().add(btnConsultar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(540, 84, 125, 23);
        btnModificar.addActionListener(this);
        getContentPane().add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(540, 116, 125, 23);
        btnEliminar.addActionListener(this);
        getContentPane().add(btnEliminar);

        // Tabla de matrículas
        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 160, 645, 220);
        getContentPane().add(scrollPane);

        tblMatriculas = new JTable();
        tblMatriculas.setFillsViewportHeight(true);
        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            new String[] { "N° Matrícula", "Cód. Alumno", "Cód. Curso", "Fecha", "Hora" }
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMatriculas.setModel(modeloTabla);
        scrollPane.setViewportView(tblMatriculas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        if (fuente == btnAdicionar) {
            prepararOperacion(ADICIONAR);
            txtNumMatricula.setText(String.valueOf(am.codigoCorrelativo()));
        } else if (fuente == btnConsultar) {
            prepararOperacion(CONSULTAR);
        } else if (fuente == btnModificar) {
            prepararOperacion(MODIFICAR);
        } else if (fuente == btnEliminar) {
            prepararOperacion(ELIMINAR);
        } else if (fuente == btnOpciones) {
            habilitarBotonesOperacion(true);
            txtNumMatricula.setEditable(false);
            txtCodAlumno.setEditable(false);
            txtCodCurso.setEditable(false);
            btnBuscar.setEnabled(false);
            btnAceptar.setEnabled(false);
            limpiarCampos();
        } else if (fuente == btnBuscar || fuente == btnAceptar) {
            JOptionPane.showMessageDialog(this,
                "La lógica de registro de matrículas se implementará en la Fase 3.",
                "Fase 2 - Interfaz Gráfica",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void prepararOperacion(int tipo) {
        this.tipoOperacion = tipo;
        limpiarCampos();
        habilitarBotonesOperacion(false);

        if (tipo == ADICIONAR) {
            txtNumMatricula.setEditable(false);
            btnBuscar.setEnabled(false);
            txtCodAlumno.setEditable(true);
            txtCodCurso.setEditable(true);
            btnAceptar.setEnabled(true);
            txtCodAlumno.requestFocus();
        } else {
            txtNumMatricula.setEditable(true);
            btnBuscar.setEnabled(true);
            txtCodAlumno.setEditable(false);
            // Según especificación, en Modificar solo se puede cambiar el curso matriculado
            txtCodCurso.setEditable(tipo == MODIFICAR);
            btnAceptar.setEnabled(tipo == ELIMINAR);
            txtNumMatricula.requestFocus();
        }
    }

    private void habilitarBotonesOperacion(boolean sino) {
        btnAdicionar.setEnabled(sino);
        btnConsultar.setEnabled(sino);
        btnModificar.setEnabled(sino);
        btnEliminar.setEnabled(sino);
        btnOpciones.setEnabled(!sino);
    }

    private void limpiarCampos() {
        txtNumMatricula.setText("");
        txtCodAlumno.setText("");
        txtCodCurso.setText("");
        txtFecha.setText("");
        txtHora.setText("");
    }
}