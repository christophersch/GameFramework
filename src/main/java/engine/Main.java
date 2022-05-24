package engine;

import javax.swing.*;
import java.awt.event.ActionListener;

import MazePlatformer.MazePlatformer;

public class Main {
    public static Game game_instance;

    public static void main(String[] args) {

        game_instance = new MazePlatformer();

        game_instance.applyGUI(new GameWindow(game_instance));

        ActionListener taskPerformer = evt -> {
            game_instance.update();
            game_instance.gui.repaint();
        };

        Timer timer = new Timer(15, taskPerformer);
        timer.start();
    }
}
