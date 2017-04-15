package support;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SampleSolver extends FileReadTemplate<SampleSolver.Input, SampleSolver.Output> {

    public static void main(String[] args) throws Exception {
        SampleSolver solver = new SampleSolver();
        solver.solve(args);
    }

    @Override
    protected Function<Scanner, List<Input>> toInput() {
        return scanner -> {
            List<Input> inputs = new ArrayList<>();
            // read using scanner
            return inputs;
        };
    }

    @Override
    protected BiConsumer<PrintWriter, List<Output>> toOutput() {
        return (writer, output) -> {
            for (int i = 0; i < output.size(); i++) {
                writer.println("Case #" + (i + 1) + ": ");
                // print output
            }
        };
    }

    @Override
    protected Output solve(Input input) {
        return new Output();
    }

    static class Input {
    }

    static class Output {
    }
}
