package map;

import gameobjects.Entity;

//TODO
public class BasicTile extends Tile {
    public BasicTile(String sprite) {
        super(sprite);
    }

    public BasicTile(String sprite, Entity gameObject) {
        super(sprite, gameObject);
    }
}
