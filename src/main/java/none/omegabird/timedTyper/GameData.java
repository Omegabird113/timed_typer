package none.omegabird.timedTyper;

import java.util.Objects;
import java.util.Scanner;

public class GameData {
    private long score;
    private float multiplier;
    private int difficulty;
    private final ScoreCalculator scoreCalculator;
    private final Scanner scanner;

    GameData(long startScore, float startMultiplier, int startDifficulty, ScoreCalculator scoreCalculator, Scanner scanner) {
        this.score = Math.max(startScore, 0);
        this.multiplier = Math.max(startMultiplier, 0f);
        this.difficulty = Math.max(startDifficulty, 1);
        this.scoreCalculator = Objects.requireNonNullElse(scoreCalculator, new Scorer());
        this.scanner = Objects.requireNonNull(scanner);
    }
    GameData(SerializableGameData gd, ScoreCalculator scoreCalculator, Scanner scanner) {
        Objects.requireNonNull(gd);
        this.score = gd.score;
        this.multiplier = gd.multiplier;
        this.difficulty = gd.difficulty;
        this.scoreCalculator = Objects.requireNonNullElse(scoreCalculator, new Scorer());
        this.scanner = Objects.requireNonNull(scanner);
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

    public Scanner getScanner() {
        return this.scanner;
    }

    @Override
    public String toString() {
        return "GameData(score=" + this.score + ", multiplier=" + this.multiplier + ", difficulty=" + this.difficulty + ", score_calculator=" + this.scoreCalculator + ")";
    }
}