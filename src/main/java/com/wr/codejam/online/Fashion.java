package com.wr.codejam.online;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Fashion {
    static class Input {
        char[][] stage;
        int size;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(place(new char[][]{{'.', '.'}, {'.', '.'}}, 2));
//        System.out.println(place(new char[][]{{'o'}, {'o'}}, 1));
//        System.out.println(place(new char[][]{{'.', '.', '.'}, {'+', '+', '+'}, {'x', '.', '.'}}, 3));
        System.out.println(place(new char[][]{{'.', '.', '.'}, {'.', '.', '.'}, {'.', '.', '.'}}, 3));
        String filename;
        if (0 < args.length) {
            filename = args[0];
        } else {
            filename = getFilenameFromSystemIn();
        }

        Input[] inputs = readFile(Paths.get("data/" + filename).toAbsolutePath().toFile());

        Result[] solution = new Result[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            solution[i] = place(inputs[i].stage, inputs[i].size);
        }
        saveSolution(filename, solution);
    }

    private static String getFilenameFromSystemIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    private static Input[] readFile(File file) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            int inputLength = Integer.parseInt(br.readLine());
            Input[] inputs = new Input[inputLength];

            for (int i = 0; i < inputLength; i++) {
                String[] s = br.readLine().split("\\s+");
                Input input = new Input();
                int size = Integer.parseInt(s[0]);
                int dataLength = Integer.parseInt(s[1]);

                char[][] stage = new char[size][size];
                for (int j = 0; j < size; j++) {
                    Arrays.fill(stage[j], '.');
                }

                for (int j = 0; j < dataLength; j++) {
                    String[] s1 = br.readLine().split("\\s+");
                    char c = s1[0].toCharArray()[0];
                    int row = Integer.parseInt(s1[1]);
                    int col = Integer.parseInt(s1[2]);
                    stage[row - 1][col - 1] = c;
                }
                input.stage = stage;
                input.size = size;
                inputs[i] = input;
            }
            return inputs;
        }
    }

    private static void saveSolution(String fileName, Result[] solution) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("results/" + fileName.replace(".in", ".out"), "UTF-8");
        for (int i = 0; i < solution.length; i++) {
            Result result = solution[i];
            writer.println("Case #" + (i + 1) + ": " + result.stylePoints + " " + result.models.size());
            for (Model model : result.models) {
                writer.println(model.type + " " + model.i + " " + model.j);
            }
        }
        writer.close();
    }

    static class Result {
        int stylePoints;
        List<Model> models = new ArrayList<>();

        @Override
        public String toString() {
            return "Result{" +
                    "stylePoints=" + stylePoints +
                    ", models=" + models +
                    '}';
        }
    }

    static class Model {
        final char type;
        final int i;
        final int j;

        Model(char type, int i, int j) {
            this.type = type;
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "Model{" +
                    "type=" + type +
                    ", i=" + i +
                    ", j=" + j +
                    '}';
        }
    }

    public static Result place(char[][] stage, int size) {
        Result res = new Result();

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                char model = stage[i][j];
                boolean subs = model == 'o';

                if (!subs) {
                    stage[i][j] = 'o';
                    if (doesntBreakConstraints(stage, size, i, j)) {
                        res.models.add(new Model('o', i + 1, j + 1));
                        subs = true;
                    } else stage[i][j] = model;
                }

                if (!subs && model == '.') {
                    stage[i][j] = '+';

                    if (doesntBreakConstraints(stage, size, i, j)) {
                        res.models.add(new Model('+', i + 1, j + 1));
                        subs = true;
                    } else stage[i][j] = model;
                }

                if (!subs && model == '.') {
                    stage[i][j] = 'x';

                    if (doesntBreakConstraints(stage, size, i, j)) {
                        res.models.add(new Model('x', i + 1, j + 1));
//                        subs = true;
                    } else stage[i][j] = model;
                }

                res.stylePoints += stage[i][j] == '.' ? 0 : stage[i][j] == 'o' ? 2 : 1;
            }
        }
        return res;
    }

    private static boolean doesntBreakConstraints(char[][] stage, int size, int row, int col) {
        return checkCol(stage, row, col, size) &&
                checkRow(stage, row, col, size) &&
                checkDiagonal(stage, row, col, size);
    }

    private static boolean checkRow(char[][] stage, int row, int col, int size) {
        char c = stage[row][col];
        if (c == '+') return true;
        for (int i = 0; i < size; i++) {
            if (i != col && !(stage[row][i] == '.' || stage[row][i] == '+')) return false;
        }
        return true;
    }

    private static boolean checkCol(char[][] stage, int row, int col, int size) {
        char c = stage[row][col];
        if (c == '+') return true;
        for (int i = 0; i < size; i++) {
            if (i != row && !(stage[i][col] == '.' || stage[i][col] == '+')) return false;
        }
        return true;
    }

    private static boolean checkDiagonal(char[][] stage, int row, int col, int size) {
        char c = stage[row][col];
        if (c == 'x') return true;
        //first \
        int cRow = row - 1, cCol = col - 1;
        while (cRow >= 0 && cCol >= 0) {
            if (!(stage[cRow][cCol] == '.' || stage[cRow][cCol] == 'x')) return false;
            cRow--;
            cCol--;
        }
        cRow = row + 1;
        cCol = col + 1;
        while (cRow < size && cCol < size) {
            if (!(stage[cRow][cCol] == '.' || stage[cRow][cCol] == 'x')) return false;
            cRow++;
            cCol++;
        }
        //second /
        cRow = row - 1;
        cCol = col + 1;
        while (cRow >= 0 && cCol < size) {
            if (!(stage[cRow][cCol] == '.' || stage[cRow][cCol] == 'x')) return false;
            cRow--;
            cCol++;
        }
        cRow = row + 1;
        cCol = col - 1;
        while (cRow < size && cCol >= 0) {
            if (!(stage[cRow][cCol] == '.' || stage[cRow][cCol] == 'x')) return false;
            cRow++;
            cCol--;
        }
        return true;
    }

}
