package map;

import gameobjects.Entity;
import main.Game;

import java.awt.*;

public class tile {
    private String sprite;
    private Entity gameObject;

    public String getSprite() {
        return sprite;
    }

    public tile(String sprite) {
        this.sprite = sprite;
    }

    public tile(String sprite, Entity gameObject) {
        this.sprite = sprite;
        this.gameObject = gameObject;
    }

    public Entity getGameObject() {
        return gameObject;
    }

    public void render(Graphics g, int x, int y) {

        Image img = Game.sprites.getSprite(sprite);
        g.drawImage(img, x, y, null);
    }

    public void setGameObject(Entity gameObject) {
        this.gameObject = gameObject;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
