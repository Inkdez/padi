package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;

public class Login extends JFrame implements ActionListener
{
    JButton b1;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    JTextField  textField1,textField2;


    public Login() throws Exception
    {
        this.textField1 = new JTextField();
        this.textField2 = new JTextField();
        this.b1 = new JButton();
        this.newPanel = new JPanel();
        this.userLabel = new JLabel();
        this.passLabel = new JLabel();
        CreateLoginForm();
    }
    //calling constructor
    public void CreateLoginForm()
    {

        JLabel userLabel = new JLabel();
        userLabel.setText("Nome do usuário");
        JTextField textField1 = new JTextField(15);
        JLabel  passLabel = new JLabel();
        passLabel.setText("Senha");
        JPasswordField textField2 = new JPasswordField(15);
        b1 = new JButton("Login");
        newPanel = new JPanel(new GridLayout(3, 2));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(b1);

        add(newPanel, BorderLayout.CENTER);

        b1.addActionListener(this);
        setTitle("LOGIN");

        this.setSize(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        String userValue = this.textField1.getText();
        String passValue = textField2.getText();
        if (true) {
            PainelAdmin painelAdmin = new PainelAdmin();
            painelAdmin.setVisible(true);
            this.setVisible(false);
        }
        else{
            JOptionPane.showMessageDialog(null, "Nome do usuário ou senha incorreta!"  );
        }
    }
}