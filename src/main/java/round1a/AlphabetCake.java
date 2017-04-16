package round1a;

import support.FileReadTemplate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AlphabetCake extends FileReadTemplate<Character[][], Character[][]> {

    public static void main(String[] args) throws Exception {
        AlphabetCake cake = new AlphabetCake();
        cake.solve(args);
    }

    @Override
    protected Function<Scanner, List<Character[][]>> toInput() {
        return scanner -> {
            int testCasesNum = scanner.nextInt();

            List<Character[][]> testCases = new ArrayList<>();

            for (int i = 0; i < testCasesNum; i++) {
                int rows = scanner.nextInt();
                int cols = scanner.nextInt();
                Character[][] cake = new Character[rows][cols];
                for (int j = 0; j < rows; j++) {
                    char[] row = scanner.next().toCharArray();
                    for (int k = 0; k < cols; k++) {
                        cake[j][k] = row[k];
                    }
                }
                testCases.add(cake);
            }
            return testCases;
        };
    }

    @Override
    protected BiConsumer<PrintWriter, List<Character[][]>> toOutput() {
        return (writer, output) -> {
            for (int i = 0; i < output.size(); i++) {
                writer.println("Case #" + (i + 1) + ": ");
                Character[][] solution = output.get(i);
                for (Character[] aSolution : solution) {
                    for (int k = 0; k < solution[0].length; k++) {
                        writer.print(aSolution[k]);
                    }
                    writer.println();
                }
            }
        };

    }

    @Override
    protected Character[][] solve(Character[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        for (Character[] row : input) {
            for (int j = 0; j < cols; j++) {
                if (row[j] != '?') {
                    if (j < cols - 1 && row[j + 1] == '?') {
                        forwFill(row, j);
                    }
                    if (j > 0 && row[j - 1] == '?') {
                        backFill(row, j);
                    }
                }
            }
        }
        for (int i = 1; i < rows; i++) {
            if (input[i][0] == '?' && input[i - 1][0] != '?') {
                input[i] = input[i - 1];
            }
        }

        for (int i = rows - 2; i >= 0; i--) {
            if (input[i][0] == '?' && input[i + 1][0] != '?') {
                input[i] = input[i + 1];
            }
        }

        return input;
    }

    private void backFill(Character[] input, int fromIndex) {
        char c = input[fromIndex];
        int k = fromIndex - 1;
        while (k >= 0 && input[k] == '?') {
            input[k] = c;
            k--;
        }
    }

    private void forwFill(Character[] input, int fromIndex) {
        char c = input[fromIndex];
        int k = fromIndex + 1;
        while (k < input.length && input[k] == '?') {
            input[k] = c;
            k++;
        }
    }
}
