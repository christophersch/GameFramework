package engine;

public class MathUtils {

    public static double lerp(double a, double b, double step) {
        return a + (b-a)*step;
    }

    public static double approach(double a, double b, double step) {
        if (a < b)
            return Math.min(a + step, b);
        else
            return Math.max(a - step, b);
    }
}
