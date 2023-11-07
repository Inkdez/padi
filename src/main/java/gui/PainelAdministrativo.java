package gui;
import util.Armazenamento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelAdministrativo extends JFrame {
    private JMenuBar menuBar;
    private JMenu viewMenu;

    private JMenuItem cursoMenuItem;

    private JMenuItem idiomaMenuItem;

    private JMenuItem licaoMenuItem;

    private JMenuItem nivelMenuItem;

    private JMenuItem testeMenuItem;

    private JMenuItem usuarioMenuItem;



    public PainelAdministrativo() {
        setTitle("Painel administrativo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        viewMenu = new JMenu("Operações");
        menuBar.add(viewMenu);


        cursoMenuItem = new JMenuItem("Cursos");
        idiomaMenuItem = new JMenuItem("Idiomas");
        licaoMenuItem = new JMenuItem("Lições");
        nivelMenuItem  = new JMenuItem("Níveis");
        testeMenuItem = new JMenuItem("Testes");
        usuarioMenuItem = new JMenuItem("Usuários");

        viewMenu.add(cursoMenuItem);
        viewMenu.add(idiomaMenuItem);
        viewMenu.add(licaoMenuItem);
        viewMenu.add(nivelMenuItem);
        viewMenu.add(testeMenuItem);
        viewMenu.add(usuarioMenuItem);


        cursoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCursoCRUD();
            }
        });


        idiomaMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openIdiomaCRUD();
            }
        });

        nivelMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNivelCRUD();
            }
        });


        licaoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLicaoCRUD();
            }
        });

        testeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTesteCRUD();
            }
        });

        usuarioMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUsuarioCRUD();
            }
        });



    }

    private void openUsuarioCRUD() {
        UsuarioUI usuarioUI = new UsuarioUI(new Armazenamento());
        this.dispose();
        usuarioUI.setVisible(true);
    }

    private void openNivelCRUD() {
        NivelUI nivelUI = new NivelUI(new Armazenamento());
        this.dispose();
        nivelUI.setVisible(true);
    }

    private void openIdiomaCRUD() {
        IdiomaUI idiomaUI = new IdiomaUI(new Armazenamento());
        this.dispose();
        idiomaUI.setVisible(true);
    }

    private void openCursoCRUD() {
        CursoUI cursoUI = new CursoUI(new Armazenamento());
        this.dispose();
        cursoUI.setVisible(true);
    }

    private void openLicaoCRUD() {
        LicaoUI licaoUI = new LicaoUI(new Armazenamento());
        this.dispose();
        licaoUI.setVisible(true);
    }

    private void openTesteCRUD() {
        TesteUI testeUI = new TesteUI(new Armazenamento());
        this.dispose();
        testeUI.setVisible(true);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PainelAdministrativo painelAdministrativo = new PainelAdministrativo();
                painelAdministrativo.setVisible(true);
            }
        });
    }
}
