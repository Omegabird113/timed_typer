package none.omegabird.timedTyper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Random;

public class GameDataSerializationTest {
    private static final Random random = new Random();
    @TempDir
    private Path tempDir;

    @Test
    void serializationTest() {
        String location = tempDir + "/_tmp";

        GameData gameData = new GameData(random.nextInt(0, 10000), random.nextFloat(0.5f, 1.5f), random.nextInt(1, 5));
        GameDataSaveManager.trySave(location, gameData);

        GameData newGameData = GameDataSaveManager.tryLoad(location + ".json");

        Assertions.assertEquals(gameData.getDifficulty(), newGameData.getDifficulty());
        Assertions.assertEquals(gameData.getScore(), newGameData.getScore());
        Assertions.assertEquals(gameData.getMultiplier(), newGameData.getMultiplier());
    }
}
