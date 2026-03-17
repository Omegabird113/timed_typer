package none.omegabird.timedTyper;

import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;

final public class GameDataSaveManager {
    private GameDataSaveManager() {}

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Path getGameDataPath() {
        final String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        Path gameDataPath;
        if (os.contains("win")) {
            gameDataPath = Path.of(System.getenv("%APPDATA%"), "TimedTyper");
        } else if (os.contains("mac")) {
            gameDataPath = Path.of(userHome, "Library", "Application Support", "TimedTyper");
        } else { // Linux case
            gameDataPath = Path.of(userHome, ".lcoal", "share", "TimedTyper");
        }

        gameDataPath.toFile().mkdirs();
        return gameDataPath;

    }

    private static GameData generatePlainGameData() {
        return new GameData(0, 1f, 1, new Scorer());
    }

    public static GameData tryLoad(String location) {
        File file = new File(location);
        if (!file.exists()) {
            System.out.printf("ERROR: Invalid save file, it does not exist..\ndue to this, the file (%s) has not been loaded.\n", location);
            return generatePlainGameData();
        } else if (!location.endsWith(".json")) {
            System.out.printf("ERROR: Invalid save file, it must be of a .json format.\ndue to this, the file (%s) has not been loaded.\n", location);
            return generatePlainGameData();
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                SerializableGameData sgd = objectMapper.readValue(file, SerializableGameData.class);
                System.out.println("Loaded game at " + location + " successfully. You have a score of " + sgd.getScore() + " to start.");
                return new GameData(sgd, new Scorer());
            } catch (Exception e) {
                System.out.printf("ERROR: The file failed to load into the Game Object. \ndue to this, the file (%s) has not been loaded.\n", location);
                System.out.println("(exact error: " + e + ")");
                return generatePlainGameData();
            }
        }
    }

    public static boolean trySave(String filename, GameData gd) {
        SerializableGameData sgd = new SerializableGameData(gd);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filename + ".json");
        try {
            objectMapper.writeValue(file, sgd);
            if (!file.exists()) {
                System.out.printf("ERROR: Failed to save the game.\nIt was not saved properly to " + filename + ".json.");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to save the game.\nIt was not saved properly to " + filename + ".json.\n(exact error: " + e + ")\n");
            return false;
        }
    }
}