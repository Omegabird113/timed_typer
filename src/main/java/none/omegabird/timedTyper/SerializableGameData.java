package none.omegabird.timedTyper;

class SerializableGameData {
    // This should ONLY be used as an interim object to load/save GameData objects from/to files
    protected long score;
    protected float multiplier;
    protected int difficulty;

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