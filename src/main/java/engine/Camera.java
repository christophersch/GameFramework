package engine;

import java.awt.*;

public class Camera {
    Vector2D position = new Vector2D(0, 0);
    Vector2D scale = new Vector2D(1, 1);
    GameObject target = null;

    public Camera() {
    }

    public Camera(double x, double y) {
        position = new Vector2D(x,y);
    }

    public Camera(GameObject target) {
        this.target = target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public void update(int tick) {
        if (target != null) {
            position.setX(Math.ceil(target.position.getX()));
            position.setY(Math.ceil(target.position.getY()));
        }
    }

    public Vector2D getDrawTranslation() {
        Vector2D ret = new Vector2D(0, 0);

        ret.setX(position.getX() - GameWindow.GAME_WIDTH/2.0/scale.getX());
        ret.setY(position.getY() - GameWindow.GAME_HEIGHT/2.0/scale.getY());

        ret = ret.multiply(-1);

        return ret;
    }

    public void fitScene(Graphics2D g) {
        Vector2D translation = getDrawTranslation();

        g.scale(scale.getX() * GameWindow.window_scale, scale.getY() * GameWindow.window_scale);
        g.translate(translation.getX(), translation.getY());
    }

    public void resetScene(Graphics2D g) {
        g.translate(-getDrawTranslation().getX(), -getDrawTranslation().getY());
        g.scale( 1/scale.getX(), 1/scale.getY());
    }

    public Vector2D setScale() {return scale;}
    public Vector2D getScale() {
        return scale;}

    public void scale(double s) {
        scale.setX(s);
        scale.setY(s);
    }

    public Vector2D getPosition() {
        return position;
    }
}
