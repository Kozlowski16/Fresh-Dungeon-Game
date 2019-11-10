package map;

import Entities.Entity;
import Entities.Orc;
import Entities.Player;
import map.tiles.BasicTile;
import map.tiles.Wall;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private static boolean playerExists = false;
    public ArrayList<Entity> enemies;
    public Point start;
    public Point exit;
    private Tile[][] tiles;
    public Map() {
        enemies = new ArrayList<Entity>();
        Floor floor = new Floor();
        start = floor.start;
        exit = floor.exit;
        convertMap(floor.map);
        floor.printFloor();
    }

    public void initPlayer(Player player) {
        if (playerExists) {
            System.out.println("#################################\nERROR THIS SHOULD NEVER RUN MORE THAN ONCE\nERROR THIS SHOULD NEVER RUN MORE THAN ONCE\n#################################");
        }
        playerExists = true;
        tiles[player.y][player.x].setEntity(player);
    }

    private void checkMonsters() {
        for (Entity basicEnemy : enemies) {
            if (tiles[basicEnemy.y][basicEnemy.x].getEntity() != basicEnemy) {
                System.out.println("ERROR:" + basicEnemy);
            }
        }
    }

    private void convertMap(char[][] map) {
        tiles = new Tile[map.length][map[0].length];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (map[y][x] == '#') {
                    tiles[y][x] = new Wall("wall.png");
                } else if (map[y][x] == '1') {
                    tiles[y][x] = new BasicTile("notwall.png");
                    Entity bad = new Orc("Bad.png", "Orc", x, y);
                    tiles[y][x].setEntity(bad);
                    enemies.add(bad);
                } else {
                    tiles[y][x] = new BasicTile("notwall.png");
                }
            }
        }
        tiles[exit.getY()][exit.getX()] = new BasicTile("stairs.png");
        tiles[start.getY()][start.getX()] = new BasicTile("notwall.png");
        checkMonsters();
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

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public Tile getTile(java.awt.Point p) {
        return tiles[p.y][p.x];
    }

    public void render(Graphics g, int renderX, int renderY, int x, int y) {
        tiles[y][x].render(g, renderX, renderY);
    }

    public void moveEntity(Entity entity, int x, int y) {
        if (tiles[entity.y][entity.x].getEntity() != entity) {
            System.out.println("ERROR:");
        }
        tiles[entity.y][entity.x].setEntity(null);
        tiles[y][x].setEntity(entity);
        entity.y = y;
        entity.x = x;
        checkMonsters();
    }

    public int[][] getIntMap() {
        int[][] out = new int[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (tiles[y][x].isSolid()) {
                    out[y][x] = tiles[y][x].getEntity() == null ? -1 : 99;
                } else {
                    out[y][x] = 99;
                }
            }
        }
        return out;
    }

    public int getActiveBad() {
        int total = 0;
        for (int i = 0; i < enemies.size(); i++) {
            if (tiles[enemies.get(i).y][enemies.get(i).x].discovered) {
                enemies.get(i).active = true;
                total++;
            }
        }
        return total;
    }
}
