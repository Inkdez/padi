package gui;

import entidade.Nivel;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class NivelCRUDUI extends JFrame {
    private Armazenamento nivelDAO;
    private DefaultTableModel nivelTableModel;
    private JTable nivelTable;
    private JTextField nivelField;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;

    public NivelCRUDUI(Armazenamento nivelDAO) {
        this.nivelDAO = nivelDAO;

        nivelTableModel = new DefaultTableModel();
        nivelTableModel.addColumn("ID");
        nivelTableModel.addColumn("Nivel");

        nivelTable = new JTable(nivelTableModel);
        nivelField = new JTextField(20);
        createButton = new JButton("Create Nivel");
        updateButton = new JButton("Update Nivel");
        deleteButton = new JButton("Delete Nivel");

        setTitle("Nivel CRUD");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(nivelField);
        topPanel.add(createButton);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        add(new JScrollPane(nivelTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNivel();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNivel();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNivel();
            }
        });

        refreshNivelTable();
    }

    private void createNivel() {
        String nivel = nivelField.getText().trim();
        if (!nivel.isEmpty()) {
            Nivel nivelEntity = new Nivel();
            nivelEntity.setNivel(nivel);
            nivelDAO.criarObjeto(nivelEntity);
            nivelField.setText("");
            refreshNivelTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid nivel.");
        }
    }

    private void updateNivel() {
        int selectedRow = nivelTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) nivelTableModel.getValueAt(selectedRow, 0);
            String nivel = nivelField.getText().trim();
            if (!nivel.isEmpty()) {
                Nivel selectedNivel = new Nivel();
                selectedNivel.setId(id);
                selectedNivel.setNivel(nivel);
                nivelDAO.actualizarObjeto(selectedNivel);
                nivelField.setText("");
                refreshNivelTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid nivel.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a nivel to update.");
        }
    }

    private void deleteNivel() {
        int selectedRow = nivelTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) nivelTableModel.getValueAt(selectedRow, 0);
            nivelDAO.removerObjecto(id, Nivel.class);
            nivelField.setText("");
            refreshNivelTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select a nivel to delete.");
        }
    }

    private void refreshNivelTable() {
        nivelTableModel.setRowCount(0); // Clear the table
        List<Nivel> niveles = nivelDAO.listaDeObjectos(Nivel.class);
        for (Nivel nivel : niveles) {
            nivelTableModel.addRow(new Object[]{nivel.getId(), nivel.getNivel()});
        }
    }

    public static void main(String[] args) {
        // Initialize Hibernate and create a NivelDAO
        // Set up Hibernate configuration and create a SessionFactory

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NivelCRUDUI nivelCRUDUI = new NivelCRUDUI(new Armazenamento());
                nivelCRUDUI.setVisible(true);
            }
        });
    }
}
