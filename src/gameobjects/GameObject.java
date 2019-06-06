package gameobjects;

public abstract class GameObject {
    public String sprite;
    protected String name;
    protected boolean solid;// can the player walk through it;

    protected void setName(String name) {
        this.name = name;
    }

    public void render() {

    }
}
