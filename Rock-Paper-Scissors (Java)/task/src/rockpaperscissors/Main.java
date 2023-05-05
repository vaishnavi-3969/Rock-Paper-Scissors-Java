package rockpaperscissors;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class  Main{
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);

        Map<String, Integer> scores = loadScores();

        String optionsStr = promptOptions();
        String[] options = optionsStr.split(",");
        System.out.println("Okay, let's start");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("!exit")) {
                break;
            } else if (input.equals("!rating")) {
                System.out.println("Your rating: " + scores.getOrDefault(name, 0));
            } else if (Arrays.asList(options).contains(input)) {
                String computerChoice = pickRandomOption(options);
                int result = getResult(input, computerChoice, options);
                if (result == 0) {
                    System.out.println("There is a draw (" + computerChoice + ")");
                    scores.put(name, scores.getOrDefault(name, 0) + 50);
                } else if (result == 1) {
                    System.out.println("Well done. The computer chose " + computerChoice + " and failed");
                    scores.put(name, scores.getOrDefault(name, 0) + 100);
                } else {
                    System.out.println("Sorry, but the computer chose " + computerChoice);
                }
            } else {
                System.out.println("Invalid input");
            }
        }

        saveScores(scores);
        System.out.println("Bye!");
    }

    private static Map<String, Integer> loadScores() {
        Map<String, Integer> scores = new HashMap<>();
        try (Scanner fileScanner = new Scanner(Paths.get("rating.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] tokens = line.split("\\s+");
                scores.put(tokens[0], Integer.parseInt(tokens[1]));
            }
        } catch (IOException e) {
            // ignore and start with empty scores
        }
        return scores;
    }

    private static void saveScores(Map<String, Integer> scores) {
        try (PrintWriter writer = new PrintWriter("rating.txt")) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                writer.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error saving scores");
        }
    }

    private static String promptOptions() {
//        System.out.print("Enter the list of options: ");
        String input = scanner.nextLine();
        return input.isEmpty() ? "rock,paper,scissors" : input;
    }

    private static String pickRandomOption(String[] options) {
        return options[random.nextInt(options.length)];
    }

    private static int getResult(String userChoice, String computerChoice, String[] options) {
        int userIndex = Arrays.asList(options).indexOf(userChoice);
        int computerIndex = Arrays.asList(options).indexOf(computerChoice);
        if (userIndex == computerIndex) {
            return 0; // draw
        } else if (userIndex > computerIndex || userIndex + options.length / 2 < computerIndex) {
            return 1; // win
        } else {
            return -1; // loss
        }
    }
}

