package none.omegabird.timedTyper;

import java.util.Objects;

public class GameData {
    private long score;
    private float multiplier;
    private int difficulty;
    private final ScoreCalculator scoreCalculator;

    GameData(long startScore, float startMultiplier, int startDifficulty, ScoreCalculator scoreCalculator) {
        this.score = Math.max(startScore, 0);
        this.multiplier = Math.max(startMultiplier, 0f);
        this.difficulty = Math.max(startDifficulty, 1);
        this.scoreCalculator = Objects.requireNonNullElse(scoreCalculator, new Scorer());
    }
    GameData(SerializableGameData sgd, ScoreCalculator scoreCalculator) {
        Objects.requireNonNull(sgd);
        this.score = sgd.getScore();
        this.multiplier = sgd.getMultiplier();
        this.difficulty = sgd.getDifficulty();
        this.scoreCalculator = Objects.requireNonNullElse(scoreCalculator, new Scorer());
    }

    public long getScore() {
        return this.score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public float getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public int getDifficulty() {
        return  this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public ScoreCalculator getScoreCalculator() {
        return this.scoreCalculator;
    }

    @Override
    public String toString() {
        return "GameData(score=" + this.score + ", multiplier=" + this.multiplier + ", difficulty=" + this.difficulty + ", score_calculator=" + this.scoreCalculator + ")";
    }
}
