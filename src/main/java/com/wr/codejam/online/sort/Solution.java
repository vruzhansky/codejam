package com.wr.codejam.online.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numberOfCases = in.nextInt();
        String[] index = new String[numberOfCases];
        for (int caseNumber = 0; caseNumber < numberOfCases; caseNumber++) {
            int length = in.nextInt();
            int[] arr = new int[length];
            for (int i = 0; i < length; i++) arr[i] = in.nextInt();
            index[caseNumber] = solve(arr);
        }
        for (int i = 0; i < index.length; i++) {
            System.out.println("Case #" + (i + 1) + ": " + index[i]);
        }
    }

    private static String solve(int[] arr) {
        int n = arr.length;

        int[] even = new int[n - n / 2];
        int[] uneven = new int[n / 2];

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) even[i / 2] = arr[i];
            else uneven[i / 2] = arr[i];
        }
        int[] res = sortAndInterleave(n, even, uneven);

        for (int i = 0; i < n - 1; i++) {
            if (res[i] > res[i + 1]) return Integer.toString(i);
        }
        return "OK";
    }

    private static int[] sortAndInterleave(int n, int[] even, int[] uneven) {
        Arrays.sort(even);
        Arrays.sort(uneven);
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            res[i] = (i % 2 == 0) ? even[i / 2] : uneven[i / 2];
        }
        return res;
    }
}
