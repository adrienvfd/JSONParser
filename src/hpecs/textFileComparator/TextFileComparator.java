package hpecs.textFileComparator;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class TextFileComparator {

    private String path1; // Reference Path
    private String path2; // Output Path

    public TextFileComparator(String path1, String path2){
        this.path1 = path1;
        this.path2 = path2;
    }

    public int execute() throws IOException {
        int differences = 0;
        LinkedList<String> file1 = new LinkedList<>();
        LinkedList<String> file2 = new LinkedList<>();

        // Map file1:
        try (Scanner scPath1 = new Scanner(new BufferedReader(new FileReader(path1)))) {
            while (scPath1.hasNextLine()) {
                String nextLine = scPath1.nextLine();
                file1.add(nextLine);
            }
        }

        //Map file2:
        try (Scanner scPath2 = new Scanner(new BufferedReader(new FileReader(path2)))) {
            while (scPath2.hasNextLine()) {
                String nextLine = scPath2.nextLine();
                file2.add(nextLine);
            }
        }

        //Compare:
        if (file1.size() != file2.size()) return Integer.MIN_VALUE;
        int lineCounter = 0;
        while (!file1.isEmpty()) {
            String strFile1 = file1.removeFirst();
            String strFile2 = file2.removeFirst();
            lineCounter++;
            // Print difference:
            if (!strFile1.equals(strFile2)) {
                System.out.println("-----------------------");
                System.out.println("Difference found line " + lineCounter);
                System.out.println("File 1 = " + strFile1);
                System.out.println("File 2 = " + strFile2);
                differences++;

            }
        }
        System.out.println("-----------------------");
        System.out.println("Nb of line(s) in files: " + lineCounter);
        System.out.println("-----------------------");
        if (differences != 0) {
            System.out.println("Nb of differences : " + differences);
        } else {
            System.out.println("Ref & Output files are identical! :)");
        }
        System.out.println("-----------------------");


        return differences;
    }
}