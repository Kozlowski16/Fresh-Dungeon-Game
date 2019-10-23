package map;

import gameobjects.BasicEnemy;
import gameobjects.Orc;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    public ArrayList<BasicEnemy> enemies;
    Tile[][] tiles;

    public Map() {
        enemies = new ArrayList<BasicEnemy>();
        Floor floor = new Floor();
        floor.printFloor();
        tiles = floor.getMap();
        tiles[2][2].gameObject = new Orc("player.png", "Test UNIT");
    }

    public int getHeight() {
        return tiles.length;
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void render(Graphics g, int renderX, int renderY, int x, int y) {
        tiles[y][x].render(g, renderX, renderY);
    }
}
