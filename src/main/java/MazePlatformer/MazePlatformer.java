package MazePlatformer;

import engine.*;
import MazePlatformer.entity.Player;
import java.awt.*;

public class MazePlatformer extends Game {

    Player player;

    Sprite health_heart = new Sprite(new String[]{"heart"}, 0, 0);
    Sprite health_empty = new Sprite(new String[]{"heart_empty"}, 0, 0);

    Sprite[] maze_sprites = {
        new Sprite("maze/down", 0, 0),
        new Sprite("maze/left", 0, 0),
        new Sprite("maze/corner", 0, 0),
    };

    public static int bestY = Integer.MIN_VALUE;

    public void start() {
        player = new Player(100,200);
        camera.setTarget(player);
    }

    public static void restart() {
        Main.game_instance.start();
    }

    // update
    public void update() {
        super.update();

        if (Input.get("cancel"))
            camera.scale(MathUtils.lerp(camera.getScale().getX(),.5,.2));
        else
            camera.scale(MathUtils.lerp(camera.getScale().getX(),1,.2));
    }


    public void draw(Graphics2D g) {
        camera.fitScene(g);

        // Background & maze

        double start_y = (int)(camera.getPosition().getY()/32);
        double start_x = (int)(camera.getPosition().getX()/32);

        g.setColor(Color.BLACK);
        g.fillRect((int) Math.floor(-camera.getDrawTranslation().getX()), (int)Math.floor(-camera.getDrawTranslation().getY()), gui.getWidth(), gui.getHeight());

        for(double y = start_y - 5; y < start_y + 5; y++) {
            for(double x = start_x - 8; x < start_x + 8; x++) {
                int type = Maze.getTypeAtCoords(x, y, true);
                double[] coords = Maze.getBBoxAtCoords(x, y);

                double cx = coords[0]+coords[2]/2;
                double cy = coords[1]+coords[3]/2;

                double d = camera.getPosition().distanceTo(new Vector2D(cx, cy));

                int br = (int) (255 -
                        255 * Math.max(Math.min(d / (125 / (1.5*camera.getScale().getX())), 255), 0)
                );

                br = Math.max(Math.min(br, 254), 0);

                int r = br;
                int b = br;
                int gr = br;

                if (Maze.specialBlock(x, y) == 1) {
                    b = (int) (br*.5);
                    gr = (int) (br*.5);
                }


                g.setColor(new Color(r, gr, b, 255));
                g.fillRect((int) coords[0], (int) coords[1], (int) coords[2], (int) coords[3]);

                // maze_sprites[type].draw(g, (int) x*32, (int) y*32);
            }
        }

        // Deepest y position
        g.setColor(new Color(255, 0, 0, 125));
        g.drawLine((int) -camera.getDrawTranslation().getX(), bestY, (int) (-camera.getDrawTranslation().getX() + gui.getWidth()), bestY);

        // Handle other game drawing like objects
        GameObject.drawObjects(g);

        // GUI layer
        camera.resetScene(g);

        // Health
        double w = 32 * (player.hp)/(double)player.max_hp;

        double float_factor_x = 32 - ((double)player.max_hp - player.hp)/player.max_hp * 16;
        double float_factor_y = 36 - ((double)player.max_hp - player.hp)/player.max_hp * 16;

        int heartx = player.heartshake_x/5 + (int)(Math.cos(tick/float_factor_x)*2);
        int hearty = (int)(Math.sin(tick/float_factor_y)*2);
        w = Math.max(Math.min(w,32), 1);
        health_empty.draw(g, 16 + heartx, 16 + hearty);
        health_heart.drawPart(g, 16 + heartx, 16  + hearty + 32-(int)w, 0, 32-(int)w, 32, (int)w);

        g.dispose();
    }

    public static void setBestY(int y) {
        bestY = Math.max(bestY, y);
    }

}
