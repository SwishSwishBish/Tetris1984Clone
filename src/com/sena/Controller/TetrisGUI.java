package com.sena.Controller;

import com.sena.TetrisModel.TetrisModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TetrisGUI extends JFrame implements KeyListener {

    private final TetrisModel model = new TetrisModel();
    private final JTextArea text = new JTextArea();

    private final DrawScreen drawScreen = new DrawScreen();


    public TetrisGUI() {
        setTitle("Tetris 1984");
        setSize(380, 485);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        text.setFont(new Font("Courier New", Font.PLAIN, 16));
        text.addKeyListener(this);
        add(text);
        text.requestFocus();

        new Thread(() -> {
            while (true) {
                if (!model.isGameOver() && !model.isStart()) {
                    model.update();
                }
                draw();
                text.setText(drawScreen.convertScreenToText());
                try {
                    //noinspection BusyWait
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    public void draw() {
        drawScreen.clear();
        drawScreen.drawScore(model);
        drawScreen.drawGrid(model);
        drawScreen.drawNextPiece(model);
        drawScreen.drawInfo(model);
        if (model.isStart()) drawScreen.drawStart();
        if (model.isGameOver()) {
            drawScreen.drawGameOver();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (model.isGameOver() || model.isStart()) {
                if (e.getKeyCode() == 32) {
                    model.start();
                }
            } else {
                switch (e.getKeyCode()) {
                    case 37 -> model.move(-1);
                    case 39 -> model.move(1);
                    case 38 -> model.rotate();
                    case 40 -> model.down();
                    case 65 -> model.update();
                }
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}