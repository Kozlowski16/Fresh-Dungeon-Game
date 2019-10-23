package map;

import gameobjects.Entity;
import gameobjects.GameObject;
import main.Game;

import java.awt.*;

public class Tile {
    private String sprite;
    public GameObject gameObject;

    public String getSprite() {
        return sprite;
    }

    public Tile(String sprite) {
        this.sprite = sprite;
    }

    public Tile(String sprite, Entity gameObject) {
        this.sprite = sprite;
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void render(Graphics g, int x, int y) {
        Image img = Game.getInstance().getSpriteHandler().getSprite(sprite);
        g.drawImage(img, x, y, null);
        if (gameObject != null) {
            gameObject.render(g, x, y);
        }
    }

    public void setGameObject(Entity gameObject) {
        this.gameObject = gameObject;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
