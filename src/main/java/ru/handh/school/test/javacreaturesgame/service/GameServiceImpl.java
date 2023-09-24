package ru.handh.school.test.javacreaturesgame.service;

import ru.handh.school.test.javacreaturesgame.exceptions.model.HealthTryException;
import ru.handh.school.test.javacreaturesgame.model.Monster;
import ru.handh.school.test.javacreaturesgame.model.Player;

import java.util.*;

public class GameServiceImpl implements GameService {
    private final Scanner scanner = new Scanner(System.in);
    private final Random rnd = new Random();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    private boolean isPlayerUpdated = false;
    private int howMuchMonsters = 1;
    private Player player;


    @Override
    public void startGame() {
        System.out.println("Creatures Game v1(c)");
        while (true) {
            printMainMenu();
            int number = checkAndReturnNumber(4);
            if (number == 4) break;
            switch (number) {
                case 1:
                    addPlayerParameters();
                    break;
                case 2:
                    addMonster();
                    break;
                case 3:
                    playGame();
                    break;
            }
        }
    }


    private void playGame() {
        if (!isPlayerUpdated) {
            player = new Player(rnd.nextInt(30 - 1 + 1) + 1,
                    rnd.nextInt(50 - 1 + 1) + 1,
                    rnd.nextInt(30 - 10 + 1) + 10,
                    rnd.nextInt(100 - 20 + 1) + 20);
        }
        for (int i = 0; i < howMuchMonsters; i++) {
            monsters.add(new Monster(rnd.nextInt(25 - 1 + 1) + 1,
                    rnd.nextInt(25 - 1 + 1) + 1,
                    rnd.nextInt(20 - 10 + 1) + 10,
                    rnd.nextInt(50 - 30 + 1) + 30));
        }
        while (true) {
            checkMonsters();
            printGameMenu();
            int number = checkAndReturnNumber(5);
            if (number == 5) return;
            switch (number) {
                case 1:
                    seeMonsters();
                    break;
                case 2:
                    attackMonster();
                    break;
                case 3:
                    int min = (int) (player.getDamage() - player.getDamage() * 0.2);
                    System.out.printf("У тебя %s здоровья, %s атаки, %s защиты, %s-%s урона\n",
                            player.getHealth(),
                            player.getAttack(),
                            player.getDefense(),
                            min,
                            player.getDamage());
                    break;
                case 4:
                    try {
                        player.healthUp();
                        player.setTryHealthUp(player.getTryHealthUp() - 1);
                        int health = (int) (player.getHealth() * 0.3);
                        System.out.printf("Ты вылечился на %s единиц\n", health);
                        System.out.printf("У тебя осталось %s аптечек\n", player.getTryHealthUp());
                    } catch (HealthTryException e) {
                        System.out.println("У тебя кончились аптечки");
                    }
                    break;
            }
        }
    }

    private void checkMonsters() {
        List<Monster> secondList = List.copyOf(monsters);
        for (int i = secondList.size(); i > 0; i--) {
            if (secondList.get(i - 1).getHealth() <= 0) {
                monsters.remove(i - 1);
            }
        }
        if (monsters.isEmpty()) {
            System.out.println("Ура победа, вы уничтожили всех монстров\n" +
                    "Спасибо что играли!");
            System.exit(0);
        }
    }

    private void seeMonsters() {
        Monster m;
        for (int i = 0; i < monsters.size(); i++) {
            m = monsters.get(i);
            if (m.getHealth() <= 0) continue;
            System.out.printf("Монстр%s(Здоровье:%s,Атака:%s,Защита:%s,Урон:%s)  ",
                    i + 1, m.getHealth(), m.getAttack(), m.getDefense(), m.getDamage());
            if ((i + 1) % 5 == 0) System.out.println();
        }
        System.out.println("\nКаждый из них будет отвечать твоей атаке в ответ");
    }

