package none.omegabird.timedTyper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ScorerTests {
    private static final Random random = new Random();
    private static final Scorer scorer = new Scorer();
    private static final GameData gameData = new GameData(0,10f,1);

    @Test
    public void noAccuracyTest(){
        RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 1f, gameData);

        roundData.setTimeTaken(5f);
        roundData.setUserInput("");

        long score = scorer.calcRoundScore(roundData);
        Assertions.assertEquals(0, score);
    }

    @Test
    public void singleTimeLimitExceededTest(){
        RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 10f, gameData);

        roundData.setTimeTaken(11f);
        roundData.setUserInput("the quick brown fox jumps over the lazy dog");

        long score = scorer.calcRoundScore(roundData);
        Assertions.assertEquals(0, score);
    }

    @Test
    public void singleTimeMinimumCheatPreventionTest(){
        RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 10f, gameData);

        roundData.setTimeTaken(0.112f);
        roundData.setUserInput("the quick brown fox jumps over the lazy dog");

        long score = scorer.calcRoundScore(roundData);
        Assertions.assertEquals(0, score);
    }

    @Test
    public void idealScoreTest1(){
        // Normal Multiplier, Fairly High Accuracy, High Time Taken

        RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 10f, gameData);

        roundData.setTimeTaken(7f);
        roundData.setUserInput("the quik brown fox jump over the lazy dog ");

        long score = scorer.calcRoundScore(roundData);
        Assertions.assertTrue(score > 30);
    }

    @Test
    public void idealScoreTest2(){
        // Lower Multiplier, Lowest Tested Accuracy, Very High Time Taken

        RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 9f, gameData);

        roundData.setTimeTaken(8.6f);
        roundData.setUserInput("th quck bown fx jumped over da lazy doh ");

        long score = scorer.calcRoundScore(roundData);
        Assertions.assertTrue(score > 10);
    }

    @Test
    public void idealScoreTest3(){
        // High Multiplier, High Accuracy, Low Time Taken

        Scorer scorer = new Scorer();
        GameData gameData = new GameData(0,3f,1);
        RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 9f, gameData);

        roundData.setTimeTaken(4f);
        roundData.setUserInput("the quick brown fox jumps over the lazy dog");

        long score = scorer.calcRoundScore(roundData);
        Assertions.assertTrue(score > 150);
    }

    @Test
    public void massValidScoreBasedOnTimeTest(){
        final String[] sentenceChoices = new String[5];
        sentenceChoices[0] = "the quick brown fox jumps over the lazy dog";
        sentenceChoices[1] = "the quik brown fox jumped over the lazy dog";
        sentenceChoices[2] = "the quickk brown fox jumps over the lazy dog";
        sentenceChoices[3] = "th quck broen foxs jumped over le laxy dogge";
        sentenceChoices[4] = "the quick brown fox jumps, over he lazy dog   ";

        for(int i = 0; i < 50000; i++) {
            RoundData roundData = new RoundData("the quick brown fox jumps over the lazy dog", 10f, gameData);

            roundData.setTimeTaken(random.nextFloat(1f, 12f));
            roundData.setUserInput(sentenceChoices[random.nextInt(0, sentenceChoices.length)]);

            long score = scorer.calcRoundScore(roundData);
            if (score < 0) {
                Assertions.fail("Score is negative");
            } else if (score == 0) {
                if (roundData.getTimeTaken() < roundData.getTimeLimit() && (roundData.getTimeTaken() / roundData.getTimeLimit()) > 0.125) {
                    Assertions.fail("Score is improperly 0.");
                }
            }
        }
    }
}
