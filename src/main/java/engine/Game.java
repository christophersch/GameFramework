package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Game {
    public int tick = 0;
    public Camera camera;
    public GameWindow gui;

    public Game() {
        camera = new Camera();
        start();
    }

    /**
     * Game initialisation
     */
    public void start() {}

    /**
     * Update processing
     */
    public void update() {
        Sprite.updateSprites();
        GameObject.updateObjects(tick);
        if (camera != null)
            camera.update(tick);
        tick++;
    }

    public void preDraw(Graphics2D g) {
    }

    public void draw(Graphics2D g) {
        camera.fitScene(g);
        GameObject.drawObjects(g);
        g.dispose();
    }

    public void postDraw(Graphics2D g) {
    }

    public void keyPressed(KeyEvent e) {
        Input.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        Input.keyReleased(e);
    }

    public void applyGUI(GameWindow gui) {
        this.gui = gui;
    }

    public void setCamera(Camera cam) {
        this.camera = cam;
    }
}
