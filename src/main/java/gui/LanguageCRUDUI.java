package gui;

import entidade.Idioma;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LanguageCRUDUI extends JFrame {
    private Armazenamento languageDAO;
    private DefaultTableModel languageTableModel;
    private JTable languageTable;
    private JTextField nameField;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;

    public LanguageCRUDUI(Armazenamento languageDAO) {
        this.languageDAO = languageDAO;

        languageTableModel = new DefaultTableModel();
        languageTableModel.addColumn("ID");
        languageTableModel.addColumn("Name");

        languageTable = new JTable(languageTableModel);
        nameField = new JTextField(20);
        createButton = new JButton("Create Language");
        updateButton = new JButton("Update Language");
        deleteButton = new JButton("Delete Language");

        setTitle("Language CRUD");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(nameField);
        topPanel.add(createButton);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        add(new JScrollPane(languageTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLanguage();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLanguage();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLanguage();
            }
        });

        refreshLanguageTable();
    }

    private void createLanguage() {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            Idioma language = new Idioma();
            language.setIdioma(name);
            languageDAO.criarObjeto(language);
            nameField.setText("");
            refreshLanguageTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid language name.");
        }
    }

    private void updateLanguage() {
        int selectedRow = languageTable.getSelectedRow();
        if (selectedRow != -1) {
            String newName = nameField.getText().trim();
            if (!newName.isEmpty()) {
                int id = (int) languageTableModel.getValueAt(selectedRow, 0);
                Idioma selectedLanguage = new Idioma();
                selectedLanguage.setId(id);
                selectedLanguage.setIdioma(newName);
                languageDAO.actualizarObjeto(selectedLanguage);
                nameField.setText("");
                refreshLanguageTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid language name.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a language to update.");
        }
    }

    private void deleteLanguage() {
        int selectedRow = languageTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) languageTableModel.getValueAt(selectedRow, 0);
            languageDAO.removerObjecto(id, Idioma.class);
            refreshLanguageTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select a language to delete.");
        }
    }

    private void refreshLanguageTable() {
        languageTableModel.setRowCount(0); // Clear the table
        List<Idioma> languages = languageDAO.listaDeObjectos(Idioma.class);
        for (Idioma language : languages) {
            languageTableModel.addRow(new Object[]{language.getId(), language.getIdioma()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LanguageCRUDUI languageCRUDUI = new LanguageCRUDUI(new Armazenamento());
                languageCRUDUI.setVisible(true);
            }
        });
    }
}
