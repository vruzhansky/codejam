package support;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class FileReadTemplate<I, O> {

    protected void solve(String[] args) throws Exception {
        String filename = args.length > 0 ? args[0] : getFilenameFromSystemIn();
        List<I> input = readFile(filename, toInput());
        List<O> output = new ArrayList<>();
        for (I i : input) {
            output.add(solve(i));
        }
        saveSolution(filename, toOutput(), output);
    }

    protected abstract Function<Scanner, List<I>> toInput();

    protected abstract BiConsumer<PrintWriter, List<O>> toOutput();

    protected abstract O solve(I input);

    private String getFilenameFromSystemIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    private List<I> readFile(String filename, Function<Scanner, List<I>> toInput) throws Exception {
        File file = Paths.get("data/" + filename).toAbsolutePath().toFile();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return toInput.apply(new Scanner(br));
        }
    }

    private void saveSolution(String fileName, BiConsumer<PrintWriter, List<O>> toOutput, List<O> output) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("results/" + fileName.replace(".in", ".out"), "UTF-8");
        toOutput.accept(writer, output);
        writer.close();
    }
}
