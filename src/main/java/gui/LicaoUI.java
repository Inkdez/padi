package gui;

import entidade.Curso;
import entidade.Licao;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LicaoUI extends JFrame {
    private Armazenamento armazenamento;
    private DefaultTableModel tabelaLicaoModel;
    private JTable tabelaLicao;
    private JTextField campoTitulo;
    private JTextArea campoConteudo;
    private JTextField campoOrdem;
    private JComboBox<Curso> cursoLista;
    private JButton criarCurso;
    private JButton edicaoCurso;
    private JButton removeCurso;

    public LicaoUI(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;

        tabelaLicaoModel = new DefaultTableModel();
        tabelaLicaoModel.addColumn("ID");
        tabelaLicaoModel.addColumn("Título");
        tabelaLicaoModel.addColumn("Conteudo");
        tabelaLicaoModel.addColumn("Ordem");
        tabelaLicaoModel.addColumn("Curso");

        JLabel titleLabel = new JLabel("Título:");
        JLabel contentLabel = new JLabel("Conteúdo:");
        JLabel orderLabel = new JLabel("Ordem:");
        JLabel cursoLabel = new JLabel("Curso:");

        tabelaLicao = new JTable(tabelaLicaoModel);
        campoTitulo = new JTextField(20);
        campoConteudo = new JTextArea(5, 20);
        campoOrdem = new JTextField(5);
        cursoLista = new JComboBox<>();

        List<Curso> cursos = armazenamento.listaDeObjectos(Curso.class);
        for (Curso curso : cursos) {
            cursoLista.addItem(curso);
        }

        criarCurso = new JButton("+ criar");
        edicaoCurso = new JButton("Editar");
        removeCurso = new JButton("Remover");

        setTitle("Lições");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(titleLabel);
        topPanel.add(campoTitulo);
        topPanel.add(contentLabel);
        topPanel.add(new JScrollPane(campoConteudo));
        topPanel.add(orderLabel);
        topPanel.add(campoOrdem);
        topPanel.add(cursoLabel);
        topPanel.add(cursoLista);
        topPanel.add(criarCurso);
        topPanel.add(edicaoCurso);
        topPanel.add(removeCurso);
        add(new JScrollPane(tabelaLicao), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        criarCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarLicao();
            }
        });

        edicaoCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLicao();
            }
        });

        removeCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLicao();
            }
        });

        actualizarTabela();
    }

    private void criarLicao() {
        String titulo = campoTitulo.getText().trim();
        String conteudo = campoConteudo.getText().trim();
        int ordem = Integer.parseInt(campoOrdem.getText());
        int cursoId = ((Curso) cursoLista.getSelectedItem()).getId();

        if (!titulo.isEmpty() && !conteudo.isEmpty()) {
            Licao licao = new Licao();
            licao.setTitulo(titulo);
            licao.setConteudo(conteudo);
            licao.setOrdem(ordem);
            licao.setCursoId(cursoId);

            armazenamento.criarObjeto(licao);
            limparCampos();
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
        }
    }

    private void editarLicao() {
        int selectedRow = tabelaLicao.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tabelaLicaoModel.getValueAt(selectedRow, 0);
            String titulo = campoTitulo.getText().trim();
            String conteudo = campoConteudo.getText().trim();
            int ordem = Integer.parseInt(campoOrdem.getText());
            int cursoId = ((Curso) cursoLista.getSelectedItem()).getId();

            if (!titulo.isEmpty() && !conteudo.isEmpty()) {
                Licao selectedLicao = new Licao();
                selectedLicao.setId(id);
                selectedLicao.setTitulo(titulo);
                selectedLicao.setConteudo(conteudo);
                selectedLicao.setOrdem(ordem);
                selectedLicao.setCursoId(cursoId);

                armazenamento.actualizarObjeto(selectedLicao);
                limparCampos();
                actualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione uma lição para editar.");
        }
    }

    private void removerLicao() {
        int selectedRow = tabelaLicao.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tabelaLicaoModel.getValueAt(selectedRow, 0);
            armazenamento.removerObjecto(id, Licao.class);
            limparCampos();
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione uma lição para remover.");
        }
    }

    private void limparCampos() {
        campoTitulo.setText("");
        campoConteudo.setText("");
        campoOrdem.setText("");
    }

    private void actualizarTabela() {
        tabelaLicaoModel.setRowCount(0);
        List<Licao> licoes = armazenamento.listaDeObjectos(Licao.class);
        for (Licao licao : licoes) {
            tabelaLicaoModel.addRow(new Object[]{
                    licao.getId(),
                    licao.getTitulo(),
                    licao.getConteudo(),
                    licao.getOrdem(),
                    licao.getCursoId()
            });
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LicaoUI licaoUI = new LicaoUI(new Armazenamento());
                licaoUI.setVisible(true);
            }
        });
    }
}
