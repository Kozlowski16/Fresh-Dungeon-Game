package map.tiles;

import Entities.Entity;
import map.Tile;

//TODO
public class BasicTile extends Tile {

    @Override
    public boolean isSolid() {
        return (getEntity() != null && getEntity().solid) || (gameObject != null && gameObject.solid);
    }

    public BasicTile(String sprite) {
        super(sprite);
    }

    public BasicTile(String sprite, Entity gameObject) {
        super(sprite, gameObject);
    }
}
