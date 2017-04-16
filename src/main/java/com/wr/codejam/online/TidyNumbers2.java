package com.wr.codejam.online;

import com.wr.codejam.support.FileReadTemplate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TidyNumbers2 extends FileReadTemplate<Long, Long> {

    public static void main(String[] args) throws Exception {
        TidyNumbers2 tidyNumbers = new TidyNumbers2();
        tidyNumbers.solve(args);
    }

    @Override
    protected Function<Scanner, List<Long>> toInput() {
        return scanner -> {
            int numLength = scanner.nextInt();
            List<Long> nums = new ArrayList<>();

            for (int i = 0; i < numLength; i++) {
                nums.add(scanner.nextLong());
            }
            return nums;
        };
    }

    @Override
    protected BiConsumer<PrintWriter, List<Long>> toOutput() {
        return (writer, output) -> {
            for (int i = 0; i < output.size(); i++) {
                writer.println("Case #" + (i + 1) + ": " + output.get(i));
            }
        };
    }

    @Override
    protected Long solve(Long input) {
        int[] chars = String.valueOf(input).chars()
                .map(Character::getNumericValue)
                .toArray();

        int i = chars.length - 1;
        while (!isTidy(chars)) {
            chars[i] = 9;
            chars[i - 1]--;
            i--;
        }
        return toLong(chars);
    }

    private boolean isTidy(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private long toLong(int[] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i : chars) {
            sb.append(i);
        }
        return Long.valueOf(sb.toString());
    }

}