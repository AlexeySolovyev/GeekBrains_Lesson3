import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    /*
      Блок настроек игры
    */
    private static char[][] map; // матрица поля игры
    private static int size = 3; // размерность поля игры

    private static final char DOT_EMPTY = ' '; // пустой символ - свободное поле, константы (final) пишутся в верхнем регистре и вместо пробелов ставим "_"
    private static final char DOT_X = 'X'; // крестик
    private static final char DOT_O = 'O'; // нолик

    private static final boolean SILLY_MODE = false; // переключение программы в "глупый режим" для компьютера
    private static final boolean SCORING_MODE = true; // переключение программы в smart режим для компьютера

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {

        initMap(); // инициализация игрового поля
        printMap(); // отображаем поле на экран

        while(true) {   // цикл игры

            humanTurn();  // ход человека, фаза 1
            if (isEndGame(DOT_X)) { // если игра закончилась
                break; // прерываем ход игры
            }

          computerTurn();  // ход компьютера, фаза 2
          if (isEndGame(DOT_O)) { // если игра закончилась
              break; // прерываем хлд игры
          }
        }
        System.out.println(" Игра закончена");
    }
    /*
      Метод подготовки (инициализации) игрового поля
    */
    private static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }
    /*
      Метод вывода игрового поля на экран
    */
    private static void printMap() {
        for (int i = 0; i <= size; i++) { // выводим сверху по горизонтали номера ячеек для удобства их определения человеком 1,2,3
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " "); // выводим слева по вертикали номера ячеек для удобства их определения человеком 1,2,3
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    /*
       Ход человека
    */
    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты ячейки через пробел");
            y = scanner.nextInt() - 1; // так как массив начинается с 0, и человек может запутаться, сделаем так, чтобы массив для него был как будто с 1
            x = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));

        map[y][x] = DOT_X;
    }
    /*
     Ход компьютера
   */
    private static void computerTurn() {
        int x = -1;
        int y = -1;
        if (SILLY_MODE) {

            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!isCellValid(x, y));
        } else {
            // Компьютер ищет свой ход
            // Компьютер решает, можно ли продолжить последовательность для выигрыша

            if (SCORING_MODE) {
                // Маркер того, что ход найден
                boolean moveFound = false;

                // Упрощенный алгоритм
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (map[i][j] == DOT_EMPTY) {
                            // проверяем направления
                            // влево вверх
                            if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("LU");
                            }
                            // вверх
                            else if (i - 1 >= 0 && map[i - 1][j] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("U");
                            }
                            // вправо вверх
                            else if (i - 1 >= 0 && j + 1 < size && map[i - 1][j + 1] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("RU");
                            }
                            // вправо
                            else if (j + 1 < size && map[i][j + 1] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("R");
                            }
                            // вправо вниз
                            else if (i + 1 < size && j + 1 < size && map[i + 1][j + 1] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("RD");
                            }
                            // вниз
                            else if (i + 1 < size && map[i + 1][j] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("D");
                            }
                            // лево вниз
                            else if (i + 1 < size && j - 1 >= 0 && map[i + 1][j - 1] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("LD");
                            }
                            // влево
                            else if (j - 1 >= 0 && map[i][j - 1] == DOT_O) {
                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("L");
                            }
                        }
                        // если ход найден, прерываем внутренний цикл
                        if (moveFound) {
                            break;
                        }
                    }
                    // если ход найден, перываем внешний цикл
                    if (moveFound) {
                        break;
                    }
                }
                // если ничего не нашли, делаем глупый ход
                if (x == -1) {
                    do {
                        x = random.nextInt(size);
                        y = random.nextInt(size);
                    } while (!isCellValid(x, y));
                    System.out.println("Random");
                }
            }
            System.out.println("Компьютер выбрал ячейку " + (y + 1) + " " + (x + 1));
            map[y][x] = DOT_O;
        }
    }
    /*
        Метод валидации запрашиваемой ячейки на корректность
        @param x - координата по горизонтали
        @param y - координата по вертикали
        @return boolean - признак валидности
    */
    private static boolean isCellValid(int x, int y) {
        boolean result = true;

        //проверка координаты
        if (x < 0 || x >= size || y < 0 || y >= size) {
            result = false;
        }

        //проверка заполненности ячейки
        if (map[y][x] != DOT_EMPTY) {
            result = false;
        }
        return result;
    }
    /*
    Метод проверки игры на зваершение
    @param playerSymbol символ, которым играет текущий игрок
    @return boolean - признак завершения игры
     */
    private static boolean isEndGame(char playerSymbol) {
        boolean result = false;

        printMap();

        // проверяем необходимость следующего хода
        if (checkWin(playerSymbol)) {
            System.out.printf("Победили " + playerSymbol + "!");
            result = true;
        }

        if (isMapFull()) {
            System.out.println("Ничья");
            result = true;
        }
        return result;
    }
    /*
    Проверка на 100% заполненность поля
    @return boolean признак оптимальности
     */
    private static boolean isMapFull() {
        boolean result = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    result = false;
                }
            }

        }
        return result;
    }
    /*
    Метод проверки выигрыша
    @param playerSymbol - символ введенный пользователем
    @return boolean - результат проверки
     */
    private static boolean checkWin(char playerSymbol) {
        boolean result = false;

        if (
                (map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol) ||
                (map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol) ||
                (map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol) ||
                (map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol) ||
                (map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol) ||
                (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol) ||
                (map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol) ||
                (map[2][0] == playerSymbol && map[1][1] == playerSymbol && map[0][2] == playerSymbol)) {
            result = true;
        }
        return result;
    }
}
