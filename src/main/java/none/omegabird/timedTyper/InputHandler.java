package none.omegabird.timedTyper;

import org.apache.commons.text.WordUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class InputHandler implements InputManager{
    final Scanner scanner;
    InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public RoundData promptAndTimeUser(RoundData rData) {
        ScoreCalculator scoreCalc = rData.getGameData().getScoreCalculator();

        System.out.printf("You have %d seconds to enter the following:\n%s\n", Math.round(rData.getTimeLimit()), WordUtils.wrap(rData.getPrompt(), 145));
        System.out.flush();

        Instant start = Instant.now();
        String userInput = scanner.nextLine();
        Instant stop = Instant.now();

        Duration timeTakenDuration = Duration.between(start, stop);
        double timeTaken = (double) timeTakenDuration.toNanos() / 1_000_000_000.0;
        if(timeTaken < 0) {
            timeTaken = 0;
        }

        rData.setTimeTaken((float) timeTaken);
        rData.setUserInput(userInput);
        long score = scoreCalc.calcRoundScore(rData);
        rData.setRoundScore(score);
        return rData;
    }

    @Override
    public GameData callRound(RoundData rData) {
        RoundData round = this.promptAndTimeUser(rData);

        GameData gData = rData.getGameData();
        long currentGameScore = gData.getScore();
        gData.setScore(currentGameScore + round.getRoundScore());

        System.out.printf("You completed the round in %f/%f seconds and added %d to your score,\nwhich means your total score is now %d.", round.getTimeTaken(), round.getTimeLimit(), round.getRoundScore(), gData.getScore());
        return rData.getGameData();
    }
}