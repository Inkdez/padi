package gui;

import entidade.Curso;
import entidade.Teste;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TesteUI extends JFrame {
    private Armazenamento armazenamento;
    private DefaultTableModel testeTabela;
    private JTable testeLista;
    private JTextField campoTitulo;
    private JTextField campoOrdem;
    private JComboBox<Curso> cursoLista;
    private JButton criarTeste;
    private JButton editarTeste;
    private JButton removerTeste;

    private JButton criarQuestionario;

    public TesteUI(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;

        testeTabela = new DefaultTableModel();
        testeTabela.addColumn("ID");
        testeTabela.addColumn("Título");
        testeTabela.addColumn("Ordem");
        testeTabela.addColumn("Curso");

        testeLista = new JTable(testeTabela);

        JLabel titleLabel = new JLabel("Título:");
        JLabel orderLabel = new JLabel("Ordem:");
        JLabel cursoLabel = new JLabel("Curso:");

        campoTitulo = new JTextField(20);
        campoOrdem = new JTextField(5);
        cursoLista = new JComboBox<>();
        List<Curso> cursos = armazenamento.listaDeObjectos(Curso.class);
        for (Curso curso : cursos) {
            cursoLista.addItem(curso);
        }

        criarTeste = new JButton("+ criar");
        editarTeste = new JButton("Editar");
        removerTeste = new JButton("Remover");
        criarQuestionario = new JButton("Elaborar teste.");

        setTitle("Testes");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(titleLabel);
        topPanel.add(campoTitulo);
        topPanel.add(orderLabel);
        topPanel.add(campoOrdem);
        topPanel.add(cursoLabel);
        topPanel.add(cursoLista);
        topPanel.add(criarTeste);
        topPanel.add(editarTeste);
        topPanel.add(removerTeste);
        topPanel.add(criarQuestionario);

        add(new JScrollPane(testeLista), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        criarTeste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarTeste();
            }
        });

        editarTeste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarTeste();
            }
        });

        removerTeste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerTeste();
            }
        });

        criarQuestionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarQuestionario();
            }
        });

        actualizarTabela();
    }

    private void criarTeste() {
        String titulo = campoTitulo.getText().trim();
        int ordem = Integer.parseInt(campoOrdem.getText());
        int cursoId =( (Curso) cursoLista.getSelectedItem()).getId();

        if (!titulo.isEmpty()) {
            Teste teste = new Teste();
            teste.setTitulo(titulo);
            teste.setOrdem(ordem);
            teste.setCursoId(cursoId);

            armazenamento.criarObjeto(teste);
            limparCampos();
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
        }
    }

    private void editarTeste() {
        int selectedRow = testeLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) testeTabela.getValueAt(selectedRow, 0);
            String titulo = campoTitulo.getText().trim();
            int ordem = Integer.parseInt(campoOrdem.getText());
            int cursoId = ((Curso) cursoLista.getSelectedItem()).getId();

            if (!titulo.isEmpty()) {
                Teste selectedTeste = new Teste();
                selectedTeste.setId(id);
                selectedTeste.setTitulo(titulo);
                selectedTeste.setOrdem(ordem);
                selectedTeste.setCursoId(cursoId);

                armazenamento.actualizarObjeto(selectedTeste);
                limparCampos();
                actualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor preencha  todos campos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um teste para editar.");
        }
    }

    private void removerTeste() {
        int selectedRow = testeLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) testeTabela.getValueAt(selectedRow, 0);
            armazenamento.removerObjecto(id, Teste.class);
            limparCampos();
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um teste para remover.");
        }
    }

    private void criarQuestionario() {
        int selectedRow = testeLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) testeTabela.getValueAt(selectedRow, 0);
            var test = (Teste) armazenamento.prourarObjecto(id, Teste.class);
            CriarTeste testeUI = new CriarTeste(test,new Armazenamento());
            testeUI.setVisible(true);
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um teste para poder elaboralo.");
        }
    }

    private void limparCampos() {
        campoTitulo.setText("");
        campoOrdem.setText("");
    }

    private void actualizarTabela() {
        testeTabela.setRowCount(0);
        List<Teste> testes = armazenamento.listaDeObjectos(Teste.class);
        for (Teste teste : testes) {
            testeTabela.addRow(new Object[]{
                    teste.getId(),
                    teste.getTitulo(),
                    teste.getOrdem(),
                    teste.getCursoId()
            });
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TesteUI testeUI = new TesteUI(new Armazenamento());
                testeUI.setVisible(true);
            }
        });
    }
}
