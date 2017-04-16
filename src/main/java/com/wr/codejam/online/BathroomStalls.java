package com.wr.codejam.online;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BathroomStalls {
    static class Case {
        long n;
        long k;
    }

    static class Solution {
        long max;
        long min;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(Arrays.toString(stalls(4, 2)));
//        System.out.println(Arrays.toString(stalls(5, 2)));
//        System.out.println(Arrays.toString(stalls(6, 2)));
//        System.out.println(Arrays.toString(stalls(1000, 1000)));
//        System.out.println(Arrays.toString(stalls(1000, 1)));
        String filename;
        if (0 < args.length) {
            filename = args[0];
        } else {
            filename = getFilenameFromSystemIn();
        }

        Case[] cases = readFile(Paths.get("data/" + filename).toAbsolutePath().toFile());

        Solution[] solution = new Solution[cases.length];
        for (int i = 0; i < cases.length; i++) {
            solution[i] = stalls(cases[i]);
            System.out.println("Done #" + i);
        }
        saveSolution(filename, solution);
    }

    private static String getFilenameFromSystemIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    private static Case[] readFile(File file) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            int caseNum = Integer.parseInt(br.readLine());
            Case[] cases = new Case[caseNum];

            for (int i = 0; i < caseNum; i++) {
                String[] s = br.readLine().split("\\s+");
                Case cass = new Case();
                cass.n = Long.parseLong(s[0]);
                cass.k = Long.parseLong(s[1]);
                cases[i] = cass;
            }
            return cases;
        }
    }

    private static void saveSolution(String fileName, Solution[] solution) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("results/" + fileName.replace(".in", ".out"), "UTF-8");
        for (int i = 0; i < solution.length; i++) {
            writer.println("Case #" + (i + 1) + ": " + solution[i].max + " " + solution[i].min);
        }
        writer.close();
    }

    public static Solution stalls(Case cass) {
        long n = cass.n, k = cass.k;
        TreeMap<Long, Long> stallSpaces = new TreeMap<>();
        stallSpaces.put(n, 1L);

        Solution s = new Solution();
        long space1 = 0, space2 = 0;
        while (stallSpaces.size() > 0 && k > 0) {

            Map.Entry<Long, Long> maxRegion = stallSpaces.lastEntry();
            Long maxSpace = maxRegion.getKey();
            stallSpaces.merge(maxSpace, -1L, Long::sum);
            if (stallSpaces.get(maxSpace) < 1) {
                stallSpaces.remove(maxSpace);
            }

            if (maxSpace % 2 == 0) {
                space2 = maxSpace / 2;
                space1 = space2 - 1;
            } else {
                space1 = maxSpace / 2;
                space2 = space1;
            }

            if (space1 > 0) {
                stallSpaces.merge(space1, 1L, Long::sum);
            }
            if (space2 > 0) {
                stallSpaces.merge(space2, 1L, Long::sum);
            }
            k--;
        }
        if (space1 > space2) {
            s.max = space1 > 0 ? space1 : 0;
            s.min = space2 > 0 ? space2 : 0;
        } else {
            s.max = space2 > 0 ? space2 : 0;
            s.min = space1 > 0 ? space1 : 0;
        }
        return s;
    }
}