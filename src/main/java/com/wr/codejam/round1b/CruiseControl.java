package com.wr.codejam.round1b;

import com.wr.codejam.support.FileReadTemplate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CruiseControl extends FileReadTemplate<CruiseControl.Input, Double> {

    static class Input {
        int d;
        int n;
        List<Horse> horses = new ArrayList<>();
    }

    static class Horse {
        final int pos;
        final double speed;

        Horse(int pos, double speed) {
            this.pos = pos;
            this.speed = speed;
        }
    }

    public static void main(String[] args) throws Exception {
        CruiseControl c = new CruiseControl();
        c.solve(args);
    }

    @Override
    protected Function<Scanner, List<Input>> toInput() {
        return scanner -> {
            int testCasesNum = scanner.nextInt();

            List<Input> testCases = new ArrayList<>();

            for (int i = 0; i < testCasesNum; i++) {
                int d = scanner.nextInt();
                int n = scanner.nextInt();
                Input input = new Input();
                input.d = d;
                input.n = n;
                for (int j = 0; j < n; j++) {
                    int pos = scanner.nextInt();
                    int speed = scanner.nextInt();
                    input.horses.add(new Horse(pos, speed));
                }
                testCases.add(input);
            }
            return testCases;
        };
    }

    @Override
    protected BiConsumer<PrintWriter, List<Double>> toOutput() {
        return (writer, output) -> {
            for (int i = 0; i < output.size(); i++) {
                writer.println("Case #" + (i + 1) + ": " + output.get(i));
            }
        };
    }

    @Override
    protected Double solve(Input input) {
        return input.horses.stream()
                .mapToDouble(h -> input.d * h.speed / (input.d - h.pos))
                .min().orElse(0.0);
    }
}
