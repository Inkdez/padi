package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelAdmin extends JFrame {
    private JButton createUserButton;
    private JButton createCourseButton;
    private JButton createLectureButton;
    private JButton createLanguageButton;
    private JButton createExamButton;

    public PainelAdmin() {
        setTitle("Admin Panel");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        createUserButton = new JButton("Create User");
        createCourseButton = new JButton("Create Course");
        createLectureButton = new JButton("Create Lecture");
        createLanguageButton = new JButton("Create Language");
        createExamButton = new JButton("Create Exam");

        add(createUserButton);
        add(createCourseButton);
        add(createLectureButton);
        add(createLanguageButton);
        add(createExamButton);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement user creation logic
                JOptionPane.showMessageDialog(null, "User creation feature placeholder.");
            }
        });

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement course creation logic
                JOptionPane.showMessageDialog(null, "Course creation feature placeholder.");
            }
        });

        createLectureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement lecture creation logic
                JOptionPane.showMessageDialog(null, "Lecture creation feature placeholder.");
            }
        });

        createLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement language creation logic
                JOptionPane.showMessageDialog(null, "Language creation feature placeholder.");
            }
        });

        createExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement exam creation logic
                JOptionPane.showMessageDialog(null, "Exam creation feature placeholder.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PainelAdmin adminPanel = new PainelAdmin();
                adminPanel.setVisible(true);
            }
        });
    }
}
