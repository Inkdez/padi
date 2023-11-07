package util;

import gui.CursoUI;
import gui.LicaoUI;
import gui.TesteUI;
import gui.UsuarioUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface MenuDeOpcoes {
     JMenuBar menuBar = new JMenuBar();
     JMenu viewMenu = new JMenu("View");
     JMenuItem usuarioMenuItem =     new JMenuItem("Manage Usuarios");
     JMenuItem cursoMenuItem =      new JMenuItem("Manage Cursos");
     JMenuItem licaoMenuItem =     new JMenuItem("Manage Licoes");
     JMenuItem testeMenuItem =   new JMenuItem("Manage Testes");

     default  void menuDeOpcoes(JFrame frame){
            frame.setJMenuBar(menuBar);
            menuBar.add(viewMenu);
         viewMenu.add(usuarioMenuItem);
         viewMenu.add(cursoMenuItem);
         viewMenu.add(licaoMenuItem);
         viewMenu.add(testeMenuItem);

         // Add action listeners to open respective views when menu items are clicked
         usuarioMenuItem.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 openUsuarioCRUD();
             }
         });

         cursoMenuItem.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 openCursoCRUD();
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
     }


    private void openUsuarioCRUD() {
        // Create and open the UsuarioCRUDUI frame

        UsuarioUI usuarioUI = new UsuarioUI(new Armazenamento());
        this.dispose();
        usuarioUI.setVisible(true);
    }

    void dispose();

    private void openCursoCRUD() {
        // Create and open the CursoCRUDUI frame
        CursoUI cursoUI = new CursoUI(new Armazenamento());
        this.dispose();
        cursoUI.setVisible(true);
    }

    private void openLicaoCRUD() {
        // Create and open the LicaoCRUDUI frame
        LicaoUI licaoUI = new LicaoUI(new Armazenamento());
        this.dispose();
        licaoUI.setVisible(true);
    }

    private void openTesteCRUD() {
        // Create and open the TesteCRUDUI frame
        TesteUI testeUI = new TesteUI(new Armazenamento());
        this.dispose();
        testeUI.setVisible(true);
    }


}