/*
public class Main {
    static int score = 0;
    static final String[] choices = {"rock", "paper", "scissors"};
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);
        score = readAndGetScore(name);
        while (true) {
            String input = scanner.nextLine();
            if (validateInput(input)) {
                playGame(input);
            } else if (input.equals("!rating")) {
                System.out.println("Your rating: " + score);
            } else if (input.equals("!exit")) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("Bye!");
    }

    public static int readAndGetScore(String name) throws IOException {
        File file = new File("rating.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        while (br.ready()) {
            String input = br.readLine();
            String[] split = input.split(" ");
            if (split[0].equals(name)) {
                br.close();
                return Integer.parseInt(split[1]);
            }
        }
        return 0;
    }

    private static void playGame(String userChoice) {
        String computerChoice = generateComputerChoice();
        if (userChoice.equals(computerChoice)) {
            System.out.println("There is a draw (" + userChoice + ")");
            score += 50;
        } else if (userChoice.equals("rock") && computerChoice.equals("scissors")
                || userChoice.equals("paper") && computerChoice.equals("rock")
                || userChoice.equals("scissors") && computerChoice.equals("paper")) {
            System.out.println("Well done. The computer chose " + computerChoice + " and failed");
            score += 100;
        } else {
            System.out.println("Sorry, but the computer chose " + computerChoice);
        }
    }

    private static boolean validateInput(String input) {
        return input.equals("rock") || input.equals("paper") || input.equals("scissors");
    }

    private static String generateComputerChoice() {
        int computerChoice = (int) (Math.random() * 3);
        return choices[computerChoice];
    }
}







//=====================================================================
//package rockpaperscissors;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your name:");
//        String name = scanner.nextLine();
//        System.out.println("Hello, " + name);
//
//        Map<String, Integer> scores = readScores();
//
//        int score = scores.getOrDefault(name, 0);
//
//        while (true) {
//            String input = scanner.nextLine();
//
//            if (input.equals("!exit")) {
//                System.out.println("Bye!");
//                break;
//            } else if (input.equals("!rating")) {
//                System.out.println("Your rating: " + score);
//            } else if (isValidInput(input)) {
//                String computerMove = getRandomMove();
//                int result = getResult(input, computerMove);
//                if (result == 0) {
//                    System.out.println("There is a draw (" + computerMove + ")");
//                    score += 50;
//                } else if (result == 1) {
//                    System.out.println("Sorry, but the computer chose " + computerMove);
//                } else {
//                    System.out.println("Well done. The computer chose " + computerMove + " and failed");
//                    score += 100;
//                }
//            } else {
//                System.out.println("Invalid input");
//            }
//        }
//    }
//
//    private static Map<String, Integer> readScores() {
//        Map<String, Integer> scores = new HashMap<>();
//        try (Scanner fileScanner = new Scanner(new java.io.File("rating.txt"))) {
//            while (fileScanner.hasNextLine()) {
//                String[] parts = fileScanner.nextLine().split(" ");
//                scores.put(parts[0], Integer.parseInt(parts[1]));
//            }
//        } catch (java.io.FileNotFoundException e) {
//            // ignore, assume scores are 0 for all users
//        }
//        return scores;
//    }
//
//    private static boolean isValidInput(String input) {
//        return input.equals("rock") || input.equals("paper") || input.equals("scissors");
//    }
//
//    private static String getRandomMove() {
//        Random random = new Random();
//        int move = random.nextInt(3);
//        if (move == 0) {
//            return "rock";
//        } else if (move == 1) {
//            return "paper";
//        } else {
//            return "scissors";
//        }
//    }
//
//    private static int getResult(String userMove, String computerMove) {
//        if (userMove.equals(computerMove)) {
//            return 0;
//        } else if (userMove.equals("rock") && computerMove.equals("paper") ||
//                userMove.equals("paper") && computerMove.equals("scissors") ||
//                userMove.equals("scissors") && computerMove.equals("rock")) {
//            return 1;
//        } else {
//            return 2;
//        }
//    }
//}
//

*/


//==========================================================
//public class  Main{
//
//    private static final Scanner scanner = new Scanner(System.in);
//    private static final Random random = new Random();
//    private static final Map<String, Integer> scores = new HashMap<>();
//
//    public static void main(String[] args) {
//        System.out.println("Enter your name:");
//        String name = scanner.nextLine();
//        System.out.println("Hello, " + name);
//        loadScores();
//        while (true) {
//            System.out.println("Please enter your move (rock, paper, scissors) or enter '!exit' to quit:");
//            String move = scanner.nextLine();
//            if ("!exit".equals(move)) {
//                System.out.println("Bye!");
//                break;
//            } else if ("!rating".equals(move)) {
//                System.out.println("Your rating: " + scores.getOrDefault(name, 0));
//            } else if (isValidMove(move)) {
//                int computerMoveIndex = random.nextInt(3);
//                String computerMove = getMove(computerMoveIndex);
//                int result = getResult(move, computerMove);
//                if (result == 0) {
//                    System.out.println("There is a draw (" + computerMove + ")");
//                    scores.put(name, scores.getOrDefault(name, 0) + 50);
//                } else if (result == 1) {
//                    System.out.println("Sorry, but the computer chose " + computerMove);
//                } else {
//                    System.out.println("Well done. The computer chose " + computerMove + " and failed");
//                    scores.put(name, scores.getOrDefault(name, 0) + 100);
//                }
//            } else {
//                System.out.println("Invalid input");
//            }
//        }
//        saveScores();
//    }
//
//    private static boolean isValidMove(String move) {
//        return move.equals("rock") || move.equals("paper") || move.equals("scissors");
//    }
//
//    private static String getMove(int index) {
//        switch (index) {
//            case 0:
//                return "rock";
//            case 1:
//                return "paper";
//            case 2:
//                return "scissors";
//            default:
//                throw new IllegalStateException("Unexpected value: " + index);
//        }
//    }
//
//    private static int getResult(String userMove, String computerMove) {
//        if (userMove.equals(computerMove)) {
//            return 0;
//        } else if (userMove.equals("rock")) {
//            return computerMove.equals("paper") ? 1 : 2;
//        } else if (userMove.equals("paper")) {
//            return computerMove.equals("scissors") ? 1 : 2;
//        } else if (userMove.equals("scissors")) {
//            return computerMove.equals("rock") ? 1 : 2;
//        } else {
//            throw new IllegalArgumentException("Invalid move: " + userMove);
//        }
//    }
//
//    private static void loadScores() {
//        try (Scanner scanner = new Scanner(new File("rating.txt"))) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split("\\s+");
//                scores.put(parts[0], Integer.parseInt(parts[1]));
//            }
//        } catch (FileNotFoundException e) {
//            // ignore if the file doesn't exist yet
//        }
//    }
//
//    private static void saveScores() {
//        try (PrintWriter writer = new PrintWriter(new File("rating.txt"))) {
//            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
//                writer.println(entry.getKey() + " " + entry.getValue());
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("Error saving scores: " + e.getMessage());
//        }
//    }
//
//    }
//

