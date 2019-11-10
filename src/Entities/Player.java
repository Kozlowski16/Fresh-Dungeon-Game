package Entities;

import java.awt.*;

public class Player extends Entity {
    public Player(int x, int y) {
        super("player.png", "Player", x, y);
        this.attack = 7;
        this.maxHP = 40;
        this.HP = maxHP;
        this.armor = 10;
    }

    //TODO
    @Override
    public Point getMovement(int[][] map) {
        return null;
    }

    //TODO
    @Override
    public void interact(Entity entity) {

    }
}
