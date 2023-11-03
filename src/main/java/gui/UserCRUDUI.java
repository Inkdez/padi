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

public class UserCRUDUI extends JFrame {
    private Armazenamento userDAO;
    private DefaultTableModel userTableModel;
    private JTable userTable;
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JTextField usernameField;
    private JComboBox<String> adminDropdown;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;

    public UserCRUDUI(Armazenamento userDAO) {
        this.userDAO = userDAO;

        userTableModel = new DefaultTableModel();
        userTableModel.addColumn("ID");
        userTableModel.addColumn("Nome");
        userTableModel.addColumn("Senha");
        userTableModel.addColumn("Username");
        userTableModel.addColumn("Admin");

        userTable = new JTable(userTableModel);
        nomeField = new JTextField(20);
        senhaField = new JPasswordField(20);
        usernameField = new JTextField(20);
        adminDropdown = new JComboBox<>(new String[]{"Yes", "No"});
        createButton = new JButton("Create User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");

        setTitle("User CRUD");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(nomeField);
        topPanel.add(senhaField);
        topPanel.add(usernameField);
        topPanel.add(adminDropdown);
        topPanel.add(createButton);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        add(new JScrollPane(userTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        refreshUserTable();
    }

    private void createUser() {
        String nome = nomeField.getText().trim();
        char[] senhaChars = senhaField.getPassword();
        String senha = new String(senhaChars);
        String username = usernameField.getText().trim();
        boolean admin = adminDropdown.getSelectedItem().equals("Yes");

        if (!nome.isEmpty() && !senha.isEmpty() && !username.isEmpty()) {
            String md5Hash = getMD5Hash(senha);
            Usuario user = new Usuario();
            user.setNome(nome);
            user.setSenha(md5Hash);
            user.setUsername(username);
            user.setAdmin(booleanToByte(admin));

            userDAO.criarObjeto(user);
            clearFields();
            refreshUserTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private Byte booleanToByte(boolean admin) {
        return admin ? (byte) 1 : 0;
    }

    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) userTableModel.getValueAt(selectedRow, 0);
            String nome = nomeField.getText().trim();
            char[] senhaChars = senhaField.getPassword();
            String senha = new String(senhaChars);
            String username = usernameField.getText().trim();
            boolean admin = adminDropdown.getSelectedItem().equals("Yes");

            if (!nome.isEmpty() && !senha.isEmpty() && !username.isEmpty()) {
                String md5Hash = getMD5Hash(senha);
                Usuario selectedUser = new Usuario();
                selectedUser.setId(id);
                selectedUser.setNome(nome);
                selectedUser.setSenha(md5Hash);
                selectedUser.setUsername(username);
                selectedUser.setAdmin(booleanToByte(admin));

                userDAO.actualizarObjeto(selectedUser);
                clearFields();
                refreshUserTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a user to update.");
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) userTableModel.getValueAt(selectedRow, 0);
            userDAO.removerObjecto(id, Usuario.class);
            clearFields();
            refreshUserTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
        }
    }

    private void clearFields() {
        nomeField.setText("");
        senhaField.setText("");
        usernameField.setText("");
        adminDropdown.setSelectedIndex(0);
    }

    private void refreshUserTable() {
        userTableModel.setRowCount(0); // Clear the table
        List<Usuario> users = userDAO.listaDeObjectos(Usuario.class);
        for (Usuario user : users) {
            userTableModel.addRow(new Object[]{user.getId(), user.getNome(), user.getSenha(), user.getUsername(), user.isAdmin() ? "Yes" : "No"});
        }
    }

    private String getMD5Hash(String input) {
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
        // Initialize Hibernate and create a UserDAO
        // Set up Hibernate configuration and create a SessionFactory

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserCRUDUI userCRUDUI = new UserCRUDUI(new Armazenamento());
                userCRUDUI.setVisible(true);
            }
        });
    }
}
