package ru.handh.school.test.javacreaturesgame.model;

import lombok.Getter;
import lombok.Setter;
import ru.handh.school.test.javacreaturesgame.exceptions.model.HealthTryException;


public class Player extends Creatures {

    public Player(Integer attack, Integer damage, Integer defense, Integer health) {
        super(attack, damage, defense, health);
    }

    @Getter
    @Setter
    private int tryHealthUp = 4;

    private int maxHealth = getHealth();

    public int healthUp() {
        if (tryHealthUp != 0) {
            double healthUp = maxHealth * 0.3;
            setHealth(getHealth() + (int) healthUp);
            if (getHealth() > maxHealth) setHealth(maxHealth);
            return (int) healthUp;
        } else {
            throw new HealthTryException();
        }
    }
}
