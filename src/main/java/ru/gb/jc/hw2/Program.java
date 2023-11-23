package ru.gb.jc.hw2;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final char DOT_HUMAN = 'X';      // Фишка игрока
    private static final char DOT_AI = 'O';         // Фишка ИИ
    private static final char DOT_EMPTY = '*';      // Признак пустого поля
    private static final Scanner scanner = new Scanner(System.in);      // Сканер
    private static final Random random = new Random();  // Генератор случайных чисел

    private static char[][] field;                  // Двумерный массив, хранящий теущее состояние игрового поля.
    private static int fieldSizeX;                  // Размерность игрового поля (X и Y).
    private static int fieldSizeY;

    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                AITurn();
                printField();
                if (checkGameState(DOT_AI, "Компьютер победил!"))
                    break;
            }
            System.out.println("Желаете сыграть ещё раз? (Y - да)");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    static void initialize() {                // Инициализация игрового поля
        fieldSizeX = 3;
        fieldSizeY = 3;

        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    private static void printField() {      // Вывод игрового поля
        System.out.print("+");
        for (int i = 0; i<fieldSizeX; i++) {    // Вывод хедера
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++) {      // Вывод поля
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {      // Вывод футера
            System.out.print("-");
        }
        System.out.println();
    }

    static void humanTurn() {           // Ход игрока (человек)
        int x;
        int y;

        do {
            //System.out.println("Введите координаты хода X и Y (от 1 до 3) через пробел: ");
            System.out.println("Ход игрока...");
            System.out.println("Введите координаты хода X и Y (от 1 до " + fieldSizeX + ") через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;


        }
        while(!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    static void AITurn() {              // Ход игрока (ИИ)
        int x;
        int y;
        System.out.println("Ход ИИ...");
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while(!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

        static boolean isCellEmpty(int x, int y) {      // Проверка пуста ли ячейка поля
        return field[x][y] == DOT_EMPTY;
    }

    static boolean isCellValid(int x, int y) {      // Проверка доступности ячейки поля
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    static boolean checkDraw() { // Проверка на ничью
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    static boolean checkWin(char dot) {      //Проверка победы игрока (фишка)
        // Проверка по 3-м горизонталям
        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;

        // Проверка по 3-м вертикалям
        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;

        // Проверка по 2-м диагоналям
        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;

        return false;
    }

    static boolean checkGameState(char dot, String s){      // Проверка состояния игры (фишка,слоган победный)
        if (checkWin(dot)){
            System.out.println(s);
            return true;
        }
        if (checkDraw()){
            System.out.println("Игра закончена в НИЧЬЮ!");
        }
        return false; // Игра продолжается
    }
}

