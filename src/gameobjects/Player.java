package gameobjects;

public class Player extends Entity {

    public Player(int x, int y) {
        super("player.png", "Player");

        maxHP = 100;
        HP = maxHP;
        this.x = x;
        this.y = y;
    }

}
