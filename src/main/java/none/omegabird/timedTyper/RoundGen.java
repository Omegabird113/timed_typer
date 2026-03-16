package none.omegabird.timedTyper;

import java.util.List;
import java.util.Random;

public final class RoundGen {
    private RoundGen() {}

    private static final Random RANDOM = new Random();

    private static final List<String> LEVEL_1_WORDS = List.of(
            "bat", "dog", "toy", "cat", "rat", "law", "mud", "boy", "the", "she",
            "red", "pop", "lie", "bay", "hay", "can", "sad", "mad", "cap", "rap",
            "met", "fad", "hop", "top", "tap", "lap", "bag", "ham", "ray",
            "say", "tie", "bit", "hit", "hat", "bet", "bid", "lid", "pad", "and",
            "tan", "tar", "eat", "ate", "lag", "rag", "tag", "kid", "dip", "rip",
            "lip", "pod", "rod", "nod", "his", "sis", "gem", "ant", "wad", "win",
            "nap", "way", "pay", "day", "van", "mom", "dad", "dot", "rot", "tip",
            "hen", "tin", "fee", "pee", "tee", "tea", "pea", "bee", "sea", "see",
            "men", "man", "did", "sim", "pin", "fib", "yay", "may", "pot", "pan",
            "run", "ran", "ton", "put", "hut", "but", "cut", "pit", "vat"
    );

    private static final List<String> LEVEL_2_WORDS = List.of(
            "lemon", "cheese", "happy", "angry", "tomato", "carve", "tattoo",
            "water", "walks", "visit", "liver", "flight", "height", "skirts",
            "house", "train", "spoon", "tiger", "zebra", "doctor", "teach",
            "study", "juice", "bottle", "never", "peace", "donor", "python",
            "coding", "uncle", "sleeps", "table", "wonder", "growth", "forest",
            "river", "yellow", "purple", "cycle", "pillow", "school", "queen",
            "yogurt", "bouncy", "flavor", "guitar", "piano", "breeze", "sunny",
            "window", "marvel", "gloves", "framed", "sweet", "dirty", "clean",
            "apple", "coffee", "button", "refer", "climb", "minute", "light",
            "shelf", "wobble", "tired", "party", "brain", "vault", "tennis",
            "winter", "cover", "paper", "pencil", "hello"
    );

    private static final List<String> LEVEL_4_WORDS = List.of(
            "congratulations", "absolutely", "photosynthesis", "monstrosity",
            "university", "cheeseburger", "accommodate", "suburban", "pizazz",
            "assuming", "stewardess", "xylophone", "overzealous", "withdrawal",
            "wednesday", "vulnerable", "visualization", "versatile", "veterinary",
            "vaccination", "vegetarian", "unanimous", "transmission", "trajectory",
            "temporary", "tournament", "temperature", "surveillance", "psychology",
            "responsibility", "recommendation", "pronunciation", "practitioner",
            "plagiarism", "pilgrimage", "philosophy", "participation",
            "nutritious", "necessarily", "miscellaneous", "marshmallows",
            "mayonnaise", "leprechaun", "limousine", "kindergarten",
            "knowledgeable", "interference", "inflammation", "handkerchief",
            "fluorescent", "extraordinary", "pharmaceutical", "exhilaration",
            "environmental", "disappointment", "choreography", "circumstantial",
            "cauliflower", "cantaloupe", "auditorium", "apostrophe",
            "amphitheater", "advantageous", "acknowledgement", "abbreviation",
            "abundant", "accommodation", "adjustment", "ambidextrous",
            "asymmetrical", "auxiliary", "bachelorette", "bureaucracy",
            "behavioral", "boulevard", "camouflage", "determination",
            "differentiation", "description", "dysfunctional", "impressionable",
            "enthusiastic", "entrepreneur", "eavesdropping", "exaggerated",
            "fascinating", "governmental", "hygienic", "immediately",
            "individuality", "interpretation", "laboratory", "labyrinth",
            "lieutenant", "lightning", "legitimate", "likelihood", "maintenance",
            "masquerade", "medicinal", "mezzanine", "medieval", "mischievous",
            "misunderstood", "pneumonia", "noticeable", "opportunity",
            "overwhelming", "picturesque", "prohibitive",
            "quadruple", "ridiculous", "sacrilegious", "rudimentary", "chandelier",
            "sophomore", "superfluous", "susceptible", "suspicious", "synonymous",
            "tomorrow", "zucchini"
    );

