package none.omegabird.timedTyper;

import org.apache.commons.text.similarity.LevenshteinDistance;
import java.util.Random;

public class Scorer implements ScoreCalculator{
    private static final Random RANDOM = new Random();
    private static final LevenshteinDistance ld = LevenshteinDistance.getDefaultInstance();

    @Override
    public long calcRoundScore(RoundData rData) {
        String prompt = rData.getPrompt();
        prompt = prompt == null ? "" : prompt.trim();
        String userInput = rData.getUserInput();
        userInput = userInput == null ? "" : userInput.trim();

        float timeLimit = rData.getTimeLimit();
        float timeTaken = rData.getTimeTaken();
        int difficulty = rData.getGameData().getDifficulty();
        int wordCount = prompt.isEmpty() ? 0 : prompt.split("\\s+").length;

        if (difficulty < 1) { //prevents weird negative or 0 difficulty values
            difficulty = 1;
        }

        if (timeLimit <= 0) { //forces valid time limit of more than 0
            timeLimit = 1f;
            System.err.println("Invalid time limit of 0 or less occurred");
        }

        int distance = ld.apply(prompt, userInput);
        int maximumPotentialLength = Math.max(Math.max(prompt.length(), userInput.length()), 1);
        double accuracy = 1 - ((double) distance / maximumPotentialLength);
        accuracy = Math.max(0, Math.min(accuracy, 1));
        if (accuracy < 0.5) { //prevents keyboard mashing or really typo-ed inputs from getting credit.
            accuracy = 0;
        }

        double timeRelative = timeTaken / timeLimit;
        if (timeRelative > 1) { //Removes credit for taking more than the time limit
            return 0;
        } else if (timeRelative < 0.125) { //sets a hard minimum which prevents 0/negative values and people who cheat and do it really fast
            return 0;
        }

        double baseScore = difficulty * accuracy * Math.pow(Math.max(wordCount, 1), 1.5) * (1.0 / timeRelative);
        if(baseScore > 1000) {
            baseScore = 1000;
        }
        double finalScore = baseScore * RANDOM.nextDouble(0.9, 1.1);

//      This is for debug purposes and in a distribution to users is intended to not be present.
//      System.out.println("accuracy=" + accuracy + ", wordCount=" + wordCount + ", timeRelative=" + timeRelative + ", base score=" + baseScore + ", final score=" + finalScore);

        return (long) Math.ceil(finalScore * Math.max(rData.getGameData().getMultiplier(), 0f));
    }
}