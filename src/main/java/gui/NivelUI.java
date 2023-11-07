package gui;

import entidade.Nivel;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class NivelUI extends JFrame {
    private Armazenamento armazenamento;
    private DefaultTableModel nivelTabela;
    private JTable nivelLista;
    private JTextField campoNivel;
    private JButton criarNivel;
    private JButton editarNivel;
    private JButton removerNivel;

    public NivelUI(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;

        nivelTabela = new DefaultTableModel();
        nivelTabela.addColumn("ID");
        nivelTabela.addColumn("Nivel");

        nivelLista = new JTable(nivelTabela);
        campoNivel = new JTextField(20);
        criarNivel = new JButton("+ criar");
        editarNivel = new JButton("Editar");
        removerNivel = new JButton("remover");

        setTitle("Níveis de curso (Idiomas).");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Nome"));
        topPanel.add(campoNivel);
        topPanel.add(criarNivel);
        topPanel.add(editarNivel);
        topPanel.add(removerNivel);

        add(new JScrollPane(nivelLista), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        criarNivel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarNivel();
            }
        });

        editarNivel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarNivel();
            }
        });

        removerNivel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerNivel();
            }
        });

        actualizarTabela();
    }

    private void adicionarNivel() {
        String nivel = campoNivel.getText().trim();
        if (!nivel.isEmpty()) {
            Nivel nivelEntity = new Nivel();
            nivelEntity.setNivel(nivel);
            armazenamento.criarObjeto(nivelEntity);
            campoNivel.setText("");
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
        }
    }

    private void editarNivel() {
        int selectedRow = nivelLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) nivelTabela.getValueAt(selectedRow, 0);
            String nivel = campoNivel.getText().trim();
            if (!nivel.isEmpty()) {
                Nivel selectedNivel = new Nivel();
                selectedNivel.setId(id);
                selectedNivel.setNivel(nivel);
                armazenamento.actualizarObjeto(selectedNivel);
                campoNivel.setText("");
                actualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor preencha todos os campos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a nivel to update.");
        }
    }

    private void removerNivel() {
        int selectedRow = nivelLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) nivelTabela.getValueAt(selectedRow, 0);
            armazenamento.removerObjecto(id, Nivel.class);
            campoNivel.setText("");
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um nível para remover.");
        }
    }

    private void actualizarTabela() {
        nivelTabela.setRowCount(0); 
        List<Nivel> nivels = armazenamento.listaDeObjectos(Nivel.class);
        for (Nivel nivel : nivels) {
            nivelTabela.addRow(new Object[]{nivel.getId(), nivel.getNivel()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NivelUI nivelUI = new NivelUI(new Armazenamento());
                nivelUI.setVisible(true);
            }
        });
    }
}
