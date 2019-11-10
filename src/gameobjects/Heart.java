package gameobjects;

import Entities.Entity;
import Entities.Player;

import java.util.function.Function;

public class Heart extends GameObject {
    Function<Entity, Entity> function;

    protected Heart(String sprite, String name) {
        super(sprite, name);
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Player) {
            entity.HP += entity.maxHP / 10;
        } else {
            System.out.println(entity + " cannot interact with Heart");
        }
    }

}
