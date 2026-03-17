package none.omegabird.timedTyper;

import java.util.Objects;

public class RoundData {
    private final String prompt;
    private final float timeLimit;
    private final GameData gData;
    private String userInput;
    private float timeTaken;
    private long roundScore;

    RoundData(String prompt, float timeLimit, GameData gData) {
        this.prompt = Objects.requireNonNullElse(prompt, "");
        this.timeLimit = Math.max(timeLimit, 1f);
        this.gData = Objects.requireNonNull(gData);
    }

    public String getPrompt() {
        return this.prompt;
    }

    public float getTimeLimit() {
        return this.timeLimit;
    }

    public String getUserInput() {
        return this.userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public float getTimeTaken() {
        return this.timeTaken;
    }

    public void setTimeTaken(float timeTaken) {
        this.timeTaken = timeTaken;
    }

    public long getRoundScore() {
        return this.roundScore;
    }

    public void setRoundScore(long roundScore) {
        this.roundScore = roundScore;
    }

    public GameData getGameData() {
        return this.gData;
    }

    @Override
    public String toString() {
        return "RoundData(prompt='" + this.prompt + "', timeLimit=" + timeLimit + ", gameData=" + this.gData + ", roundResult=(userInput='"+ this.userInput + "', timeTaken=" + this.timeTaken + ", roundScore=" + this.roundScore + "))";
    }
}