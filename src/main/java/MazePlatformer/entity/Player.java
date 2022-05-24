package MazePlatformer.entity;

import MazePlatformer.MazePlatformer;
import MazePlatformer.Maze;
import engine.*;

import java.awt.*;

public class Player extends GameObject {
    static Sprite sprite = new Sprite(new String[]{"player/temp0","player/temp1"}, 8, 16, 1, 60);

    public int max_hp = 200;
    public int hp = max_hp;
    public int heartshake_x = 0;

    boolean onGround = false;

    public Player(double x, double y) {
        super(x, y);
    }

    @Override
    public void update(int tick) {
        double acc = .5;

        if (Input.get("left"))
            velocity.setX(MathUtils.lerp(velocity.getX(),-2, acc));
        else if (Input.get("right"))
            velocity.setX(MathUtils.lerp(velocity.getX(),2, acc));
        else
            velocity.setX(MathUtils.lerp(velocity.getX(),0, acc));

        if (Math.abs(velocity.getX()) < .1) {
            sprite.interframe_speed = 0;
            sprite.image_index = 0;
        }
        else {
            if (sprite.interframe_speed == 0)
                sprite.image_index = 1;
            sprite.interframe_speed = 10;
        }

        velocity.setY(velocity.getY() + 0.1);

        Vector2D n = position.add(velocity);
        double nx = Math.round(n.getX());
        double ny = Math.round(n.getY());

        onGround = false;

        // super simple collision detection
        // load collision bounds in a 3x3 block area around the player
        for(int x = (int)(nx/32) - 1; x < (int)(nx/32) + 1; x++) {
            for(int y = (int)(ny/32) - 1; y < (int)(ny/32) + 1; y++) {
                double[] bbox = Maze.getBBoxAtCoords(x, y);


                if (!Input.get("action")) {

                    if (position.getX() >= bbox[0] && position.getX() <= bbox[0] + bbox[2] && position.getY() >= bbox[1] && position.getY() <= bbox[1] + bbox[3]) {
                        velocity.setX(0);
                        velocity.setY(0);
                    }
                    if (nx >= bbox[0] && nx <= bbox[0] + bbox[2] && position.getY() >= bbox[1] && position.getY() <= bbox[1] + bbox[3]) {
                        velocity.setX(0);
                    }

                    // ground collision
                    if (position.getX() >= bbox[0] && position.getX() <= bbox[0] + bbox[2] && ny >= bbox[1] && ny <= bbox[1] + bbox[3]) {
                        // fall damage
                        if (velocity.getY() > 4) {
                            // shake heart upon damage
                            heartshake_x = 10;
                            hp -= 2 * Math.pow(velocity.getY(), 2);
                        }
                        velocity.setY(0);
                        if (position.getY() < bbox[1] + bbox[3]) {
                            position.setY(bbox[1] - .1);
                            onGround = true;
                        }
                    }
                }
            }
        }

        if (Maze.specialBlock((int)Math.floor(position.getX()/32), (int)Math.floor(position.getY()/32)) == 1 && tick % 2 == 0) {
            hp = Math.max(Math.min(hp+1,max_hp), 0);
        }

        if (hp <= 0) {
            kill = true;
            MazePlatformer.setBestY((int)position.getY());
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            MazePlatformer.restart();
                        }
                    },
                    1000
            );
        }

        if (Math.abs(heartshake_x) > 0 && tick % 3 == 0)
            heartshake_x = (Math.abs(heartshake_x) - 1) * ((int)-Math.signum(heartshake_x));

        depth = (int)position.getY();
    }

    @Override
    public void draw(Graphics2D g) {

        sprite.draw(g, position.getX(), position.getY());
    }
}
