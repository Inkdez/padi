package gui;

import entidade.Usuario;
import util.Armazenamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsuarioUI extends JFrame {
    private Armazenamento armazenamento;
    private DefaultTableModel tabelaUsuario;
    private JTable usuarioLista;
    private JTextField campoNome;
    private JPasswordField campoSenha;
    private JTextField campoUsername;
    private JComboBox<String> adminVerificao;
    private JButton criarUsuario;
    private JButton editarUsuario;
    private JButton removerUsuario;

    public UsuarioUI(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;

        tabelaUsuario = new DefaultTableModel();
        tabelaUsuario.addColumn("ID");
        tabelaUsuario.addColumn("Nome");
        tabelaUsuario.addColumn("Senha");
        tabelaUsuario.addColumn("Username");
        tabelaUsuario.addColumn("É administrador?");

        usuarioLista = new JTable(tabelaUsuario);
        campoNome = new JTextField(20);
        campoSenha = new JPasswordField(20);
        campoUsername = new JTextField(20);
        adminVerificao = new JComboBox<>(new String[]{"Sim", "Não"});
        criarUsuario = new JButton("+ criar");
        editarUsuario = new JButton("Editar");
        removerUsuario = new JButton("remover");

        setTitle("Usuários");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Nome"));
        topPanel.add(campoNome);
        topPanel.add(new JLabel("Senha"));
        topPanel.add(campoSenha);
        topPanel.add(new JLabel("Username"));
        topPanel.add(campoUsername);
        topPanel.add(new JLabel("É administrador?"));
        topPanel.add(adminVerificao);
        
        topPanel.add(criarUsuario);
        topPanel.add(editarUsuario);
        topPanel.add(removerUsuario);

        add(new JScrollPane(usuarioLista), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        criarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarUsuario();
            }
        });

        editarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });

        removerUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerusuario();
            }
        });

        actualizarTabela();
    }

    private void criarUsuario() {
        String nome = campoNome.getText().trim();
        char[] senhaChars = campoSenha.getPassword();
        String senha = new String(senhaChars);
        String username = campoUsername.getText().trim();
        boolean admin = adminVerificao.getSelectedItem().equals("Sim");

        if (!nome.isEmpty() && !senha.isEmpty() && !username.isEmpty()) {
            String md5Hash = coverterParaMD5(senha);
            Usuario user = new Usuario();
            user.setNome(nome);
            user.setSenha(md5Hash);
            user.setUsername(username);
            user.setAdmin(booleanToByte(admin));
            armazenamento.criarObjeto(user);
            clearFields();
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
        }
    }

    private Byte booleanToByte(boolean admin) {
        return admin ? (byte) 1 : 0;
    }

    private void editarUsuario() {
        int selectedRow = usuarioLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tabelaUsuario.getValueAt(selectedRow, 0);
            String nome = campoNome.getText().trim();
            char[] senhaChars = campoSenha.getPassword();
            String senha = new String(senhaChars);
            String username = campoUsername.getText().trim();
            boolean admin = adminVerificao.getSelectedItem().equals("Sim");

            if (!nome.isEmpty() && !senha.isEmpty() && !username.isEmpty()) {
                String md5Hash = coverterParaMD5(senha);
                Usuario selectedUser = new Usuario();
                selectedUser.setId(id);
                selectedUser.setNome(nome);
                selectedUser.setSenha(md5Hash);
                selectedUser.setUsername(username);
                selectedUser.setAdmin(booleanToByte(admin));

                armazenamento.actualizarObjeto(selectedUser);
                clearFields();
                actualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor preencha todos campos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
        }
    }

    private void removerusuario() {
        int selectedRow = usuarioLista.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tabelaUsuario.getValueAt(selectedRow, 0);
            armazenamento.removerObjecto(id, Usuario.class);
            clearFields();
            actualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para remover.");
        }
    }

    private void clearFields() {
        campoNome.setText("");
        campoSenha.setText("");
        campoUsername.setText("");
        adminVerificao.setSelectedIndex(0);
    }

    private void actualizarTabela() {
        tabelaUsuario.setRowCount(0);
        List<Usuario> users = armazenamento.listaDeObjectos(Usuario.class);
        for (Usuario user : users) {
            tabelaUsuario.addRow(new Object[]{user.getId(), user.getNome(), user.getSenha(), user.getUsername(), user.isAdmin() ? "Sim" : "Não"});
        }
    }

    private String coverterParaMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UsuarioUI userCRUDUI = new UsuarioUI(new Armazenamento());
                userCRUDUI.setVisible(true);
            }
        });
    }
}
