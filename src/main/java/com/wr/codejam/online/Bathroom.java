package com.wr.codejam.online;

import java.io.*;
import java.util.*;

public class Bathroom {

    public static void main(String[] args) throws IOException {
        solve(System.in, System.out);
    }

    private static void solve(InputStream is, PrintStream out) throws IOException {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(is)));
        int numberOfCases = in.nextInt();
        for (int caseNumber = 0; caseNumber < numberOfCases; caseNumber++) {
            long numberOfStalls = in.nextLong();
            long numberOfPeople = in.nextLong();
            TreeMap<Long, Long> regions = new TreeMap<>();
            regions.put(numberOfStalls, (long) 1);
            long maxLR = maxLR(numberOfStalls);
            long minLR = minLR(numberOfStalls);
            while (numberOfPeople > 0) {
                Map.Entry<Long, Long> maxEntry = regions.lastEntry();
                long width = maxEntry.getKey();
                long nMax = maxEntry.getValue();
                maxLR = maxLR(width);
                minLR = minLR(width);
                if (nMax < numberOfPeople) {
                    numberOfPeople -= nMax;
                    long merged = regions.merge(width, -nMax, Long::sum);
                    if (merged == 0) {
                        regions.remove(width);
                    }
                    if (width % 2 == 0) {
                        regions.merge(width / 2, nMax, Long::sum);
                        regions.merge(width / 2 - 1, nMax, Long::sum);
                    } else {
                        regions.merge(width / 2, nMax * 2, Long::sum);
                    }
                } else {
                    break;
                }
            }
            out.println("Case #" + (1 + caseNumber) + ": " + maxLR + " " + minLR);
        }
    }

    private static long maxLR(long width) {
        return width % 2 == 1 ? width / 2 : width / 2;
    }

    private static long minLR(long width) {
        return width % 2 == 1 ? width / 2 : width / 2 - 1;
    }


}
