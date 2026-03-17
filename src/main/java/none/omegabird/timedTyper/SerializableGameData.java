package none.omegabird.timedTyper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class SerializableGameData {
    // This should ONLY be used as an interim object by GameDataSaveManager.

    private final long score;
    private final float multiplier;
    private final int difficulty;

    @JsonCreator
    public SerializableGameData(
        @JsonProperty("score") long score,
        @JsonProperty("multiplier") float multiplier,
        @JsonProperty("difficulty") int difficulty
    ) {
        this.score = score;
        this.multiplier = multiplier;
        this.difficulty = difficulty;
    }

    public SerializableGameData(GameData gd) {
        this.score = gd.getScore();
        this.multiplier = gd.getMultiplier();
        this.difficulty = gd.getDifficulty();
    }

    public long getScore() {
        return score;
    }
    public float getMultiplier() {
        return multiplier;
    }
    public int getDifficulty() {
        return difficulty;
    }
}