    private static double logarithm(double number, double base) {
        // Why doesn't java support arbitrary bases of logarithm?
        return (Math.log(number) / Math.log(base));
    }

    private static String randomFromList(List<String> list) {
        int randomIndex = RANDOM.nextInt(list.size());
        return list.get(randomIndex);
    }

    private static String getRandomWord(int difficulty) {
        return switch (difficulty) {
            case 1 -> randomFromList(LEVEL_1_WORDS);
            case 2 -> randomFromList(LEVEL_2_WORDS);
            case 4 -> randomFromList(LEVEL_4_WORDS);
            default -> throw new IllegalArgumentException("Attempted to get a random word of an invalid difficulty.");
        };
    }

    private static String getNRandomWords(int difficulty, long n) {
        StringBuilder words = new StringBuilder((int) (n * 6));
        for(long i = 0; i < n; i++) {
            String word = getRandomWord(difficulty);
            if(i != 0) {
                words.append(" ");
            }
            words.append(word);
        }
        return words.toString();
    }

    private static String genPrompt(GameData gData) {
        long score = gData.getScore();

        long minWords;
        long maxWords;
        if(score < 60) {
            minWords = RANDOM.nextInt(3, 7);
            maxWords = RANDOM.nextInt(5, 8);
        } else if (score < 850) {
            minWords = (long) Math.ceil(logarithm(score, 6) + RANDOM.nextLong(2,4));
            maxWords = (long) Math.ceil(logarithm(score, 2) + RANDOM.nextLong(3,8));
            if (maxWords - minWords > 8) { // prevents them from being that different, so if the difference > 8, it makes the difference 7
                maxWords = minWords + 7;
            }
        } else {
            minWords = (long) Math.ceil(logarithm(score, 6) + RANDOM.nextLong(2,4));
            maxWords = (long) Math.ceil((1f/23f) * Math.pow(score, 1.11) + RANDOM.nextLong(1,3));
        }

        // Calculates the random, and swaps if max < min
        long randomNumOfWords;
        if(minWords > maxWords) {
            randomNumOfWords = RANDOM.nextLong(maxWords, minWords + 1);
        } else if(minWords == maxWords) {
            randomNumOfWords = maxWords;
        } else {
            randomNumOfWords = RANDOM.nextLong(minWords, maxWords + 1);
        }

        // Prevents <1 or >100 words
        randomNumOfWords = Math.max(randomNumOfWords, 1);
        randomNumOfWords = Math.min(randomNumOfWords, 100);

        return getNRandomWords(gData.getDifficulty(), randomNumOfWords);
    }

    private static float genTimeLimit(int difficulty, int words) {
        double time = Math.pow(words, 1.5) * difficulty;
        if (difficulty == 1) {
            time *= 0.5;
            time *= RANDOM.nextDouble(0.8, 1.2);
            time += RANDOM.nextInt(2, 5);
        } else if (difficulty == 2) {
            time *= 0.45;
            time *= RANDOM.nextDouble(0.75, 1.15);
            time += RANDOM.nextInt(0, 4);
        } else {
            time *= 0.4;
            time *= RANDOM.nextDouble(0.725, 1.125);
            time += RANDOM.nextInt(0, 2);
        }
        return (float) Math.max(Math.round(time), 1);
    }

    public static int genDifficulty(long score) {
        if (score < 235) {
            return 1;
        } else if (score < 1360) {
            return 2;
        } else {
            return 4;
        }
    }

    public static RoundData genRoundData(GameData gData) {
        int difficulty = gData.getDifficulty();
        int generatedDifficulty = genDifficulty(gData.getScore());
        if (generatedDifficulty > difficulty) {
            gData.setDifficulty(generatedDifficulty);
        }

        String prompt = genPrompt(gData);
        int words = prompt.split("\\s+").length;
        float timeLimit = genTimeLimit(gData.getDifficulty(), words);

        return new RoundData(prompt, timeLimit, gData);
    }
}