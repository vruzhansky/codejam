package com.wr.codejam.online.universe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numberOfCases = in.nextInt();
        String[] swaps = new String[numberOfCases];
        for (int caseNumber = 0; caseNumber < numberOfCases; caseNumber++) {
            long shield = in.nextLong();
            char[] program = in.next().trim().toCharArray();
            swaps[caseNumber]= solve(shield, program);
        }
        for (int i = 0; i < swaps.length; i++) {
            System.out.println("Case #" + (i+1) + ": " + swaps[i]);
        }
    }

    private static String solve(long shield, char[] p) {

        int power = Integer.MAX_VALUE;
        int swaps = 0;
        boolean done = false;
        while (power > shield && !done) {
            int charge = 1, lastShoot = 0;
            power = 0; done = true;

            for (char c : p) {
                switch (c) {
                    case 'C':
                        charge = 2 * charge;
                        break;
                    case 'S':
                        power = power + charge;
                        lastShoot = charge;
                        break;
                }
            }

            if (power <= shield) return "0";
            for (int i = p.length - 1; i > 0; i--) {
                if (p[i] == 'S' && p[i - 1] == 'C') {
                    swap(p, i, i - 1);
                    swaps++;
                    lastShoot = lastShoot / 2;
                    power = power - lastShoot;
                    done = false;
                    if (power <= shield) break;
                }
            }
        }
        return swaps == 0 || done ? "IMPOSSIBLE" : Integer.toString(swaps);
    }

    private static void swap(char[] p, int i, int j) {
        char temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }
}
