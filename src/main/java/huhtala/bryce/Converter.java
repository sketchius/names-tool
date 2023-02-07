package huhtala.bryce;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *  This class is used to convert US census data into a list of unique
 *  first names and save the result in names.txt.
 */
public class Converter {
    public static void main(String[] args) {
        Set<String> nameSet = new TreeSet<>();

        for (int i = 1889; i < 2022; i++) {
            addFileToSet("data/yob"+i+".txt", nameSet);
        }

        File file = new File("names-common.txt");

        try (PrintWriter output = new PrintWriter(file)) {
            for (String name : nameSet) {
                output.println(name);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open output file.");
        }

        System.out.println(nameSet.size() + " names");
    }

    private static void addFileToSet(String fileName, Set<String> nameSet) {
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int count = Integer.parseInt(line.split(",")[2]);
                if (count >= 300)
                    nameSet.add(line.split(",")[0]);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
