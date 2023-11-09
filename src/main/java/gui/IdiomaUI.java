package gui;

import entidade.Idioma;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class IdiomaUI extends JFrame {
    private Armazenamento armazenamento;
    private DefaultTableModel idiomasLista;
    private JTable idiomaTabela;
    private JTextField campoNome;
    private JButton adicionarIdioma;
    private JButton editarIdioma;
    private JButton removerIdioma;

    public IdiomaUI(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;

        idiomasLista = new DefaultTableModel();
        idiomasLista.addColumn("ID");
        idiomasLista.addColumn("Nome");

        idiomaTabela = new JTable(idiomasLista);
        campoNome = new JTextField(20);
        adicionarIdioma = new JButton("+ criar");
        editarIdioma = new JButton("Editar");
        removerIdioma = new JButton("Remover");

        setTitle("Idiomas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Nome"));
        topPanel.add(campoNome);
        topPanel.add(adicionarIdioma);
        topPanel.add(editarIdioma);
        topPanel.add(removerIdioma);

        add(new JScrollPane(idiomaTabela), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        adicionarIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarIdioma();
            }
        });

        editarIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarIdioma();
            }
        });

        removerIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerIdioma();
            }
        });

        actualizarTabela();
    }

    private void adicionarIdioma() {
        String name = campoNome.getText().trim();
        if (!name.isEmpty()) {
            Idioma language = new Idioma();
            language.setIdioma(name);
            armazenamento.criarObjeto(language);
            campoNome.setText("");
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
        }
    }

    private void editarIdioma() {
        int selectedRow = idiomaTabela.getSelectedRow();
        if (selectedRow != -1) {
            String newName = campoNome.getText().trim();
            if (!newName.isEmpty()) {
                int id = (int) idiomasLista.getValueAt(selectedRow, 0);
                Idioma selectedLanguage = new Idioma();
                selectedLanguage.setId(id);
                selectedLanguage.setIdioma(newName);
                armazenamento.actualizarObjeto(selectedLanguage);
                campoNome.setText("");
                actualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor preencha os campos corretamente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione o idioma para a edição.");
        }
    }

    private void removerIdioma() {
        int selectedRow = idiomaTabela.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) idiomasLista.getValueAt(selectedRow, 0);
            armazenamento.removerObjecto(id, Idioma.class);
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o idioma para remover.");
        }
    }

    private void actualizarTabela() {
        idiomasLista.setRowCount(0); 
        List<Idioma> languages = armazenamento.listaDeObjectos(Idioma.class);
        for (Idioma language : languages) {
            idiomasLista.addRow(new Object[]{language.getId(), language.getIdioma()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                IdiomaUI idiomaUI = new IdiomaUI(new Armazenamento());
                idiomaUI.setVisible(true);
            }
        });
    }
}
