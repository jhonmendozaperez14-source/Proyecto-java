package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import clases.Retiro;
import controlador.ArregloAlumnos;
import controlador.ArregloCursos;
import controlador.ArregloMatriculas;
import controlador.ArregloRetiros;

// Registro | Retiro
// Muestra las matrículas con el estado del alumno (en curso / retirado).
// Desde la misma ventana se retira al alumno, se cambia el curso de una
// matrícula desactivada o se cancela el retiro.
public class DlgRetiro extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Estados del alumno
	private static final int MATRICULADO = 1;
	private static final int RETIRADO = 2;

	// Filtros de la tabla
	private static final int TODOS = 0;
	private static final int EN_CURSO = 1;
	private static final int RETIRADOS = 2;

	private JLabel lblFiltro, lblNumMatricula, lblAlumno, lblCurso, lblEstado, lblNumRetiro, lblFecha, lblHora;
	private JTextField txtNumMatricula, txtAlumno, txtCodCurso, txtAsignatura, txtEstado, txtNumRetiro, txtFecha,
			txtHora;
	private JComboBox<String> cboFiltro;
	private JButton btnRetirar, btnCambiarCurso, btnCancelarRetiro, btnCerrar;
	private JScrollPane scpScroll;
	private JTable tblMatriculas;
	private DefaultTableModel modelo;

	// Arreglos (cada uno carga su archivo .txt al crearse)
	private ArregloRetiros ar = new ArregloRetiros();
	private ArregloMatriculas am = new ArregloMatriculas();
	private ArregloAlumnos aa = new ArregloAlumnos();
	private ArregloCursos ac = new ArregloCursos();

	// Constructor sin parámetros
	public DlgRetiro() {
		this(null);
	}

	// Constructor que recibe la ventana principal (FrmPrincipal)
	public DlgRetiro(JFrame padre) {
		super(padre, "Registro | Retiro", true);
		setBounds(100, 100, 850, 600);
		setResizable(false);
		setLocationRelativeTo(padre);
		getContentPane().setLayout(null);

		// Al salir de este apartado se graban los cambios en los .txt
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ar.grabarRetiros();
				aa.grabarAlumnos(); // el estado del alumno cambia con el retiro
				am.grabarMatriculas(); // el curso de la matrícula puede cambiar
			}
		});

		// Filtro de la tabla
		lblFiltro = new JLabel("Mostrar");
		lblFiltro.setBounds(20, 20, 100, 25);
		getContentPane().add(lblFiltro);

		cboFiltro = new JComboBox<String>(new String[] { "Todos", "En curso (matriculados)", "Retirados" });
		cboFiltro.addActionListener(this);
		cboFiltro.setBounds(130, 20, 200, 25);
		getContentPane().add(cboFiltro);

		// Datos de la matrícula elegida
		lblNumMatricula = new JLabel("N.º matrícula");
		lblNumMatricula.setBounds(20, 60, 100, 25);
		getContentPane().add(lblNumMatricula);

		txtNumMatricula = new JTextField();
		txtNumMatricula.setEditable(false);
		txtNumMatricula.setBounds(130, 60, 120, 25);
		getContentPane().add(txtNumMatricula);

		lblAlumno = new JLabel("Alumno");
		lblAlumno.setBounds(20, 92, 100, 25);
		getContentPane().add(lblAlumno);

		txtAlumno = new JTextField();
		txtAlumno.setEditable(false);
		txtAlumno.setBounds(130, 92, 280, 25);
		getContentPane().add(txtAlumno);

		lblCurso = new JLabel("Curso");
		lblCurso.setBounds(20, 124, 100, 25);
		getContentPane().add(lblCurso);

		txtCodCurso = new JTextField();
		txtCodCurso.setEditable(false); // solo se edita si el alumno está retirado
		txtCodCurso.setBounds(130, 124, 120, 25);
		getContentPane().add(txtCodCurso);

		txtAsignatura = new JTextField();
		txtAsignatura.setEditable(false);
		txtAsignatura.setBounds(260, 124, 280, 25);
		getContentPane().add(txtAsignatura);

		lblEstado = new JLabel("Estado alumno");
		lblEstado.setBounds(20, 156, 100, 25);
		getContentPane().add(lblEstado);

		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setBounds(130, 156, 120, 25);
		getContentPane().add(txtEstado);

		// Datos del retiro
		lblNumRetiro = new JLabel("N.º retiro");
		lblNumRetiro.setBounds(20, 196, 100, 25);
		getContentPane().add(lblNumRetiro);

		txtNumRetiro = new JTextField();
		txtNumRetiro.setEditable(false);
		txtNumRetiro.setBounds(130, 196, 120, 25);
		getContentPane().add(txtNumRetiro);

		lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(290, 196, 50, 25);
		getContentPane().add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(340, 196, 100, 25);
		getContentPane().add(txtFecha);

		lblHora = new JLabel("Hora");
		lblHora.setBounds(470, 196, 40, 25);
		getContentPane().add(lblHora);

		txtHora = new JTextField();
		txtHora.setEditable(false);
		txtHora.setBounds(510, 196, 90, 25);
		getContentPane().add(txtHora);

		// Botones
		btnRetirar = new JButton("Retirar alumno");
		btnRetirar.addActionListener(this);
		btnRetirar.setBounds(640, 60, 170, 28);
		getContentPane().add(btnRetirar);

		btnCambiarCurso = new JButton("Cambiar curso");
		btnCambiarCurso.addActionListener(this);
		btnCambiarCurso.setBounds(640, 95, 170, 28);
		getContentPane().add(btnCambiarCurso);

		btnCancelarRetiro = new JButton("Cancelar retiro");
		btnCancelarRetiro.addActionListener(this);
		btnCancelarRetiro.setBounds(640, 130, 170, 28);
		getContentPane().add(btnCancelarRetiro);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(640, 196, 170, 28);
		getContentPane().add(btnCerrar);

		// Tabla
		scpScroll = new JScrollPane();
		scpScroll.setBounds(20, 260, 790, 290);
		getContentPane().add(scpScroll);

		tblMatriculas = new JTable();
		tblMatriculas.setFillsViewportHeight(true);
		tblMatriculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scpScroll.setViewportView(tblMatriculas);

		modelo = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // la tabla solo se visualiza
			}
		};
		modelo.addColumn("N.º matrícula");
		modelo.addColumn("Alumno");
		modelo.addColumn("Curso");
		modelo.addColumn("Estado");
		modelo.addColumn("N.º retiro");
		modelo.addColumn("Fecha retiro");
		modelo.addColumn("Hora retiro");
		tblMatriculas.setModel(modelo);

		// Al elegir una fila se muestran sus datos en el formulario
		tblMatriculas.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				mostrarSeleccion();
		});

		listar();
		limpiarCampos();
	}

	// ---------------------------------------------------------------
	// Eventos
	// ---------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cboFiltro) {
			listar();
			limpiarCampos();
		}
		if (e.getSource() == btnRetirar)
			retirarAlumno();
		if (e.getSource() == btnCambiarCurso)
			cambiarCurso();
		if (e.getSource() == btnCancelarRetiro)
			cancelarRetiro();
		if (e.getSource() == btnCerrar)
			dispose();
	}

	// ---------------------------------------------------------------
	// Operaciones del registro
	// ---------------------------------------------------------------
	// Adicionar: retira al alumno de la matrícula elegida
	private void retirarAlumno() {
		Matricula m = matriculaSeleccionada();
		if (m == null)
			return;
		Alumno a = aa.buscar(m.getCodAlumno());
		if (a == null) {
			error("No se encontró al alumno de la matrícula.");
			return;
		}
		if (ar.buscarMatricula(m.getNumMatricula()) != null || a.getEstado() != MATRICULADO) {
			error("Solo se puede retirar a un alumno con estado 1 (matriculado).\nEste alumno tiene estado "
					+ a.getEstadoTexto() + ".");
			return;
		}
		int r = JOptionPane.showConfirmDialog(this,
				"¿Desea retirar al alumno " + a.getNombres() + " " + a.getApellidos() + "?", "Confirmar",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			// Número correlativo + fecha y hora del sistema
			Retiro x = new Retiro(ar.numeroCorrelativo(), m.getNumMatricula(), fechaSistema(), horaSistema());
			ar.adicionar(x);
			a.setEstado(RETIRADO); // la matrícula queda desactivada
			listar();
			seleccionarMatricula(m.getNumMatricula());
			info("Retiro registrado con número " + x.getNumRetiro() + ".");
		}
	}

	// Modificar: cambia el curso de una matrícula desactivada
	private void cambiarCurso() {
		Matricula m = matriculaSeleccionada();
		if (m == null)
			return;
		Alumno a = aa.buscar(m.getCodAlumno());
		if (a == null || a.getEstado() != RETIRADO) {
			error("Solo se puede cambiar el curso si el alumno tiene estado 2 (retirado).");
			return;
		}
		try {
			int codCurso = leerEntero(txtCodCurso, "el código de curso");
			if (ac.buscar(codCurso) == null)
				throw new Exception("El curso " + codCurso + " no existe.");
			m.setCodCurso(codCurso);
			listar();
			seleccionarMatricula(m.getNumMatricula());
			info("Curso de la matrícula desactivada modificado.");
		} catch (Exception ex) {
			error(ex.getMessage());
		}
	}

	// Eliminar: cancela el retiro (solo si el alumno está retirado)
	private void cancelarRetiro() {
		Matricula m = matriculaSeleccionada();
		if (m == null)
			return;
		Alumno a = aa.buscar(m.getCodAlumno());
		Retiro x = ar.buscarMatricula(m.getNumMatricula());
		if (a == null || x == null || a.getEstado() != RETIRADO) {
			error("Solo se puede cancelar un retiro si el alumno tiene estado 2 (retirado).");
			return;
		}
		int r = JOptionPane.showConfirmDialog(this, "¿Desea cancelar el retiro " + x.getNumRetiro() + " del alumno "
				+ a.getNombres() + " " + a.getApellidos() + "?", "Confirmar", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			ar.eliminar(x); // cancelación física: sale de la lista y del archivo al grabar
			a.setEstado(MATRICULADO); // la matrícula se reactiva
			listar();
			seleccionarMatricula(m.getNumMatricula());
			info("Retiro cancelado. El alumno vuelve a estar matriculado.");
		}
	}

	// ---------------------------------------------------------------
	// Métodos de apoyo
	// ---------------------------------------------------------------
	// Lista las matrículas según el filtro: en curso o retiradas
	private void listar() {
		int filtro = cboFiltro.getSelectedIndex();
		modelo.setRowCount(0);
		for (int i = 0; i < am.tamanio(); i++) {
			Matricula m = am.obtener(i);
			Alumno a = aa.buscar(m.getCodAlumno());
			if (a == null)
				continue;
			if (filtro == EN_CURSO && a.getEstado() != MATRICULADO)
				continue;
			if (filtro == RETIRADOS && a.getEstado() != RETIRADO)
				continue;
			Curso c = ac.buscar(m.getCodCurso());
			Retiro x = ar.buscarMatricula(m.getNumMatricula());
			Object[] fila = { m.getNumMatricula(), a.getNombres() + " " + a.getApellidos(),
					(c == null) ? m.getCodCurso() + "" : c.getAsignatura(), textoEstado(a),
					(x == null) ? "" : "" + x.getNumRetiro(), (x == null) ? "" : x.getFecha(),
					(x == null) ? "" : x.getHora() };
			modelo.addRow(fila);
		}
	}

	private String textoEstado(Alumno a) {
		if (a.getEstado() == MATRICULADO)
			return "En curso";
		if (a.getEstado() == RETIRADO)
			return "Retirado";
		return a.getEstadoTexto();
	}

	// Muestra en el formulario los datos de la fila elegida
	private void mostrarSeleccion() {
		int fila = tblMatriculas.getSelectedRow();
		if (fila == -1) {
			limpiarCampos();
			return;
		}
		Matricula m = am.buscar((Integer) modelo.getValueAt(fila, 0));
		if (m == null)
			return;
		Alumno a = aa.buscar(m.getCodAlumno());
		Curso c = ac.buscar(m.getCodCurso());
		Retiro x = ar.buscarMatricula(m.getNumMatricula());
		txtNumMatricula.setText("" + m.getNumMatricula());
		txtAlumno.setText((a == null) ? "" : a.getNombres() + " " + a.getApellidos());
		txtCodCurso.setText("" + m.getCodCurso());
		txtAsignatura.setText((c == null) ? "" : c.getAsignatura());
		txtEstado.setText((a == null) ? "" : textoEstado(a));
		if (x != null) { // alumno retirado: se muestran los datos de su retiro
			txtNumRetiro.setText("" + x.getNumRetiro());
			txtFecha.setText(x.getFecha());
			txtHora.setText(x.getHora());
		} else { // alumno en curso: se muestra lo que se asignará al retirarlo
			txtNumRetiro.setText("" + ar.numeroCorrelativo());
			txtFecha.setText(fechaSistema());
			txtHora.setText(horaSistema());
		}
		// El curso solo se puede cambiar si el alumno está retirado
		txtCodCurso.setEditable(a != null && a.getEstado() == RETIRADO);
	}

	// Devuelve la matrícula de la fila elegida (o null si no hay selección)
	private Matricula matriculaSeleccionada() {
		int fila = tblMatriculas.getSelectedRow();
		if (fila == -1) {
			error("Seleccione una matrícula de la tabla.");
			return null;
		}
		return am.buscar((Integer) modelo.getValueAt(fila, 0));
	}

	// Vuelve a seleccionar una matrícula luego de refrescar la tabla
	private void seleccionarMatricula(int numMatricula) {
		for (int i = 0; i < modelo.getRowCount(); i++)
			if ((Integer) modelo.getValueAt(i, 0) == numMatricula) {
				tblMatriculas.setRowSelectionInterval(i, i);
				return;
			}
		limpiarCampos(); // la fila ya no está en el filtro actual
	}

	private void limpiarCampos() {
		txtNumMatricula.setText("");
		txtAlumno.setText("");
		txtCodCurso.setText("");
		txtAsignatura.setText("");
		txtEstado.setText("");
		txtNumRetiro.setText("");
		txtFecha.setText("");
		txtHora.setText("");
		txtCodCurso.setEditable(false);
	}

	// Fecha y hora del sistema
	private String fechaSistema() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	private String horaSistema() {
		return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	// Validación de entrada
	private int leerEntero(JTextField txt, String campo) throws Exception {
		try {
			return Integer.parseInt(txt.getText().trim());
		} catch (NumberFormatException e) {
			throw new Exception("Ingrese " + campo + " (número entero).");
		}
	}

	private void info(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	private void error(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}