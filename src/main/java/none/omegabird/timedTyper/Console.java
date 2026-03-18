package none.omegabird.timedTyper;

import java.util.Scanner;

class Console {
    private static final String roundAutoSaveLocation = GameDataSaveManager.getGameDataPath() + "/" + ".lastGame";

    private static GameData askForLastGameLoad(Scanner scanner) {
        GameData gd;
        System.out.print("Would you like to load your previous game from the auto-save location? ");
        String loadInput = scanner.nextLine();
        if (loadInput.equalsIgnoreCase("yes")) {
            gd = GameDataSaveManager.tryLoad(roundAutoSaveLocation);
        } else {
            gd = GameData.plain();
        }
        return gd;
    }

    public static GameData loadGameData(String[] args, Scanner scanner) {
        GameData gd;
        try {
            if (!args[0].isEmpty()) {
                gd = GameDataSaveManager.tryLoad(args[0]);
            } else {
                gd = askForLastGameLoad(scanner);
            }
        } catch (IndexOutOfBoundsException e) {
            gd = askForLastGameLoad(scanner);
        }
        return gd;
    }

    public static void play(GameData gd, Scanner scanner) {
        final InputHandler ih = new InputHandler(scanner, new Scorer());

        System.out.println("Press enter to play: ");
        scanner.nextLine();

        while(true) {
            gd = ih.callRound(RoundGen.genRoundData(gd));
            GameDataSaveManager.trySave(roundAutoSaveLocation, gd);
            System.out.print("\nEnter 'stop' to stop, to end the game or anything else to continue: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("stop")) {
                System.out.print("Would you like to save this game? (Note: no mater what, the latest round is always auto-saved to .lastGame.json)\nEnter 'no' to not save somewhere else: ");
                String saveInput = scanner.nextLine();
                if (!saveInput.equalsIgnoreCase("no")) {
                    boolean isNameValid = false;
                    while(!isNameValid) {
                        System.out.print("Enter the location & name of your save? (don't include the file extension): ");
                        String fileNameInput = scanner.nextLine().trim();
                        isNameValid = GameDataSaveManager.trySave(fileNameInput, gd);
                    }
                }
                break;
            }
        }
    }
}
