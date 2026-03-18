package none.omegabird.timedTyper;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class IntegratedGameTest {
    private static final Random random = new Random();
    private static final GameData gameData = GameData.plain();
    private static final Scorer scorer = new Scorer();
    private static final int totalStringSelectionWeight = 100;
    private static final int randomStringSelectionWeight = 10;

    @Test
    void IntegrationTest() {
        int difficulty = gameData.getDifficulty();

        //each round
        for (int i = 0; i < 1000; i++) {
            RoundData roundData = RoundGen.genRoundData(gameData);

            String prompt = roundData.getPrompt();
            if (prompt.isEmpty()) {
                Assertions.fail("Prompt is empty.");
            }
            int promptLength = prompt.length();
            RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').get();
            String randomString = randomStringGenerator.generate(promptLength);

            // string maker loop
            StringBuilder promptBuilder = new StringBuilder(promptLength);
            for(int j = 0; j < promptLength; j++) {
                char c1 = prompt.charAt(j);
                char c2 = randomString.charAt(j);
                int selector = random.nextInt(1, totalStringSelectionWeight);
                if (selector <= randomStringSelectionWeight) {
                    promptBuilder.append(c2);
                } else {
                    promptBuilder.append(c1);
                }
            }
            roundData.setUserInput(promptBuilder.toString());

            float timeTaken = random.nextFloat(1f, roundData.getTimeLimit() + random.nextFloat(0f, 15f));
            roundData.setTimeTaken(timeTaken);

            long score = scorer.calcRoundScore(roundData);
            if (score < 0) {
                Assertions.fail("score is negative");
            } else if (score == 0) {
                if (roundData.getTimeTaken() < roundData.getTimeLimit() && (roundData.getTimeTaken() / roundData.getTimeLimit()) > 0.125) {
                    Assertions.fail("Score is improperly 0.");
                }
            }
            gameData.setScore(gameData.getScore() + score);
            if (gameData.getDifficulty() != difficulty) {
                difficulty = gameData.getDifficulty();
                System.out.println("As of round " + i + " difficulty is " + difficulty + ".");
            }
        }
        if (gameData.getDifficulty() < 4) {
            Assertions.fail("Difficulty too low after 1000 rounds.");
        }
    }
}
