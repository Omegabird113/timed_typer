package io.github.omegabird113.timedtyper;

import java.util.Scanner;

class Main {
    static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final GameData gd = Console.loadGameData(args, scanner);
        Console.play(gd, scanner);
        scanner.close();
    }
}