    private void attackMonster() {
        System.out.println(
                "Всего тут " + monsters.size() + " монстров, какого ты хочешь атаковать? (напиши цифру/число монстра)"
        );
        int number = checkAndReturnNumber(monsters.size()) - 1;
        int attackNumber = player.attack(monsters.get(number));
        if (attackNumber == 0) {
            System.out.printf("Игрок не пробил защиту монстра%s\n", number + 1);
        } else if (monsters.get(number).getHealth() >= 1) {
            System.out.printf("Игрок нанёс %s урона монстру%s\n", attackNumber, number + 1);
        } else if (monsters.get(number).getHealth() <= 0) {
            System.out.printf("Игрок нанёс сокрушительный удар в %s урона, и убил монстра%s\n", attackNumber, number + 1);
        }
        attackNumber = monsters.get(number).attack(player);

        if (attackNumber == 0) {
            System.out.printf("Монстр%s не пробил защиту игрока\n", number + 1);
        } else if (player.getHealth() >= 1) {
            System.out.printf("Монстр%s нанёс %s урона игроку\n", number + 1, attackNumber);
        } else if (player.getHealth() <= 0) {
            System.out.printf("Монстр нанёс сокрушительный удар в %s урона, и убил вас\n", attackNumber);
        }
        if (player.getHealth() <= 0) {
            System.out.println("Вас убили, вы проиграли");
            System.exit(0);
        }
    }

    private void addMonster() {
        System.out.println("Напишите сколько монстров вы хотите видеть в игре\n" +
                "Максимальное кол-во монстров = 20");
        howMuchMonsters = checkAndReturnNumber(20);
        System.out.println("Ваши изменения сохранены");
    }

    private void addPlayerParameters() {
        int attack = 0;
        int defence = 0;
        int damage = 0;
        int health = 0;
        while (true) {
            printPlayerUpdateMenu();
            int caseNumber = checkAndReturnNumber(5);
            if (caseNumber == 5) break; // Case мешает выходу из цикла
            switch (caseNumber) {
                case 1:
                    System.out.println("Введи число атаки от 1 до 30");
                    attack = checkAndReturnNumber(30);
                    System.out.println("Успешно");
                    break;
                case 2:
                    System.out.println("Введи число защиты от 1 до 30");
                    defence = checkAndReturnNumber(30);
                    System.out.println("Успешно");
                    break;
                case 3:
                    System.out.println("Введи число урона(999 max)");
                    damage = checkAndReturnNumber(999);
                    System.out.println("Успешно");
                    break;
                case 4:
                    System.out.println("Введи число здоровья(999 max)");
                    health = checkAndReturnNumber(999);
                    System.out.println("Успешно");
                    break;
            }
        }
        if (attack == 0) attack = rnd.nextInt(30 - 1 + 1) + 1;
        if (defence == 0) defence = rnd.nextInt(30 - 1 + 1) + 1;
        if (damage == 0) damage = rnd.nextInt(50 - 10 + 1) + 10;
        if (health == 0) health = rnd.nextInt(100 - 20 + 1) + 20;
        player = new Player(attack, damage, defence, health);
        isPlayerUpdated = true;
        System.out.println("Ваши изменения сохранены");
    }

    private int checkAndReturnNumber(int max) {
        while (true) {
            try {
                int number = scanner.nextInt();
                if (number > max) {
                    throw new NoSuchElementException();
                } else if (number <= 0) {
                    throw new NoSuchElementException();
                }
                return number;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Ошибка: вводить можно только цифры!!!");
                scanner.nextLine();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Ошибка: вводить можно цифры не больше " + max + "!!!");
                scanner.nextLine();
            } catch (RuntimeException e) {
                System.out.println("Ошибка: только положительные числа!!!");
                scanner.nextLine();
            }
        }
    }

    private void printMainMenu() {
        System.out.println("Введите нужную цифру для выбора \n" +
                "1. Ввести параметры для игрока (по умолчанию будет полный рандом) \n" +
                "2. Добавить определённое количество монстров (стандарт 1) \n" +
                "3. Начать игру. (Победой будет считаться полное уничтожение монстров) \n" +
                "4. Закрыть игру.");
    }

    private void printPlayerUpdateMenu() {
        System.out.println("Меню изменения параметров у персонажа \n" +
                "Введите нужную цифру для изменений параметров \n" +
                "1. Атаку \n" +
                "2. Защиту \n" +
                "3. Урон \n" +
                "4. Здоровье \n" +
                "5. Сохранить и выйти");
    }

    private void printGameMenu() {
        System.out.println("Выберите действие \n" +
                "Введите нужную цифру для действия \n" +
                "1. Посмотреть на монстров \n" +
                "2. Атаковать монстра\n" +
                "3. Посмотреть свои статы \n" +
                "4. Полечиться \n" +
                "5. Завершить игру");
    }
}
