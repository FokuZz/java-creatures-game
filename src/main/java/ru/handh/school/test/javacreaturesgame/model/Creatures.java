package ru.handh.school.test.javacreaturesgame.model;


import lombok.Builder;
import lombok.Data;

import java.util.Random;

@Data
@Builder
public class Creatures<T extends Creatures> {

    private Integer attack;

    private Integer damage;

    private Integer defense;

    private Integer health;

    public int attack(T creature) {
        int attackModifier = attack - creature.getDefense();
        if (attackModifier > 0) {
            if (throwTheCube(attackModifier)) {
                int damageNow = howMuchDamage();
                creature.setHealth(creature.getHealth() - damageNow);
                return damageNow;
            }
            return 0;
        } else {
            if (throwTheCube(1)) {
                int damageNow = howMuchDamage();
                creature.setHealth(creature.getHealth() - damageNow);
                return damageNow;
            }
            return 0;
        }
    }

    private int howMuchDamage() {
        int maximum = damage;
        double minimum = damage - damage * 0.2; // Я решил сделать диапазон атаки меньше на 20 процентов
        return new Random().nextInt(maximum - (int) minimum + 1) + (int) minimum;
    }

    private boolean throwTheCube(int cubes) {
        int maximum = 6;
        int minimum = 1;
        for (int i = 0; i < cubes; i++) {
            int randomNum = new Random().nextInt(maximum - minimum + 1) + minimum;
            if (randomNum >= 5) {
                return true;
            }
        }
        return false;
    }
}
