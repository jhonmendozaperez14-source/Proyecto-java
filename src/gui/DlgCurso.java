package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import clases.Curso;
import controlador.ArregloCursos;
import controlador.ArregloMatriculas;

public class DlgCurso extends JDialog implements ActionListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private JLabel lblCodCurso, lblAsignatura, lblCiclo, lblCréditos, lblHoras;
    private JTextField txtCodCurso, txtAsignatura, txtCréditos, txtHoras;
    private JComboBox<String> cboCiclo;
    private JButton btnAdicionar, btnConsultar, btnModificar, btnEliminar;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    private ArregloCursos ac = new ArregloCursos();
    private ArregloMatriculas am = new ArregloMatriculas(); 

    public static void main(String[] args) {
        try {
            DlgCurso dialog = new DlgCurso();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DlgCurso() {
        setTitle("Mantenimiento de Curso");
        setBounds(100, 100, 650, 480);
        getContentPane().setLayout(null);

        lblCodCurso = new JLabel("Cod. Curso:");
        lblCodCurso.setBounds(30, 30, 90, 23);
        getContentPane().add(lblCodCurso);

        txtCodCurso = new JTextField();
        txtCodCurso.setBounds(130, 30, 120, 23);
        getContentPane().add(txtCodCurso);
        txtCodCurso.setColumns(10);

        lblAsignatura = new JLabel("Asignatura:");
        lblAsignatura.setBounds(30, 70, 90, 23);
        getContentPane().add(lblAsignatura);

        txtAsignatura = new JTextField();
        txtAsignatura.setBounds(130, 70, 200, 23);
        getContentPane().add(txtAsignatura);
        txtAsignatura.setColumns(10);

        lblCiclo = new JLabel("Ciclo:");
        lblCiclo.setBounds(30, 110, 90, 23);
        getContentPane().add(lblCiclo);

        cboCiclo = new JComboBox<String>();
        cboCiclo.addItem("Primero ");
        cboCiclo.addItem("Segundo ");
        cboCiclo.addItem("Tercero ");
        cboCiclo.addItem("Cuarto ");
        cboCiclo.addItem("Quinto ");
        cboCiclo.addItem("Sexto ");
        cboCiclo.setBounds(130, 110, 150, 23);
        getContentPane().add(cboCiclo);

        lblCréditos = new JLabel("Créditos:");
        lblCréditos.setBounds(350, 30, 80, 23);
        getContentPane().add(lblCréditos);

        txtCréditos = new JTextField();
        txtCréditos.setBounds(440, 30, 100, 23);
        getContentPane().add(txtCréditos);
        txtCréditos.setColumns(10);

        lblHoras = new JLabel("Horas:");
        lblHoras.setBounds(350, 70, 80, 23);
        getContentPane().add(lblHoras);

        txtHoras = new JTextField();
        txtHoras.setBounds(440, 70, 100, 23);
        getContentPane().add(txtHoras);
        txtHoras.setColumns(10);

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(30, 150, 100, 30);
        btnAdicionar.addActionListener(this);
        getContentPane().add(btnAdicionar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(140, 150, 100, 30);
        btnConsultar.addActionListener(this);
        getContentPane().add(btnConsultar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(250, 150, 100, 30);
        btnModificar.addActionListener(this);
        getContentPane().add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(360, 150, 100, 30);
        btnEliminar.addActionListener(this);
        getContentPane().add(btnEliminar);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 200, 580, 210);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(this);
        model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Asignatura");
        model.addColumn("Ciclo");
        model.addColumn("Créditos");
        model.addColumn("Horas");
        table.setModel(model);
        scrollPane.setViewportView(table);

        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar) {
            actionPerformedBtnAdicionar(e);
        }
        if (e.getSource() == btnConsultar) {
            actionPerformedBtnConsultar(e);
        }
        if (e.getSource() == btnModificar) {
            actionPerformedBtnModificar(e);
        }
        if (e.getSource() == btnEliminar) {
            actionPerformedBtnEliminar(e);
        }
    }

    protected void actionPerformedBtnAdicionar(ActionEvent e) {
        try {
            int cod = Integer.parseInt(txtCodCurso.getText().trim());
            
            if (cod < 1000 || cod > 9999) {
                mensaje("El código debe ser de cuatro dígitos.");
                return;
            }
            if (ac.buscar(cod) != null) {
                mensaje("El código de curso ya existe.");
                return;
            }
            String asignatura = txtAsignatura.getText().trim();
            if (asignatura.isEmpty()) {
                mensaje("Ingrese una asignatura válida.");
                return;
            }
         
         if (!asignatura.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
             mensaje("La asignatura solo debe contener letras.");
             txtAsignatura.requestFocus();
             return;
         }
            
            int ciclo = cboCiclo.getSelectedIndex();
            int créditos = Integer.parseInt(txtCréditos.getText().trim());
            int horas = Integer.parseInt(txtHoras.getText().trim());

            Curso c = new Curso(cod, asignatura, ciclo, créditos, horas);
            ac.adicionar(c); 
            listar();
            limpiar();
            mensaje("Curso adicionado con éxito.");
        } catch (Exception ex) {
            mensaje("Por favor, ingrese valores numéricos correctos en los campos correspondientes.");
        }
    }

    protected void actionPerformedBtnConsultar(ActionEvent e) {
        try {
            int cod = Integer.parseInt(txtCodCurso.getText().trim());
            Curso c = ac.buscar(cod);
            if (c != null) {
                txtAsignatura.setText(c.getAsignatura());
                cboCiclo.setSelectedIndex(c.getCiclo());
                txtCréditos.setText(String.valueOf(c.getCreditos()));
                txtHoras.setText(String.valueOf(c.getHoras()));
            } else {
                mensaje("Curso no encontrado.");
            }
        } catch (Exception ex) {
            mensaje("Ingrese un código válido para consultar.");
        }
    }

    protected void actionPerformedBtnModificar(ActionEvent e) {
        try {
            int cod = Integer.parseInt(txtCodCurso.getText().trim());
            Curso c = ac.buscar(cod);
            if (c != null) {
                c.setAsignatura(txtAsignatura.getText().trim());
                c.setCiclo(cboCiclo.getSelectedIndex());
                c.setCreditos(Integer.parseInt(txtCréditos.getText().trim()));
                c.setHoras(Integer.parseInt(txtHoras.getText().trim()));
                ac.grabarCursos();
                listar();
                limpiar();
                mensaje("Curso modificado con éxito.");
            } else {
                mensaje("El curso a modificar no existe.");
            }
        } catch (Exception ex) {
            mensaje("Error al modificar los datos del curso.");
        }
    }

    protected void actionPerformedBtnEliminar(ActionEvent e) {
        try {
            int cod = Integer.parseInt(txtCodCurso.getText().trim());
            Curso c = ac.buscar(cod);
            if (c != null) {
                
                if (am.existeMatriculaConCurso(cod)) {
                    mensaje("No se puede eliminar el curso, hay alumnos matriculados en él.");
                    return;
                }
                int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el curso?", "Sistema", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    ac.eliminar(c);
                    listar();
                    limpiar();
                    mensaje("Curso eliminado correctamente.");
                }
            } else {
                mensaje("Seleccione un curso existente para eliminar.");
            }
        } catch (Exception ex) {
            mensaje("Error al intentar eliminar el curso.");
        }
    }

    void listar() {
        model.setRowCount(0);
        for (int i = 0; i < ac.tamanio(); i++) {
            Curso c = ac.obtener(i);
            Object[] fila = {
                c.getCodCurso(),
                c.getAsignatura(),
                obtenerNombreCiclo(c.getCiclo()),
                c.getCreditos(),
                c.getHoras()
            };
            model.addRow(fila);
        }
    }

    String obtenerNombreCiclo(int ciclo) {
        switch (ciclo) {
            case 0: return "Primero";
            case 1: return "Segundo";
            case 2: return "Tercero";
            case 3: return "Cuarto";
            case 4: return "Quinto";
            case 5: return "Sexto";
            default: return "Desconocido";
        }
    }

    void limpiar() {
        txtCodCurso.setText("");
        txtAsignatura.setText("");
        cboCiclo.setSelectedIndex(0);
        txtCréditos.setText("");
        txtHoras.setText("");
        txtCodCurso.requestFocus();
    }

    void mensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public void mouseClicked(MouseEvent e) {
        int fila = table.getSelectedRow();
        if (fila >= 0) {
            txtCodCurso.setText(table.getValueAt(fila, 0).toString());
            try {
                int cod = Integer.parseInt(txtCodCurso.getText().trim());
                Curso c = ac.buscar(cod);
                if (c != null) {
                    txtAsignatura.setText(c.getAsignatura());
                    cboCiclo.setSelectedIndex(c.getCiclo());
                    txtCréditos.setText(String.valueOf(c.getCreditos()));
                    txtHoras.setText(String.valueOf(c.getHoras()));
                }
            } catch (Exception ex) {
                
            }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}