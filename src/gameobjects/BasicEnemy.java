package gameobjects;

public abstract class BasicEnemy extends Entity {
    public PathNode path;

    protected BasicEnemy(String sprite, String name) {
        super(sprite, name);
    }

    public void pathFind() {

    }
}
