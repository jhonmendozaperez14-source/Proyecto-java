package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
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
import controlador.ArregloAlumnos;

// Mantenimiento | Alumno
public class DlgAlumno extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Operación activa (la define el último botón presionado)
	private static final int ADICIONAR = 0;
	private static final int CONSULTAR = 1;
	private static final int MODIFICAR = 2;
	private static final int ELIMINAR = 3;

	private JLabel lblCodigo, lblNombres, lblApellidos, lblDni, lblEdad, lblCelular, lblEstado;
	private JTextField txtCodigo, txtNombres, txtApellidos, txtDni, txtEdad, txtCelular, txtEstado;
	private JButton btnAdicionar, btnConsultar, btnModificar, btnEliminar, btnOK;
	private JScrollPane scpScroll;
	private JTable tblAlumnos;
	private DefaultTableModel modelo;

	// Arreglo de alumnos (carga alumnos.txt al crearse)
	private ArregloAlumnos aa = new ArregloAlumnos();
	private int accion = CONSULTAR;

	// Constructor sin parámetros
	public DlgAlumno() {
		this(null);
	}

	// Constructor que recibe la ventana principal (FrmPrincipal)
	public DlgAlumno(JFrame padre) {
		super(padre, "Mantenimiento | Alumno", true);
		setBounds(100, 100, 850, 600);
		setResizable(false);
		setLocationRelativeTo(padre);
		getContentPane().setLayout(null);

		// Al salir de este apartado (X o dispose) se graban los cambios en alumnos.txt
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				aa.grabarAlumnos();
			}
		});

		// Etiquetas y cajas de texto
		lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(20, 20, 100, 25);
		getContentPane().add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(130, 20, 120, 25);
		getContentPane().add(txtCodigo);

		lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(20, 52, 100, 25);
		getContentPane().add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setBounds(130, 52, 280, 25);
		getContentPane().add(txtNombres);

		lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(20, 84, 100, 25);
		getContentPane().add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(130, 84, 280, 25);
		getContentPane().add(txtApellidos);

		lblDni = new JLabel("DNI");
		lblDni.setBounds(20, 116, 100, 25);
		getContentPane().add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(130, 116, 120, 25);
		getContentPane().add(txtDni);

		lblEdad = new JLabel("Edad");
		lblEdad.setBounds(20, 148, 100, 25);
		getContentPane().add(lblEdad);

		txtEdad = new JTextField();
		txtEdad.setBounds(130, 148, 60, 25);
		getContentPane().add(txtEdad);

		lblCelular = new JLabel("Celular");
		lblCelular.setBounds(20, 180, 100, 25);
		getContentPane().add(lblCelular);

		txtCelular = new JTextField();
		txtCelular.setBounds(130, 180, 120, 25);
		getContentPane().add(txtCelular);

		lblEstado = new JLabel("Estado");
		lblEstado.setBounds(20, 212, 100, 25);
		getContentPane().add(lblEstado);

		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setBounds(130, 212, 120, 25);
		getContentPane().add(txtEstado);

		// Botones
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBounds(640, 20, 170, 28);
		getContentPane().add(btnAdicionar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(this);
		btnConsultar.setBounds(640, 55, 170, 28);
		getContentPane().add(btnConsultar);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBounds(640, 90, 170, 28);
		getContentPane().add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(640, 125, 170, 28);
		getContentPane().add(btnEliminar);

		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnOK.setBounds(640, 209, 170, 28);
		getContentPane().add(btnOK);

		// Tabla
		scpScroll = new JScrollPane();
		scpScroll.setBounds(20, 260, 790, 290);
		getContentPane().add(scpScroll);

		tblAlumnos = new JTable();
		tblAlumnos.setFillsViewportHeight(true);
		tblAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scpScroll.setViewportView(tblAlumnos);

		modelo = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // la tabla solo se visualiza
			}
		};
		modelo.addColumn("Código");
		modelo.addColumn("Nombres");
		modelo.addColumn("Apellidos");
		modelo.addColumn("DNI");
		modelo.addColumn("Edad");
		modelo.addColumn("Celular");
		modelo.addColumn("Estado");
		tblAlumnos.setModel(modelo);

		// Al elegir una fila se muestran sus datos en el formulario
		tblAlumnos.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && accion != ADICIONAR)
				mostrarSeleccion();
		});

		// Inicia en modo consulta: se visualizan todos los alumnos
		modoConsultar();
	}

	// ---------------------------------------------------------------
	// Eventos de los botones
	// ---------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdicionar)
			modoAdicionar();
		if (e.getSource() == btnConsultar) {
			modoConsultar();
			if (aa.tamanio() == 0)
				info("No hay alumnos registrados.");
		}
		if (e.getSource() == btnModificar)
			modoModificar();
		if (e.getSource() == btnEliminar)
			modoEliminar();
		if (e.getSource() == btnOK)
			procesarOK();
	}

	// ---------------------------------------------------------------
	// Modos de trabajo
	// ---------------------------------------------------------------
	private void modoAdicionar() {
		accion = ADICIONAR;
		tblAlumnos.clearSelection();
		limpiarCampos();
		txtCodigo.setText("" + aa.codigoCorrelativo()); // código correlativo
		txtEstado.setText("0 - Registrado"); // todo alumno nuevo inicia registrado
		habilitar(true, true);
		btnOK.setEnabled(true);
		txtNombres.requestFocus();
	}

	private void modoConsultar() {
		accion = CONSULTAR;
		listar();
		tblAlumnos.clearSelection();
		limpiarCampos();
		habilitar(false, false);
		btnOK.setEnabled(false);
	}

	private void modoModificar() {
		accion = MODIFICAR;
		habilitar(true, false); // el código y el DNI no se pueden modificar
		btnOK.setEnabled(true);
		mostrarSeleccion();
		if (tblAlumnos.getSelectedRow() == -1)
			info("Seleccione un alumno de la tabla.");
	}

	private void modoEliminar() {
		accion = ELIMINAR;
		habilitar(false, false);
		btnOK.setEnabled(true);
		mostrarSeleccion();
		if (tblAlumnos.getSelectedRow() == -1)
			info("Seleccione un alumno de la tabla.");
	}

	private void procesarOK() {
		switch (accion) {
		case ADICIONAR:
			adicionarAlumno();
			break;
		case MODIFICAR:
			modificarAlumno();
			break;
		case ELIMINAR:
			eliminarAlumno();
			break;
		}
	}

	// ---------------------------------------------------------------
	// Operaciones del mantenimiento
	// ---------------------------------------------------------------
	private void adicionarAlumno() {
		try {
			String nombres = leerTexto(txtNombres, "los nombres");
			String apellidos = leerTexto(txtApellidos, "los apellidos");
			String dni = txtDni.getText().trim();
			if (!dni.matches("\\d{8}"))
				throw new Exception("El DNI debe tener 8 dígitos.");
			Alumno repetido = aa.buscarDni(dni);
			if (repetido != null) // el DNI no debe repetirse
				throw new Exception("El DNI ya está registrado (alumno " + repetido.getCodAlumno() + ").");
			int edad = leerEdad();
			int celular = leerCelular();

			// Código correlativo y estado 0 (registrado)
			Alumno x = new Alumno(aa.codigoCorrelativo(), nombres, apellidos, dni, edad, celular);
			aa.adicionar(x);
			listar();
			info("Alumno adicionado con código " + x.getCodAlumno() + ".");
			modoAdicionar(); // deja listo el siguiente código
		} catch (Exception ex) {
			error(ex.getMessage());
		}
	}

	private void modificarAlumno() {
		Alumno x = alumnoSeleccionado();
		if (x == null)
			return;
		try {
			String nombres = leerTexto(txtNombres, "los nombres");
			String apellidos = leerTexto(txtApellidos, "los apellidos");
			int edad = leerEdad();
			int celular = leerCelular();

			// Se puede modificar todo menos el código y el DNI
			x.setNombres(nombres);
			x.setApellidos(apellidos);
			x.setEdad(edad);
			x.setCelular(celular);

			int fila = tblAlumnos.getSelectedRow();
			listar();
			tblAlumnos.setRowSelectionInterval(fila, fila);
			info("Datos del alumno modificados.");
		} catch (Exception ex) {
			error(ex.getMessage());
		}
	}

	private void eliminarAlumno() {
		Alumno x = alumnoSeleccionado();
		if (x == null)
			return;
		// Solo se puede eliminar un alumno con estado 0 (registrado)
		if (x.getEstado() != Alumno.REGISTRADO) {
			error("Solo se puede eliminar un alumno con estado 0 (registrado).\nEste alumno tiene estado "
					+ x.getEstadoTexto() + ".");
			return;
		}
		int r = JOptionPane.showConfirmDialog(this,
				"¿Desea eliminar al alumno " + x.getNombres() + " " + x.getApellidos() + "?", "Confirmar",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			aa.eliminar(x); // eliminación física: sus datos salen de la lista y del archivo al grabar
			listar();
			limpiarCampos();
			info("Alumno eliminado.");
		}
	}

	// ---------------------------------------------------------------
	// Métodos de apoyo
	// ---------------------------------------------------------------
	private void listar() {
		modelo.setRowCount(0);
		for (int i = 0; i < aa.tamanio(); i++) {
			Alumno x = aa.obtener(i);
			Object[] fila = { x.getCodAlumno(), x.getNombres(), x.getApellidos(), x.getDni(), x.getEdad(),
					x.getCelular(), x.getEstadoTexto() };
			modelo.addRow(fila);
		}
	}

	// Muestra en el formulario los datos de la fila elegida
	private void mostrarSeleccion() {
		int fila = tblAlumnos.getSelectedRow();
		if (fila == -1) {
			limpiarCampos();
			return;
		}
		Alumno x = aa.buscar((Integer) modelo.getValueAt(fila, 0));
		if (x == null)
			return;
		txtCodigo.setText("" + x.getCodAlumno());
		txtNombres.setText(x.getNombres());
		txtApellidos.setText(x.getApellidos());
		txtDni.setText(x.getDni());
		txtEdad.setText("" + x.getEdad());
		txtCelular.setText("" + x.getCelular());
		txtEstado.setText(x.getEstadoTexto());
	}

	// Devuelve el alumno de la fila elegida (o null si no hay selección)
	private Alumno alumnoSeleccionado() {
		int fila = tblAlumnos.getSelectedRow();
		if (fila == -1) {
			error("Seleccione un alumno de la tabla.");
			return null;
		}
		return aa.buscar((Integer) modelo.getValueAt(fila, 0));
	}

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDni.setText("");
		txtEdad.setText("");
		txtCelular.setText("");
		txtEstado.setText("");
	}

	// datos: nombres, apellidos, edad y celular | dni: solo al adicionar
	private void habilitar(boolean datos, boolean dni) {
		txtNombres.setEditable(datos);
		txtApellidos.setEditable(datos);
		txtEdad.setEditable(datos);
		txtCelular.setEditable(datos);
		txtDni.setEditable(dni);
	}

	// Validaciones de entrada
	private String leerTexto(JTextField txt, String campo) throws Exception {
		String t = txt.getText().trim();
		if (t.isEmpty())
			throw new Exception("Ingrese " + campo + ".");
		if (t.contains(";")) // ';' separa los campos en alumnos.txt
			throw new Exception("El campo no puede contener el carácter ';'.");
		return t;
	}

	private int leerEdad() throws Exception {
		try {
			int edad = Integer.parseInt(txtEdad.getText().trim());
			if (edad < 1 || edad > 120)
				throw new Exception("Ingrese una edad válida.");
			return edad;
		} catch (NumberFormatException e) {
			throw new Exception("La edad debe ser un número entero.");
		}
	}

	private int leerCelular() throws Exception {
		String c = txtCelular.getText().trim();
		if (!c.matches("9\\d{8}"))
			throw new Exception("El celular debe tener 9 dígitos y empezar con 9.");
		return Integer.parseInt(c);
	}

	private void info(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	private void error(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}