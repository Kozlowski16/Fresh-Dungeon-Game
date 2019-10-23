package gameobjects;

import main.Game;

import java.awt.*;

public abstract class GameObject {
    public String sprite;
    protected String name;
    protected boolean solid;// can the player walk through it;

    protected void setName(String name) {
        this.name = name;
    }

    protected GameObject(String sprite, String name) {
        this.name = name;
        this.sprite = sprite;
    }

    public void render(Graphics g, int x, int y) {
        Image img = Game.getInstance().getSpriteHandler().getSprite(sprite);
        g.drawImage(img, x, y, null);
    }
}
