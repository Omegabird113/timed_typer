import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

final public class GameDataSaveManager {
    private GameDataSaveManager() {}

    private static GameData generatePlainGameData() {
        return new GameData(0, 1f, 1, new Scorer(), new Scanner(System.in));
    }

    public static GameData tryLoad(String[] args) {
        if (args.length > 0) {
            String gameFileLocation = args[0];
            File file = new File(gameFileLocation);
            if (!file.exists()) {
                System.out.printf("ERROR: Invalid save file, it does not exist..\ndue to this, the file (%s) has not been loaded.\n", gameFileLocation);
                return generatePlainGameData();
            } else if (!gameFileLocation.endsWith(".json")) {
                System.out.printf("ERROR: Invalid save file, it must be of a .json format.\ndue to this, the file (%s) has not been loaded.\n", gameFileLocation);
                return generatePlainGameData();
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    SerializableGameData sgd = objectMapper.readValue(file, SerializableGameData.class);
                    System.out.println("Loaded game at " + gameFileLocation + " successfully. You have a score of " + sgd.score + " to start.");
                    return new GameData(sgd, new Scorer(), new Scanner(System.in));
                } catch (IOException e) {
                    System.out.printf("ERROR: The file failed to load into the Game Object. \ndue to this, the file (%s) has not been loaded.\n", gameFileLocation);
                    System.out.println("(exact error: " + e + ")");
                    return generatePlainGameData();
                }
            }
        } else {
            return generatePlainGameData();
        }
    }

    public static boolean trySave(String filename, GameData gd) {
        SerializableGameData sgd = new SerializableGameData(gd);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filename + ".json");
        try {
            objectMapper.writeValue(file, sgd);
            return true;
        } catch (IOException e) {
            System.out.printf("ERROR: Failed to save the game.\nIt was not saved properly to " + filename + ".json.\n(exact error: " + e + ")\n");
            return false;
        }
    }
}