///=============================================
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Random;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your name: ");
//        String name = scanner.next();
//        String input;
//        File file = new File("rating.txt");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        Scanner fileScanner = new Scanner(file);
//        String[] data = new String[0];
//        while (fileScanner.hasNext()) {
//            data = fileScanner.nextLine().split(" ");
//            if (data[0].equals(name)) {
//                break;
//            }
//        }
//        fileScanner.close();
//        int rating = data.length > 1 ? Integer.parseInt(data[1]) : 0;
//        do {
//            input = scanner.next();
//            generateRandom(input, rating, name, file);
//        } while (!input.equals("!exit"));
//    }
//
//    public static void generateRandom(String input, int rating, String name, File file) throws IOException {
//        Random random = new Random();
//        Scanner scanner = new Scanner(System.in);
//        int n = Math.abs(random.nextInt()) % 3;
//        String[] randomString = {"rock", "paper", "scissors"};
//        String randomGenerated = randomString[n];
//        if (randomGenerated.equals(input)) {
//            rating += 50;
//            System.out.println("There is a draw (" + randomGenerated + ")");
//        } else if (randomGenerated.equals("scissors") && input.equals("rock")) {
//            rating += 100;
//            System.out.println("Well done. The computer chose scissors and failed");
//        } else if (randomGenerated.equals("scissors") && input.equals("paper")) {
//            System.out.println("Sorry, but the computer chose scissors");
//        } else if (randomGenerated.equals("paper") && input.equals("scissors")) {
//            rating += 100;
//            System.out.println("Well done. The computer chose paper and failed");
//        } else if (randomGenerated.equals("paper") && input.equals("rock")) {
//            System.out.println("Sorry, but the computer chose paper");
//        } else if (randomGenerated.equals("rock") && input.equals("paper")) {
//            rating += 100;
//            System.out.println("Well done. The computer chose rock and failed");
//        } else if (randomGenerated.equals("rock") && input.equals("scissors")) {
//            System.out.println("Sorry, but the computer chose rock");
//        } else if (input.equals("!exit")) {
//            System.out.println("Bye!");
//        } else if (input.equals("!rating")) {
//            System.out.println("Your rating: " + rating);
//        } else {
//            System.out.println("Invalid input");
//        }
//        updateRating(name, rating, file);
//    }
//
//    public static void updateRating(String name, int rating, File file) throws IOException {
//        Scanner fileScanner = new Scanner(file);
//        StringBuilder sb = new StringBuilder();
//        boolean found = false;
//        while (fileScanner.hasNextLine()) {
//            String line = fileScanner.nextLine();
//            if (line.startsWith(name)) {
//                sb.append(name).append(" ").append(rating).append("\n");
//                found = true;
//            } else {
//                sb.append(line).append("\n");
//            }
//        }
//        fileScanner.close();
//        if (!found) {
//            sb.append(name).append(" ").append(rating).append("\n");
//        }
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write(sb.toString());
//        fileWriter.close();
//    }
//}