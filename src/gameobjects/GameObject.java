package gameobjects;

import Entities.Entity;
import main.Game;

import java.awt.*;

public abstract class GameObject {
    public String sprite;
    public boolean solid;// can the player walk through it;
    protected String name;

    protected GameObject(String sprite, String name) {
        this.name = name;
        this.sprite = sprite;
    }

    public abstract void interact(Entity entity);

    public void render(Graphics g, int x, int y) {
        Image img = Game.getInstance().getSpriteHandler().getSprite(sprite);
        g.drawImage(img, x, y, null);
    }

    public String toString() {
        return name + " Solid: " + solid;
    }
}
