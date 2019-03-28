import java.util.Scanner;

public class TicTacToe {
    /*
      Блок настроек игры
    */
    private static char[][] map; // матрица игры
    private static int size = 3; // размерность поля

    private static final char dot_empty = ' '; // пустой символ - свободное поле
    private static final char dot_x = 'X'; // крестик
    private static final char dot_o = 'O'; // нолик

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initMap();
        printMap();

        while(true) {
            humanTurn(); // ход человека
            if (isEndGame(dot_x)) {
                break;
            }

//            computerTurn();// ход компьютера
//            if (isEndGame(dot_o)) {
//                 break;
//            }
        }
        System.out.println(" Игра закончена");
    }
    /*
      Метод подготовки игрового поля
    */
    private static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = dot_empty;
            }
        }
    }
    /*
      Метод вывода игрового поля на экран
    */
    private static void printMap() {
        for (int i = 0; i <= size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
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

        map[y][x] = dot_x;
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
        if (map[y][x] != dot_empty) {
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
                if (map[i][j] == dot_empty) {
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
        return true;
    }
}
