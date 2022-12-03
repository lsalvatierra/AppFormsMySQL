import adapter.CursoTableModel;
import model.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class frmMantCurso extends JDialog {
    private JPanel root;
    private JTextField txtcodcurso;
    private JTextField txtnomcurso;
    private JTextField txtcredcurso;
    private JTable tblcursos;
    private JButton btnregistrar;
    private JButton btnlimpiar;

    public Curso curso;
    public List<Curso> lstcursos= new ArrayList<>();
    CursoTableModel cursoTableModel = new CursoTableModel(lstcursos);

    public frmMantCurso() {

        tblcursos.setModel(cursoTableModel);
        tblcursos.setAutoCreateRowSorter(true);
        btnregistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCurso();
            }
        });
        btnlimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCursoFromDatabase();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmMantCurso");
        frame.setContentPane(new frmMantCurso().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);

    }

    private void registrarCurso() {
        String cod = txtcodcurso.getText();
        String nom = txtnomcurso.getText();
        String cred = txtcredcurso.getText();

        if (cod.isEmpty() || nom.isEmpty() || cred.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        curso = addCursoToDatabase(cod, nom, Integer.parseInt(cred));
        if (curso != null) {
            dispose();
            JOptionPane.showMessageDialog(this,
                    "El usuario fue registrado correctamente",
                    "Correcto",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listCursoFromDatabase(){
        final String DB_URL = "jdbc:mysql://localhost:3306/bdnotas?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "admin";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM curso ORDER BY IdCurso DESC";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //Insert row into the table
            ResultSet rsCurso = preparedStatement.executeQuery();
            while(rsCurso.next()) {
                curso = new Curso();
                curso.setIdcurso(rsCurso.getString(1));
                curso.setNomcurso(rsCurso.getString(2));
                curso.setCredito(rsCurso.getInt(3));
                lstcursos.add(curso);
            }
            cursoTableModel.fireTableDataChanged();
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Curso addCursoToDatabase(String cod, String nom, Integer cred) {
        Curso curso = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/bdnotas?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "admin";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO curso (idcurso, nomcurso, credito) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cod);
            preparedStatement.setString(2, nom);
            preparedStatement.setInt(3, cred);
            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                curso = new Curso();
                curso.setIdcurso(cod);
                curso.setNomcurso(nom);
                curso.setCredito(cred);
            }
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return curso;
    }

}
