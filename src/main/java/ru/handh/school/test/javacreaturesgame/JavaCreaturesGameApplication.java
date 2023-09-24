package ru.handh.school.test.javacreaturesgame;


import ru.handh.school.test.javacreaturesgame.service.GameServiceImpl;

public class JavaCreaturesGameApplication {
    public static void main(String[] args) {
        GameServiceImpl game = new GameServiceImpl();
        game.startGame();
    }


}
