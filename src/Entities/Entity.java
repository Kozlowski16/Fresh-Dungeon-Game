package Entities;

import main.Game;
import util.PathNode;
import util.Sprite;

import java.awt.*;

public abstract class Entity {
    public int HP;
    public int maxHP;
    public boolean active;
    public boolean solid;
    public PathNode path;
    public int x;
    public int y;
    protected int armor;
    protected int attack;
    private String name;
    //TODO
    // private Sprite sprite;
    private String sprite;

    protected Entity(String sprite, String name, int x, int y) {
        this.sprite = sprite;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g, int x, int y) {
        Image img = Game.getInstance().getSpriteHandler().getSprite(sprite);
        g.drawImage(img, x, y, null);
    }

    public abstract Point getMovement(int[][] map);

    public abstract void interact(Entity entity);

    public void attack(Entity entity) {
        int damage = this.attack - (int) (Math.random() * entity.armor);
        entity.HP = entity.HP - Math.max(damage, 1);
    }

    public String toString() {
        return name + " Active: " + active + " (" + x + ", " + y + ")";
    }

}
