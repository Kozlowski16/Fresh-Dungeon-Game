package map;

import Entities.Entity;
import gameobjects.GameObject;
import main.Game;

import java.awt.*;

public abstract class Tile {
    private String sprite;
    public GameObject gameObject;
    public boolean discovered;
    public boolean visible;
    private Entity entity;

    public Tile(String sprite, GameObject gameObject) {
        this.sprite = sprite;
        this.gameObject = gameObject;
    }

    public Tile(String sprite, Entity entity) {
        this.sprite = sprite;
        this.entity = entity;
    }

    public abstract boolean isSolid();

    public void discover() {
        discovered = true;
        visible = true;
        if (entity != null) {
            //System.out.println("disvoerd");
            entity.active = true;
        }
    }

    public void loseSight() {
        visible = false;
    }

    public String getSprite() {
        return sprite;
    }

    public Tile(String sprite) {
        this.sprite = sprite;
    }

    public Entity getEntity() {
        return entity;
    }

    void setEntity(Entity entity) {
        this.entity = entity;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void render(Graphics g, int x, int y) {
        Image img;
        if (!discovered) {
            img = Game.getInstance().getSpriteHandler().getSprite("red.png");
        } else if (!visible) {
            img = Game.getInstance().getSpriteHandler().getSprite("wall.png");
        } else {
            img = Game.getInstance().getSpriteHandler().getSprite(sprite);
        }
        img = Game.getInstance().getSpriteHandler().getSprite(sprite);
        g.drawImage(img, x, y, null);
        if (gameObject != null) {
            //System.out.println(gameObject);
            gameObject.render(g, x, y);
        }
        if (entity != null) {
            //System.out.println(entity);
            entity.render(g, x, y);
        }
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
