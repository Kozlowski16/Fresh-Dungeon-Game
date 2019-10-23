package gameobjects;

public abstract class Entity extends GameObject {
    protected int HP;
    protected int maxHP;
    protected int armor;
    protected int x;
    protected int y;

    protected Entity(String sprite, String name) {
        super(sprite, name);
    }


}
