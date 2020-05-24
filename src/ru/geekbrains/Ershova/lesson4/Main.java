package ru.geekbrains.Ershova.lesson4;

import java.lang.management.PlatformLoggingMXBean;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static final int SIZE_X = 5;
    static final int SIZE_Y = 5;
    static final char PLAYER_DOT = 'X';
    static final char AI_DOT = '0';
    static final char EMPTY_DOT = '.';
    static final int TO_WIN = 4;

    static char[][] field = new char[SIZE_Y][SIZE_X];
    static Scanner scanner = new Scanner(System.in);
    static Random random  = new Random();

    static void initMap(){
        for(int i = 0; i < SIZE_Y; i++){
            for(int j = 0; j < SIZE_X; j++){
                field [i][j] = EMPTY_DOT;
            }
        }
    }

    static void printMap(){
        System.out.println("-------");
        for( int i = 0; i < SIZE_Y; i++){
            System.out.println("|");
            for(int j = 0; j < SIZE_X; j++){
                System.out.println(field[i][j]+ "|");
            }
            System.out.println();

        }
        System.out.println("-------");
    }

    static void setSym(int y, int x, char sym){
        field[y][x] = sym;
    }

    static void playerStep(){
        int x;
        int y;
        do{
            System.out.println("Введите координаты X от 1 до + SIZE_X + и Y от 1 до + SIZE_Y +");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }while (!isCellValid(y,x));
        setSym(y, x, PLAYER_DOT);
    }

    // Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.

    static void aiStep(){

        // Ищем наиболее выйгрышный ход компьютера
        for(int i = 0; i < SIZE_Y; i++){
            for(int j = 0; j < SIZE_X; j++){
                if(isCellValid(i,j)){
                    setSym(i, j, AI_DOT);
                    if(checkWin(AI_DOT)) return;
                    setSym(i, j, EMPTY_DOT);
                }
            }
        }

        // Проверяем игрока на выйгрышный ход
        for(int i = 0; i < SIZE_Y; i++){
            for(int j = 0; j < SIZE_X; j++){
                if(isCellValid(i,j)){
                    setSym(i, j, PLAYER_DOT);
                    if(checkWin(PLAYER_DOT)){
                        setSym(i, j, AI_DOT);
                        return;
                    }
                    setSym(i, j, EMPTY_DOT);

                }

            }
        }

        // Если ничего выйгрышного нет
        int x;
        int y;
        do{
            x = random.nextInt(SIZE_X);
            y = random.nextInt(SIZE_Y);
        }while (!isCellValid(y, x));
        setSym(y, x, AI_DOT);

    }

    static boolean isCellValid(int x, int y){
        if(x < 0 || y < 0 || x >= SIZE_X || y >= SIZE_Y){
            return false;
        if (field[y][x] == EMPTY_DOT){
            return true;
        } else return false;

        }
    }

    static boolean isFull(){
        for(int i = 0; i < SIZE_Y; i++){
            for(int j = 0; j < SIZE_X; j++){
                if(field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }
    // 2. Переделать проверку победы, чтобы она не была реализована просто набором условий, например,
    // с использованием циклов.
    //3. * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4.
    // Очень желательно не делать это просто набором условий для каждой из возможных ситуаций;

    static boolean checkWin(char sym){
        for(int i = 0; i < SIZE_Y; i++){
            for(int j = 0; j < SIZE_X; j++){
                if(checkLine(i, j, 0, 1, sym)) return true; // Проверка линии по Х
                if(checkLine(i, j, 1, 1, sym)) return true; // Проверка диагонали ХУ
                if(checkLine(i, j, 1, 0, sym)) return true; // Проверка линии по У
                if(checkLine(i, j, -1, 1, sym)) return true; // Проверка диагонали Х -У
            }
        }
    }

    static boolean checkLine(int x, int y, int vx, int vy, char sym){
        int wayX = x + (TO_WIN - 1) * vx;
        int wayY = y + (TO_WIN - 1) * vy;
        if(wayX < 0 || wayY < 0 || wayX >= SIZE_X || wayY >= SIZE_Y) return false;
        for(int i = 0; i < TO_WIN; i++){
            int itemX = x + i * vx;
            int itemY = y + i * vy;
            if(field[itemY][itemY] = !sym) {
                return false;
            }

        }
        return true;
    }

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true){
            playerStep();
            printMap();
            if(checkWin(PLAYER_DOT)){
                System.out.println("Поздравляю, Вы выйграли");
                break;
            }
            if(isFull()){
                System.out.println("Ничья");
                break;
            }
            aiStep();
            printMap();
            if(checkWin(AI_DOT)){
                System.out.println("Компьютер победил");
                break;
            }
            if(isFull()){
                System.out.println("Ничья");
                break;
            }


        }
    }
}
