package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entidade.Usuario;
import jakarta.persistence.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends JFrame {
    private JTextField campoUsername;
    private JPasswordField campoSenha;
    private JButton fazerLogin;

    public Login() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));
        campoUsername = new JTextField(20);
        campoSenha = new JPasswordField(20);
        fazerLogin = new JButton("Login");

        add(new JLabel("Username:"));
        add(campoUsername);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(new JLabel());
        add(fazerLogin);

        fazerLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = campoUsername.getText();
                char[] passwordChars = campoSenha.getPassword();
                String password = new String(passwordChars);

                if (autenticacao(username, password)[0] && autenticacao(username, password)[1]) {
                    PainelAdministrativo painelAdmin = new PainelAdministrativo();
                    painelAdmin.setVisible(true);
                    dispose();
                } else  {
                    JOptionPane.showMessageDialog(null, "Erro na autenticação: preencha correctamente os campos.");
                }
                campoSenha.setText("");
            }
        });
    }

    private boolean[] autenticacao(String username, String senha) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Usuario usuario = null;
        try {

            entityTransaction.begin();
            TypedQuery<Usuario> typedQuery = entityManager.createNamedQuery("Usuario.porUsername",Usuario.class);
            typedQuery.setParameter(1,username);
            
            if(!typedQuery.getResultList().isEmpty()) {
                usuario =  (Usuario) typedQuery.getResultList().toArray()[0];
            }
           
            entityTransaction.commit();
            
            if (usuario != null) {
                String hashedPassword = senhaMd5Hash(senha);
                if (hashedPassword.equals(usuario.getSenha()) && usuario.isAdmin()) {
                    return new boolean[]{true,true};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return new boolean[]{false, false};
    }

    // Hash the password using MD5
    private String senhaMd5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
