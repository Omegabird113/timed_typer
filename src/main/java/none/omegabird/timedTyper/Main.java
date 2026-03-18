package none.omegabird.timedTyper;

import java.util.Scanner;

public class Main {
    protected static final String roundAutoSaveLocation = GameDataSaveManager.getGameDataPath() + "/" + ".lastGame";

    static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final GameData gd = Console.loadGameData(args, scanner);
        Console.play(gd, scanner);
        scanner.close();
    }
}