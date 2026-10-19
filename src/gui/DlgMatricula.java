
package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import controlador.ArregloAlumnos;
import controlador.ArregloCursos;
import controlador.ArregloMatricula;

public class DlgMatricula extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    public static final int ADICIONAR = 0, CONSULTAR = 1,
            MODIFICAR = 2, ELIMINAR = 3;

    private JTextField txtNumMatricula, txtCodAlumno,
            txtCodCurso, txtFecha, txtHora;

    private JButton btnBuscar, btnAceptar, btnOpciones,
            btnAdicionar, btnConsultar, btnModificar, btnEliminar;

    private JTable tblMatriculas;
    private DefaultTableModel modeloTabla;

    private ArregloMatricula am;
    private ArregloAlumnos aa;
    private ArregloCursos ac;

    private int tipoOperacion;

    public DlgMatricula(Frame padre, boolean modal) {
        super(padre, modal);

        am = new ArregloMatricula();
        aa = new ArregloAlumnos();
        ac = new ArregloCursos();

        setTitle("Registro - Matrícula");
        setBounds(100, 100, 720, 450);
        setLocationRelativeTo(padre);
        getContentPane().setLayout(null);

        agregarEtiqueta("N° Matrícula:", 20, 20);
        txtNumMatricula = campo(120, 20, 100);
        txtNumMatricula.setEditable(false);

        btnBuscar = boton("Buscar", 230, 20, 90, 23);
        btnBuscar.setEnabled(false);

        agregarEtiqueta("Cód. Alumno:", 20, 52);
        txtCodAlumno = campo(120, 52, 100);

        agregarEtiqueta("Cód. Curso:", 20, 84);
        txtCodCurso = campo(120, 84, 100);

        agregarEtiqueta("Fecha:", 20, 116);
        txtFecha = campo(120, 116, 90);
        txtFecha.setEditable(false);

        agregarEtiqueta("Hora:", 220, 116);
        txtHora = campo(260, 116, 80);
        txtHora.setEditable(false);

        btnAceptar = boton("Aceptar", 355, 20, 110, 23);
        btnAceptar.setEnabled(false);

        btnOpciones = boton(
                "Cancelar operación", 355, 52, 145, 30);
        btnOpciones.setEnabled(false);

        btnAdicionar = boton("Adicionar", 550, 20, 125, 23);
        btnConsultar = boton("Consultar", 550, 52, 125, 23);
        btnModificar = boton("Modificar", 550, 84, 125, 23);
        btnEliminar = boton("Eliminar", 550, 116, 125, 23);

        JScrollPane sp = new JScrollPane();
        sp.setBounds(20, 160, 655, 230);
        getContentPane().add(sp);

        modeloTabla = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                    "N° Matrícula", "Cód. Alumno",
                    "Cód. Curso", "Fecha", "Hora"
                }) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblMatriculas = new JTable(modeloTabla);
        sp.setViewportView(tblMatriculas);

        listar();
    }

    private void agregarEtiqueta(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, 100, 23);
        getContentPane().add(l);
    }

    private JTextField campo(int x, int y, int w) {
        JTextField t = new JTextField();
        t.setBounds(x, y, w, 23);
        getContentPane().add(t);
        return t;
    }

    private JButton boton(
            String texto, int x, int y, int w, int h) {
        JButton b = new JButton(texto);
        b.setBounds(x, y, w, h);
        b.addActionListener(this);
        getContentPane().add(b);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == btnAdicionar) {
            prepararOperacion(ADICIONAR);
            txtNumMatricula.setText(
                    String.valueOf(am.codigoCorrelativo()));

        } else if (fuente == btnConsultar) {
            prepararOperacion(CONSULTAR);

        } else if (fuente == btnModificar) {
            prepararOperacion(MODIFICAR);

        } else if (fuente == btnEliminar) {
            prepararOperacion(ELIMINAR);

        } else if (fuente == btnOpciones) {
            limpiarCampos();
            habilitarBotonesOperacion(true);

        } else if (fuente == btnBuscar) {
            buscarMatricula();

        } else if (fuente == btnAceptar) {
            procesarOperacion();
        }
    }

    private void prepararOperacion(int tipo) {
        tipoOperacion = tipo;
        limpiarCampos();
        habilitarBotonesOperacion(false);

        if (tipo == ADICIONAR) {
            txtNumMatricula.setText(
                    String.valueOf(am.codigoCorrelativo()));
            txtCodAlumno.setEditable(true);
            txtCodCurso.setEditable(true);
            btnAceptar.setEnabled(true);

        } else {
            txtNumMatricula.setEditable(true);
            btnBuscar.setEnabled(true);
            txtCodAlumno.setEditable(false);
            txtCodCurso.setEditable(false);
            btnAceptar.setEnabled(tipo == ELIMINAR);
        }

        txtNumMatricula.requestFocus();
    }

    private void habilitarBotonesOperacion(boolean si) {
        btnAdicionar.setEnabled(si);
        btnConsultar.setEnabled(si);
        btnModificar.setEnabled(si);
        btnEliminar.setEnabled(si);

        btnOpciones.setEnabled(!si);
        btnBuscar.setEnabled(false);
        btnAceptar.setEnabled(false);
    }

    private void buscarMatricula() {
        try {
            int numero = entero(
                    txtNumMatricula.getText(),
                    "número de matrícula");

            Matricula m = am.buscar(numero);

            if (m == null) {
                mensajeError("No existe la matrícula indicada.");
                return;
            }

            mostrar(m);

            btnAceptar.setEnabled(
                    tipoOperacion == ELIMINAR ||
                    tipoOperacion == MODIFICAR);

            if (tipoOperacion == CONSULTAR) {
                btnAceptar.setEnabled(false);
            }

        } catch (Exception ex) {
            mensajeError(ex.getMessage());
        }
    }

    private void mostrar(Matricula m) {
        txtNumMatricula.setText(
                "" + m.getNumMatricula());
        txtCodAlumno.setText(
                "" + m.getCodAlumno());
        txtCodCurso.setText(
                "" + m.getCodCurso());
        txtFecha.setText(m.getFecha());
        txtHora.setText(m.getHora());

        txtCodCurso.setEditable(
                tipoOperacion == MODIFICAR);
    }

    private void procesarOperacion() {
        try {
            if (tipoOperacion == ADICIONAR) {

                int codAlumno = entero(
                        txtCodAlumno.getText(), "código de alumno");
                int codCurso = entero(
                        txtCodCurso.getText(), "código de curso");

                Alumno a = aa.buscar(codAlumno);
                Curso c = ac.buscar(codCurso);

                if (a == null) {
                    throw new Exception("El alumno no existe.");
                }

                if (c == null) {
                    throw new Exception("El curso no existe.");
                }

                if (a.getEstado() != Alumno.REGISTRADO) {
                    throw new Exception(
                            "Solo se puede matricular a un alumno "
                            + "con estado 0 (registrado).");
                }

                if (am.buscarPorAlumno(codAlumno) != null) {
                    throw new Exception(
                            "El alumno ya tiene una matrícula registrada.");
                }

                Matricula m = new Matricula(
                        am.codigoCorrelativo(),
                        codAlumno,
                        codCurso,
                        fechaSistema(),
                        horaSistema());

                am.adicionar(m);
                a.setEstado(Alumno.MATRICULADO);
                aa.grabarAlumnos();

                mostrar(m);
                mensaje("Matrícula registrada correctamente.");

            } else {
                int numero = entero(
                        txtNumMatricula.getText(),
                        "número de matrícula");

                Matricula m = am.buscar(numero);

                if (m == null) {
                    throw new Exception(
                            "No existe la matrícula indicada.");
                }

                if (tipoOperacion == MODIFICAR) {
                    int codCurso = entero(
                            txtCodCurso.getText(), "código de curso");

                    if (ac.buscar(codCurso) == null) {
                        throw new Exception(
                                "El curso indicado no existe.");
                    }

                    Alumno a = aa.buscar(m.getCodAlumno());

                    if (a == null ||
                            a.getEstado() == Alumno.RETIRADO) {
                        throw new Exception(
                                "No se puede modificar una matrícula retirada.");
                    }

                    m.setCodCurso(codCurso);
                    am.grabarMatriculas();
                    mostrar(m);

                    mensaje("Curso de la matrícula actualizado.");

                } else if (tipoOperacion == ELIMINAR) {
                    Alumno a = aa.buscar(m.getCodAlumno());

                    if (a != null &&
                            a.getEstado() == Alumno.RETIRADO) {
                        throw new Exception(
                                "No se puede cancelar una matrícula "
                                + "cuyo alumno está retirado.");
                    }

                    int r = JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea cancelar físicamente la matrícula "
                            + numero + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (r == JOptionPane.YES_OPTION) {
                        am.eliminar(m);

                        if (a != null) {
                            a.setEstado(Alumno.REGISTRADO);
                            aa.grabarAlumnos();
                        }

                        limpiarCampos();
                        mensaje("Matrícula cancelada.");
                    }
                }
            }

            listar();

        } catch (Exception ex) {
            mensajeError(ex.getMessage());
        }
    }

    private int entero(String valor, String campo)
            throws Exception {
        try {
            return Integer.parseInt(valor.trim());
        } catch (Exception e) {
            throw new Exception(
                    "Ingrese un valor numérico válido para " + campo + ".");
        }
    }

    private String fechaSistema() {
        return LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String horaSistema() {
        return LocalTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private void listar() {
        modeloTabla.setRowCount(0);

        for (int i = 0; i < am.tamanio(); i++) {
            Matricula m = am.obtener(i);

            modeloTabla.addRow(new Object[] {
                m.getNumMatricula(),
                m.getCodAlumno(),
                m.getCodCurso(),
                m.getFecha(),
                m.getHora()
            });
        }
    }

    private void limpiarCampos() {
        txtNumMatricula.setText("");
        txtCodAlumno.setText("");
        txtCodCurso.setText("");
        txtFecha.setText("");
        txtHora.setText("");

        txtNumMatricula.setEditable(false);
        txtCodAlumno.setEditable(false);
        txtCodCurso.setEditable(false);

        btnAceptar.setEnabled(false);
        btnBuscar.setEnabled(false);
    }

    private void mensaje(String s) {
        JOptionPane.showMessageDialog(
                this, s, "Matrícula",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mensajeError(String s) {
        JOptionPane.showMessageDialog(
                this, s, "Validación",
                JOptionPane.WARNING_MESSAGE);
    }
}
