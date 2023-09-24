package ru.handh.school.test.javacreaturesgame.exceptions.model;

public class HealthTryException extends RuntimeException {

    public HealthTryException() {
        super("Игрок использовал максимальное число попыток для восстановления здоровья");
    }
}
