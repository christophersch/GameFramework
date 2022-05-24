package MazePlatformer;

import java.util.Random;

public class Maze {
    static Random maze_gen = new Random();

    public static int specialBlock(double x, double y) {
        int a = 23;
        int b = 56;
        int c = 13;
        maze_gen.setSeed((long)(a*x*y + b*x + c*y));
        int special = maze_gen.nextInt(100);

        if (special < 4)
            return 1;
        return 0;
    }

    // Maze stuff
    public static int getTypeAtCoords(double x, double y, boolean recurse) {

        // magic values
        int a = 294;
        int b = 687;
        int c = 421;
        maze_gen.setSeed((long)(a*x*y + b*x + c*y));
        int type = maze_gen.nextInt(3);

        if (!recurse)
            return type;

        //
        if (type == 0) {
            if (getTypeAtCoords(x + 1,y,false) == 1)
                return 2;

        // delete as many singular blocks as possible
        } else if (type == 2)
            if (getTypeAtCoords(x - 1, y,false) != 0 || getTypeAtCoords(x, y+1,false) != 1)
                return maze_gen.nextInt(2);

        return type;
    }

    public static double[] getBBoxAtCoords(double x, double y) {
        int type = getTypeAtCoords(x, y, true);

        x = (int) x;
        y = (int) y;

        double[] bbox = new double[4];

        switch(type) {
            case 0:
                bbox[0] = x * 32;
                bbox[1] = y * 32 + 16;
                bbox[2] = 32;
                bbox[3] = 16;
                break;
            case 1:
                bbox[0] = x*32;
                bbox[1] = y*32;
                bbox[2] = 16;
                bbox[3] = 32;
                break;
            case 2:
                bbox[0] = x*32;
                bbox[1] = y*32+16;
                bbox[2] = 16;
                bbox[3] = 16;
                break;

        }

        return bbox;
    }
}
