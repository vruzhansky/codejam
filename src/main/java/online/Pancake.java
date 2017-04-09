package online;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Pancake {
    static class Input {
        boolean[] pancake;
        int flipper;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(flip(new boolean[]{false, false, false, true, false, true, true, false}, 3));
//        System.out.println(flip(new boolean[]{true, true, true, true, true}, 4));
//        System.out.println(flip(new boolean[]{false, true, false, true, false}, 4));
        //+++--+++ 3
//        System.out.println(flip(new boolean[]{true, true, true, false, false, true, true, true}, 3));
        //---------- 10
//        System.out.println(flip(new boolean[]{false,false,false,false,false,false,false,false,false,false}, 10));
        //---- 3
        System.out.println(flip(new boolean[]{false, false, false, false}, 3));
        String filename;
        if (0 < args.length) {
            filename = args[0];
        } else {
            filename = getFilenameFromSystemIn();
        }

        Input[] inputs = readFile(Paths.get("data/" + filename).toAbsolutePath().toFile());

        int[] solution = new int[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            solution[i] = flip(inputs[i].pancake, inputs[i].flipper);
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
                char[] chars = s[0].toCharArray();
                boolean[] pancake = new boolean[chars.length];
                for (int j = 0; j < chars.length; j++) {
                    pancake[j] = '+' == chars[j];
                }
                input.pancake = pancake;
                input.flipper = Integer.parseInt(s[1]);
                inputs[i] = input;
            }
            return inputs;
        }
    }

    private static void saveSolution(String fileName, int[] solution) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("results/" + fileName.replace(".in", ".out"), "UTF-8");
        for (int i = 0; i < solution.length; i++) {
            String res = solution[i] == -1 ? "IMPOSSIBLE" : String.valueOf(solution[i]);
            writer.println("Case #" + (i + 1) + ": " + res);
        }
        writer.close();
    }

    public static int flip(boolean[] pancake, int flipper) {
        int length = pancake.length;
        int left = 0, right = length - 1;
        int numFlips = 0;
        while (left <= right) {
            boolean isFlipped;
            if (!pancake[left]) {
                isFlipped = flipRight(pancake, left, flipper);
                left++;
                numFlips += isFlipped ? 1 : 0;
            } else {
                left++;
            }
            if (left <= right) {
                if (!pancake[right]) {
                    isFlipped = flipLeft(pancake, right, flipper);
                    right--;
                    numFlips += isFlipped ? 1 : 0;
                } else {
                    right--;
                }
            }
        }

        return isHappy(pancake) ? numFlips : -1;
    }

    private static boolean flipRight(boolean[] pancake, int start, int flipper) {
        if (start + flipper < pancake.length + 1) {
            for (int i = start; i < start + flipper; i++) {
                pancake[i] = !pancake[i];
            }
            return true;
        }
        return false;
    }

    private static boolean flipLeft(boolean[] pancake, int start, int flipper) {
        if (start - flipper > -1) {
            for (int i = start; i > start - flipper; i--) {
                pancake[i] = !pancake[i];
            }
            return true;
        }
        return false;
    }

    private static boolean isHappy(boolean[] a) {
        for (boolean element : a) {
            if (!element) {
                return false;
            }
        }
        return true;
    }
}
