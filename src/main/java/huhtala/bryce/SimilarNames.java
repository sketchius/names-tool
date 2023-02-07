package huhtala.bryce;

import java.io.File;
import java.util.*;

public class SimilarNames {

    private static final String NAMES_FILENAME = "names-common.txt";



    public static void main(String[] args) {
        boolean firstRun = true;
        while (true) {
            System.out.print("Please enter " + (firstRun ? "a":"another") + " name:  ");
            firstRun = false;

            Scanner userInput = new Scanner(System.in);
            String name = userInput.nextLine();

            Map<String, Double> scores = new HashMap<>();

            File file = new File(NAMES_FILENAME);

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String currentName = scanner.nextLine();
                    if (!name.equalsIgnoreCase(currentName))
                        scores.put(currentName, getScore2(name, currentName));
                }
            } catch (Exception e) {
                System.err.println((e.getMessage()));
            }

            scores.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(System.out::println);

        }
    }

    public static double getScore(String source, String compare) {
        source = source.toLowerCase(); compare = compare.toLowerCase();
        double score = 0;
        int lengthDifference = Math.abs(source.length() - compare.length());

        for (int i = 0; i < source.length(); i++) {
            for (int j = 0; j < compare.length(); j++) {
                char s = source.charAt(i);
                char c = compare.charAt(j);
                int delta = Math.abs(i-j);
                if (source.charAt(i) == compare.charAt(j)) {
                    score += (1.0/(delta+1));
                    break;
                }
            }
        }

        score = score - (lengthDifference/2.0);
        return score;
    }

    public static double getScore2(String source, String target) {
        double score = 0;
        source = source.toLowerCase();
        target = target.toLowerCase();
        Map<Character, Integer> sourceMap = new HashMap<>();
        Map<Character, Integer> targetMap = new HashMap<>();
        for (int i = 0; i < source.length(); i++) {
            Character character = source.charAt(i);
            if (sourceMap.containsKey(character)) {
                sourceMap.put(character,sourceMap.get(character)+1);
            } else {
                sourceMap.put(character,1);
            }
            if (i < target.length() && character == target.charAt(i))
                score++;
        }
        for (int i = 0; i < target.length(); i++) {
            Character character = target.charAt(i);
            if (targetMap.containsKey(character)) {
                targetMap.put(character,targetMap.get(character)+1);
            } else {
                targetMap.put(character,1);
            }
        }
        for (Map.Entry<Character,Integer> entry: sourceMap.entrySet()) {
            if (targetMap.containsKey(entry.getKey()))
                score += Math.min(targetMap.get(entry.getKey()),entry.getValue());
        }

        double lengthDifference = Math.abs(source.length() - target.length());
        score -= lengthDifference;
        return score;
    }

}
