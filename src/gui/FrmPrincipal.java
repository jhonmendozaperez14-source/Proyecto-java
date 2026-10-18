package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FrmPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	private JMenuBar menuBar;
	private JMenu mnMantenimiento;
	private JMenuItem mntmAlumno;
	private JMenuItem mntmCurso;
	
	private JMenu mnRegistro;
	private JMenuItem mntmMatricula;
	private JMenuItem mntmRetiro;
	
	private JMenu mnConsulta;
	private JMenuItem mntmConsulta;
	
	private JMenu mnReporte;
	private JMenuItem mntmReporte;

	private JLabel lblTitulo;
	private JLabel lblSubtitulo;
	private JLabel lblFooter;
	private JPanel panelDecorativo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPrincipal frame = new FrmPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmPrincipal() {
		setTitle("Sistema de Registro y Matrícula - Cibertec");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 520);
		setLocationRelativeTo(null);

		// Crear barra de menú
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menú Mantenimiento
		mnMantenimiento = new JMenu("Mantenimiento");
		menuBar.add(mnMantenimiento);
		mntmAlumno = new JMenuItem("Alumno");
		mntmAlumno.addActionListener(this);
		mnMantenimiento.add(mntmAlumno);
		mntmCurso = new JMenuItem("Curso");
		mntmCurso.addActionListener(this);
		mnMantenimiento.add(mntmCurso);

		// Menú Registro
		mnRegistro = new JMenu("Registro");
		menuBar.add(mnRegistro);
		mntmMatricula = new JMenuItem("Matrícula");
		mntmMatricula.addActionListener(this);
		mnRegistro.add(mntmMatricula);
		mntmRetiro = new JMenuItem("Retiro");
		mntmRetiro.addActionListener(this);
		mnRegistro.add(mntmRetiro);

		// Menú Consulta
		mnConsulta = new JMenu("Consulta");
		menuBar.add(mnConsulta);
		mntmConsulta = new JMenuItem("Consultas Generales");
		mntmConsulta.addActionListener(this);
		mnConsulta.add(mntmConsulta);

		// Menú Reporte
		mnReporte = new JMenu("Reporte");
		menuBar.add(mnReporte);
		mntmReporte = new JMenuItem("Reportes del Sistema");
		mntmReporte.addActionListener(this);
		mnReporte.add(mntmReporte);

		// Panel Principal
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 247, 250)); 
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	
		
		// Panel decorativo central
		panelDecorativo = new JPanel();
		panelDecorativo.setBackground(Color.WHITE);
		panelDecorativo.setBorder(new LineBorder(new Color(218, 225, 233), 1, true));
		panelDecorativo.setBounds(120, 80, 590, 280);
		contentPane.add(panelDecorativo);
		panelDecorativo.setLayout(null);

		// Título principal dentro de la tarjeta
		lblTitulo = new JLabel("SISTEMA DE MATRÍCULAS");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(new Color(41, 128, 185)); // Azul corporativo
		lblTitulo.setBounds(30, 45, 530, 40);
		panelDecorativo.add(lblTitulo);

	
		lblSubtitulo = new JLabel("Algoritmos y Estructura de Datos - Proyecto de Investigación");
		lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtitulo.setForeground(new Color(100, 110, 120));
		lblSubtitulo.setBounds(30, 95, 530, 30);
		panelDecorativo.add(lblSubtitulo);

	
		JLabel lblInstruccion = new JLabel("Utilice la barra de menú superior para acceder a los módulos.");
		lblInstruccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstruccion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		lblInstruccion.setForeground(new Color(140, 150, 160));
		lblInstruccion.setBounds(30, 180, 530, 25);
		panelDecorativo.add(lblInstruccion);

		// Pie de página de la ventana
		lblFooter = new JLabel("Desarrollado para el ciclo 2026 - Cibertec");
		lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblFooter.setForeground(new Color(120, 130, 140));
		lblFooter.setBounds(0, 435, 850, 20);
		contentPane.add(lblFooter);
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmAlumno) {
			actionPerformedMntmAlumno(e);
		}
		if (e.getSource() == mntmCurso) {
			actionPerformedMntmCurso(e);
		}
		if (e.getSource() == mntmMatricula) {
			actionPerformedMntmMatricula(e);
		}
		if (e.getSource() == mntmRetiro) {
			actionPerformedMntmRetiro(e);
		}
		if (e.getSource() == mntmConsulta) {
			actionPerformedMntmConsulta(e);
		}
		if (e.getSource() == mntmReporte) {
			actionPerformedMntmReporte(e);
		}
	}


	protected void actionPerformedMntmAlumno(ActionEvent e) {
		DlgAlumno dlg = new DlgAlumno();
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}

	protected void actionPerformedMntmCurso(ActionEvent e) {
		DlgCurso dlg = new DlgCurso();
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}

	protected void actionPerformedMntmMatricula(ActionEvent e) {
		DlgMatricula dlg = new DlgMatricula(this, true);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}

	protected void actionPerformedMntmRetiro(ActionEvent e) {
		DlgRetiro dlg = new DlgRetiro();
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}

	protected void actionPerformedMntmConsulta(ActionEvent e) {
		DlgConsulta dlg = new DlgConsulta();
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}

	protected void actionPerformedMntmReporte(ActionEvent e) {
		DlgReporte dlg = new DlgReporte();
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
	}
}