package gui;

import entidade.Curso;
import entidade.Idioma;
import entidade.Nivel;
import entidade.Usuario;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CursoCRUDUI extends JFrame {
    private Armazenamento cursoDAO;
    private DefaultTableModel cursoTableModel;
    private JTable cursoTable;
    private JTextField tituloField;
    private JTextField descricaoField;
    private JComboBox<Idioma> idiomaIdDropdown;
    private JComboBox<Nivel> nivelIdDropdown;
    private JComboBox<Usuario> usuarioIdDropdown;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;

    public CursoCRUDUI(Armazenamento cursoDAO) {
        this.cursoDAO = cursoDAO;

        cursoTableModel = new DefaultTableModel();
        cursoTableModel.addColumn("ID");
        cursoTableModel.addColumn("Título");
        cursoTableModel.addColumn("Descrição");
        cursoTableModel.addColumn("Idioma ID");
        cursoTableModel.addColumn("Nivel ID");
        cursoTableModel.addColumn("Usuario ID");

        cursoTable = new JTable(cursoTableModel);
        tituloField = new JTextField(20);
        descricaoField = new JTextField(20);
        idiomaIdDropdown = new JComboBox<>();
        cursoDAO.listaDeObjectos(Idioma.class).forEach(idioma -> idiomaIdDropdown.addItem((Idioma) idioma));

        nivelIdDropdown = new JComboBox<>();

        cursoDAO.listaDeObjectos(Nivel.class).forEach( nivel -> nivelIdDropdown.addItem((Nivel) nivel));
        usuarioIdDropdown = new JComboBox<>();

        cursoDAO.listaDeObjectos(Usuario.class).forEach( usuario -> usuarioIdDropdown.addItem((Usuario) usuario));

        createButton = new JButton("Create Curso");
        updateButton = new JButton("Update Curso");
        deleteButton = new JButton("Delete Curso");

        setTitle("Curso CRUD");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(tituloField);
        topPanel.add(descricaoField);
        topPanel.add(idiomaIdDropdown);
        topPanel.add(nivelIdDropdown);
        topPanel.add(usuarioIdDropdown);
        topPanel.add(createButton);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        add(new JScrollPane(cursoTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCurso();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCurso();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCurso();
            }
        });

        refreshCursoTable();
    }

    private void createCurso() {
        String titulo = tituloField.getText().trim();
        String descricao = descricaoField.getText().trim();
        var idiomaId =(Idioma) idiomaIdDropdown.getSelectedItem();
        var nivelId = (Nivel) nivelIdDropdown.getSelectedItem();
        var usuarioId = (Usuario) usuarioIdDropdown.getSelectedItem();

        if (!titulo.isEmpty() && !descricao.isEmpty()) {
            Curso curso = new Curso();
            curso.setTitulo(titulo);
            curso.setDescricao(descricao);
            curso.setIdiomaId(idiomaId.getId());
            curso.setNivelId(nivelId.getId());
            curso.setUsuarioId(usuarioId.getId());

            cursoDAO.criarObjeto(curso);
            clearFields();
            refreshCursoTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in title and description.");
        }
    }

    private void updateCurso() {
        int selectedRow = cursoTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) cursoTableModel.getValueAt(selectedRow, 0);
            String titulo = tituloField.getText().trim();
            String descricao = descricaoField.getText().trim();
            int idiomaId =((Idioma) idiomaIdDropdown.getSelectedItem()).getId();
            int nivelId = ((Nivel) nivelIdDropdown.getSelectedItem()).getId();
            int usuarioId = ((Usuario) usuarioIdDropdown.getSelectedItem()).getId();

            if (!titulo.isEmpty() && !descricao.isEmpty()) {
                Curso selectedCurso = new Curso();
                selectedCurso.setId(id);
                selectedCurso.setTitulo(titulo);
                selectedCurso.setDescricao(descricao);
                selectedCurso.setIdiomaId(idiomaId);
                selectedCurso.setNivelId(nivelId);
                selectedCurso.setUsuarioId(usuarioId);

                cursoDAO.actualizarObjeto(selectedCurso);
                clearFields();
                refreshCursoTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in title and description.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a curso to update.");
        }
    }

    private void deleteCurso() {
        int selectedRow = cursoTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) cursoTableModel.getValueAt(selectedRow, 0);
            cursoDAO.removerObjecto(id, Curso.class);
            clearFields();
            refreshCursoTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select a curso to delete.");
        }
    }

    private void clearFields() {
        tituloField.setText("");
        descricaoField.setText("");
    }

    private void refreshCursoTable() {
        cursoTableModel.setRowCount(0); // Clear the table
        List<Curso> cursos = cursoDAO.listaDeObjectos(Curso.class);
        for (Curso curso : cursos) {
            cursoTableModel.addRow(new Object[]{
                    curso.getId(),
                    curso.getTitulo(),
                    curso.getDescricao(),
                    ((Idioma) cursoDAO.prourarObjecto(curso.getIdiomaId(),Idioma.class)).getIdioma(),
                    ((Nivel) cursoDAO.prourarObjecto(curso.getNivelId(),Nivel.class)).getNivel(),
                    ((Usuario) cursoDAO.prourarObjecto(curso.getUsuarioId(),Usuario.class)).getNome(),
            });
        }
    }

    public static void main(String[] args) {
        // Initialize Hibernate and create a CursoDAO
        // Set up Hibernate configuration and create a SessionFactory

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CursoCRUDUI cursoCRUDUI = new CursoCRUDUI(new Armazenamento());
                cursoCRUDUI.setVisible(true);
            }
        });
    }
}
