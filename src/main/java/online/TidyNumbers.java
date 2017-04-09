package online;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class TidyNumbers {
    public static void main(String[] args) throws Exception {
        String filename;
        if (0 < args.length) {
            filename = args[0];
        } else {
            filename = getFilenameFromSystemIn();
        }

        long[] numbers = readFile(Paths.get("data/" + filename).toAbsolutePath().toFile());

        long[] solution = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            solution[i] = maxTidy3(numbers[i]);
        }
        saveSolution(filename, solution);
    }

    private static String getFilenameFromSystemIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    private static long[] readFile(File file) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            int numLength = Integer.parseInt(br.readLine());
            long[] nums = new long[numLength];

            for (int i = 0; i < numLength; i++) {
                nums[i] = Long.parseLong(br.readLine());
            }
            return nums;
        }
    }

    private static void saveSolution(String fileName, long[] solution) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("results/" + fileName.replace(".in", ".out"), "UTF-8");
        for (int i = 0; i < solution.length; i++) {
            writer.println("Case #" + (i + 1) + ": " + solution[i]);
        }
        writer.close();
    }

    public static long maxTidy3(long num) {
        int[] chars = String.valueOf(num).chars()
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

    private static boolean isTidy(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        return true;
    }

    private static long toLong(int[] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i : chars) {
            sb.append(i);
        }
        return Long.valueOf(sb.toString());
    }

}