package engine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class GameObject {
    public boolean visible = true;  // Does the object get drawn?
    public boolean kill = false;    // If true, the object won't be part of the next update iteration

    public Vector2D position;
    public Vector2D previous_position;
    public Vector2D velocity;

    public int depth = 0;   // Order of updating/drawing

    public void update(int tick) {};
    public void draw(Graphics2D g) {};

    public void keyPressed(KeyEvent e) {};
    public void keyReleased(KeyEvent e) {};

    public static ArrayList<GameObject> getObjects() {
        return game_objects;
    }

    public static void updateObjects(int tick) {
        ArrayList<GameObject> next_iteration = new ArrayList<>();

        for(GameObject obj : game_objects) {
            obj.update(tick);

            obj.previous_position = obj.position;
            obj.position = obj.position.add(obj.velocity);

            if (!obj.kill)
                next_iteration.add(obj);
        }

        // sort based on depth (for drawing)
        next_iteration.sort(Comparator.comparingInt((GameObject o) -> o.depth));

        game_objects = next_iteration;
    }

    public static void drawObjects(Graphics2D g) {
        for(GameObject obj : GameObject.getObjects())
            if (obj.visible)
                obj.draw(g);
    }

    /**
     * @param other The object to calculate the distance of to this one
     * @return The distance between the two objects
     */
    public double distanceToObject(GameObject other) {
        return other.position.add(position.multiply(-1)).length();
    }

    /**
     * @param c The class of a particular object
     * @return The closest object of class c to this object
     */
    public GameObject getNearest(Class c) {
        GameObject nearest = null;
        double shortest_distance = Double.POSITIVE_INFINITY;

        for(GameObject obj : game_objects) {
            if (c.isInstance(obj)) {
                double dist = distanceToObject(obj);
                if (dist < shortest_distance) {
                    shortest_distance = dist;
                    nearest = obj;
                }
            }
        }

        return nearest;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void kill(GameObject obj) {
        if (obj != null)
            obj.kill = true;
    }


    static ArrayList<GameObject> game_objects = new ArrayList<>();
    public GameObject(double x, double y) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(0, 0);

        game_objects.add(this);
    }
}

