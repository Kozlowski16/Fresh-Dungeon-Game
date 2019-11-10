package Entities;

import java.awt.*;

public class Orc extends Entity {

    public Orc(String sprite, String name, int x, int y) {
        super(sprite, name, x, y);
        solid = true;
        this.attack = 10;
        this.maxHP = 100;
        this.HP = maxHP;
        this.armor = 0;
    }

    //TODO
    @Override
    public Point getMovement(int[][] map) {
        return null;
    }

    //TODO
    @Override
    public void interact(Entity entity) {
        this.attack(entity);
    }

}
