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

public class CursoUI extends JFrame {
    private Armazenamento armazenamento;
    private DefaultTableModel cursoTabela;
    private JTable cursoTable;
    private JTextField campoTitulo;
    private JTextField campoDescricao;
    private JComboBox<Idioma> idiomaLista;
    private JComboBox<Nivel> nivelLista;
    private JComboBox<Usuario> autoresLista;
    private JButton Criar;
    private JButton editar;
    private JButton remover;
    private JLabel tituloLabel = new JLabel("Título:");
    private JLabel descricaoLabel = new JLabel("Descrição:");
    private JLabel idiomaLabel = new JLabel("Idioma:");
    private JLabel nivelLabel = new JLabel("Nível:");
    private JLabel usuarioLabel = new JLabel("Autor:");

    public CursoUI(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;

        cursoTabela = new DefaultTableModel();
        cursoTabela.addColumn("ID");
        cursoTabela.addColumn("Título");
        cursoTabela.addColumn("Descrição");
        cursoTabela.addColumn("Idioma");
        cursoTabela.addColumn("Nivel");
        cursoTabela.addColumn("Autor");

        cursoTable = new JTable(cursoTabela);
        campoTitulo = new JTextField(20);
        campoDescricao = new JTextField(20);
        idiomaLista = new JComboBox<>();
        armazenamento.listaDeObjectos(Idioma.class).forEach(idioma -> idiomaLista.addItem((Idioma) idioma));

        nivelLista = new JComboBox<>();

        armazenamento.listaDeObjectos(Nivel.class).forEach( nivel -> nivelLista.addItem((Nivel) nivel));
        autoresLista = new JComboBox<>();

        armazenamento.listaDeObjectos(Usuario.class).forEach( usuario -> autoresLista.addItem((Usuario) usuario));

        Criar = new JButton("+ Curso");
        editar = new JButton("Editar");
        remover = new JButton("Remover");

        setTitle("Curso CRUD");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 12, 4, 10));
        topPanel.add(tituloLabel);
        topPanel.add(campoTitulo);
        topPanel.add(descricaoLabel);
        topPanel.add(campoDescricao);
        topPanel.add(idiomaLabel);
        topPanel.add(idiomaLista);
        topPanel.add(nivelLabel);
        topPanel.add(nivelLista);
        topPanel.add(usuarioLabel);
        topPanel.add(autoresLista);
        topPanel.add(Criar);
        topPanel.add(editar);
        topPanel.add(remover);

        add(new JScrollPane(cursoTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        Criar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionalCurso();
            }
        });

        editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editatCurso();
            }
        });

        remover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCurso();
            }
        });

        actualizarTable();
    }

    private void adicionalCurso() {
        String titulo = campoTitulo.getText().trim();
        String descricao = campoDescricao.getText().trim();
        var idiomaId =(Idioma) idiomaLista.getSelectedItem();
        var nivelId = (Nivel) nivelLista.getSelectedItem();
        var usuarioId = (Usuario) autoresLista.getSelectedItem();

        if (!titulo.isEmpty() && !descricao.isEmpty()) {
            Curso curso = new Curso();
            curso.setTitulo(titulo);
            curso.setDescricao(descricao);
            curso.setIdiomaId(idiomaId.getId());
            curso.setNivelId(nivelId.getId());
            curso.setUsuarioId(usuarioId.getId());

            armazenamento.criarObjeto(curso);
            limarCampos();
            actualizarTable();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos campos");
        }
    }

    private void editatCurso() {
        int selectedRow = cursoTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) cursoTabela.getValueAt(selectedRow, 0);
            String titulo = campoTitulo.getText().trim();
            String descricao = campoDescricao.getText().trim();
            int idiomaId =((Idioma) idiomaLista.getSelectedItem()).getId();
            int nivelId = ((Nivel) nivelLista.getSelectedItem()).getId();
            int usuarioId = ((Usuario) autoresLista.getSelectedItem()).getId();

            if (!titulo.isEmpty() && !descricao.isEmpty()) {
                Curso selectedCurso = new Curso();
                selectedCurso.setId(id);
                selectedCurso.setTitulo(titulo);
                selectedCurso.setDescricao(descricao);
                selectedCurso.setIdiomaId(idiomaId);
                selectedCurso.setNivelId(nivelId);
                selectedCurso.setUsuarioId(usuarioId);

                armazenamento.actualizarObjeto(selectedCurso);
                limarCampos();
                actualizarTable();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor preencha todos campos");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione um curso para actualizar.");
        }
    }

    private void removerCurso() {
        int selectedRow = cursoTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) cursoTabela.getValueAt(selectedRow, 0);
            armazenamento.removerObjecto(id, Curso.class);
            limarCampos();
            actualizarTable();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um curso para remover.");
        }
    }

    private void limarCampos() {
        campoTitulo.setText("");
        campoDescricao.setText("");
    }

    private void actualizarTable() {
        cursoTabela.setRowCount(0);
        List<Curso> cursos = armazenamento.listaDeObjectos(Curso.class);
        for (Curso curso : cursos) {
            cursoTabela.addRow(new Object[]{
                    curso.getId(),
                    curso.getTitulo(),
                    curso.getDescricao(),
                    ((Idioma) armazenamento.prourarObjecto(curso.getIdiomaId(),Idioma.class)).getIdioma(),
                    ((Nivel) armazenamento.prourarObjecto(curso.getNivelId(),Nivel.class)).getNivel(),
                    ((Usuario) armazenamento.prourarObjecto(curso.getUsuarioId(),Usuario.class)).getNome(),
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CursoUI cursoUI = new CursoUI(new Armazenamento());
                cursoUI.setVisible(true);
            }
        });
    }
}
