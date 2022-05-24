package engine;

import MazePlatformer.MazePlatformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JPanel {

    public static int GAME_WIDTH = 400;     // native window width (pixels)
    public static int GAME_HEIGHT = 225;    // native window height (pixels)

    public static int window_scale = 3;     // window size

    Game game_instance;
    JFrame window = new JFrame();

    public GameWindow(Game game_instance) {
        this.game_instance = game_instance;
        window.add(this);
        window.setSize(GAME_WIDTH * GameWindow.window_scale, GAME_HEIGHT * GameWindow.window_scale);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game_instance.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                game_instance.keyPressed(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        game_instance.preDraw(g2);
        game_instance.draw(g2);
        game_instance.postDraw(g2);
    }
}
