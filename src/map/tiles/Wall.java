package map.tiles;

import map.Tile;

//TODO
public class Wall extends Tile {
    @Override
    public boolean isSolid() {
        return true;
    }

    public Wall(String sprite) {
        super(sprite);
    }
}
