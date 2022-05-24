package engine;

import java.awt.event.KeyEvent;

public class Input {
    public boolean active = false;
    public boolean pressed = false;
    public int press_frames = 0;
    public boolean released = false;
    public int[] keyID;
    public String name = "";

    // List of Input key : event pairs
    // Todo: Include mouse & controller support
    static Input[] inputs = {
            new Input("left",   new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_A}),
            new Input("up",     new int[]{KeyEvent.VK_UP, KeyEvent.VK_A}),
            new Input("right",  new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_D}),
            new Input("down",   new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_S}),
            new Input("action", new int[]{KeyEvent.VK_Z}),
            new Input("cancel", new int[]{KeyEvent.VK_X})
    };

    /**
     * @param id  String identifier of the input
     * @return {@code true} if the input is being held down
     */
    public static boolean get(String id) {
        for(Input input : inputs) {
            if (input.name.equals(id))
                return input.active;
        }
        return false;
    }

    /**
     * @param id  String identifier of the input
     * @return {@code true} if the input is pressed, {@code false} if being held.
     */
    public static boolean getPressed(String id) {
        for(Input input : inputs) {
            if (input.name.equals(id) && input.active)
                return input.press_frames++ == 1;
        }
        return false;
    }


    public static void keyPressed(KeyEvent e) {
        for(Input input : inputs)
            for(int id : input.keyID)
                if (e.getKeyCode() == id) {
                    input.active = true;
                    break;
                }

    }

    public static void keyReleased(KeyEvent e) {
        for(Input input : inputs)
            for(int id : input.keyID)
                if (e.getKeyCode() == id) {
                    input.active = false;
                    input.press_frames = 0;
                    break;
                }
    }

    private Input(String name, int[] keyID) {
        this.name = name;
        this.keyID = keyID;
    }
}
