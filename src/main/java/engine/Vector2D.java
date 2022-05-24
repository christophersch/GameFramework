package engine;

public class Vector2D {
    double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x*scalar, this.y*scalar);
    }

    public double length() {
        return Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2));
    }

    public double distanceTo(Vector2D other) {
        return other.multiply(-1).add(this).length();
    }
    
    public void normalize() {
        double l = length();
        this.x /= l;
        this.y /= l;
    }
    
    public Vector2D clone() {
        return new Vector2D(this.x, this.y);
    }
    
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
