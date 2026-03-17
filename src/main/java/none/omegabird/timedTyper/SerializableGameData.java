package none.omegabird.timedTyper;

public class SerializableGameData {
    // This should ONLY be used as an interim object to load/save GameData objects from/to files
    public long score;
    public float multiplier;
    public int difficulty;

    public SerializableGameData() {

    }

    public SerializableGameData(long score, float multiplier, int difficulty) {
        this.score = score;
        this.multiplier = multiplier;
        this.difficulty = difficulty;
    }

    public SerializableGameData(GameData gd) {
        this.score = gd.getScore();
        this.multiplier = gd.getMultiplier();
        this.difficulty = gd.getDifficulty();
    }
}