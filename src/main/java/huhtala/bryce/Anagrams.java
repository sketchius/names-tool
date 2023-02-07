package huhtala.bryce;

import java.io.File;
import java.util.*;

public class Anagrams {

    private static final String NAMES_FILENAME = "names.txt";
    private static final String MY_NAME = "amber";



    public static void main(String[] args) {
        List<String> anagrams = new ArrayList<>();

        File file = new File(NAMES_FILENAME);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentName = scanner.nextLine();
                if (isAnagram(currentName,MY_NAME)) {
                    anagrams.add(currentName);
                }
            }
        } catch (Exception e) {}

        for (String anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    public static boolean isAnagram(String source, String target) {
        if (source.length() != target.length()) return false;
        if (source.equalsIgnoreCase(target)) return false;
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
            if (targetMap.get(entry.getKey()) != entry.getValue())
                return false;
        }
        return true;
    }

}
