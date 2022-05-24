package engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Sprite {

    ArrayList<BufferedImage> images = new ArrayList<>();

    public int interframe_time = 60;
    public int interframe_timer = 0;
    public int interframe_speed = 15;
    public int image_index = 0;

    Vector2D origin;
    Vector2D scale = new Vector2D(1, 1);

    public final static int OFFSET_TOP_LEFT = 0;
    public final static int OFFSET_TOP_CENTER = 1;
    public final static int OFFSET_CENTER = 2;
    public final static int OFFSET_BOTTOM_CENTER = 3;

    public Sprite(String[] filenames, int origin_x, int origin_y, int interframe_speed, int interframe_time) {
        try {
            int i = 0;
            for(String filename : filenames) {
                System.out.println("/" + filename + ".png");
                java.net.URL in = Sprite.class.getResource("/sprites/" + filename + ".png");

                System.out.println(in);

                images.add(ImageIO.read(
                        in
                ));

                // check for sub-image size
                if (images.size() > 1) {
                    BufferedImage im = images.get(images.size()-1);
                    BufferedImage im_prev = images.get(images.size()-2);

                    if (im.getWidth() != im_prev.getWidth() || im.getHeight() != im_prev.getHeight())
                        throw new Exception("[!] Sub-images have to be of same size!");
                }
            }

            this.origin = new Vector2D(origin_x, origin_y);
            this.interframe_speed = interframe_speed;
            this.interframe_time = interframe_time;

            sprites.add(this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * @param filenames String array of sprite image files to be used. Sprite image files need to share dimensions.
     * @param origin_x Origin x on the sprite
     * @param origin_y Origin y on the sprite
     */
    public Sprite(String[] filenames, int origin_x, int origin_y) {
        this(filenames, origin_x, origin_y, 1, 60);
    }

    /**
     * @param filename Sprite image file to be used
     * @param origin_x Origin x on the sprite
     * @param origin_y Origin y on the sprite
     */
    public Sprite(String filename, int origin_x, int origin_y) {
        this(new String[]{filename}, origin_x, origin_y);
    }

    public void setScale(double w, double h) {
        scale = new Vector2D(w, h);
    }

    public void drawPart(Graphics2D g, double x, double y, int top_x, int top_y, int w, int h) {
        AffineTransform t = g.getTransform();
        double tx = t.getTranslateX();
        double ty = t.getTranslateY();
        double sx = t.getScaleX();
        double sy = t.getScaleY();

        double ox = -origin.getX();
        double oy = -origin.getY();

        int drawx = (int)Math.ceil(x/scale.x);
        int drawy = (int)Math.ceil(y/scale.y);

        g.scale(scale.getX(), scale.getY());
        g.translate(ox,oy);

        g.drawImage(images.get(image_index).getSubimage(top_x, top_y, w, h), drawx, drawy, null);
        g.translate(-ox,-oy);
        g.scale(1/scale.getX(), 1/scale.getY());
    }

    public void draw(Graphics2D g, double x, double y) {

        double ox = -origin.getX();
        double oy = -origin.getY();

        int drawx = (int)Math.ceil(x/scale.x);
        int drawy = (int)Math.ceil(y/scale.y);

        g.scale(scale.getX(), scale.getY());
        g.translate(ox,oy);
        g.drawImage(images.get(image_index), drawx, drawy, null);
        g.translate(-ox,-oy);
        g.scale(1/scale.getX(), 1/scale.getY());
    }

    static ArrayList<Sprite> sprites = new ArrayList<>();

    public static void updateSprites() {
        for(Sprite sprite : sprites) {
            sprite.interframe_timer += sprite.interframe_speed;

            if (sprite.interframe_timer >= sprite.interframe_time) {
                sprite.image_index = (sprite.image_index + 1) % sprite.images.size();
                sprite.interframe_timer = 0;
            }
        }
    }

}
