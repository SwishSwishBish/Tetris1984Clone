package com.sena;

import com.sena.Controller.TetrisGUI;

import javax.swing.SwingUtilities;

public class TetrisTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TetrisGUI view = new TetrisGUI();
            view.setVisible(true);
        });
    }
}
