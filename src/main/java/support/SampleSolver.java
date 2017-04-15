package support;

import java.io.PrintWriter;
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
        return null;
    }

    @Override
    protected BiConsumer<PrintWriter, List<Output>> toOutput() {
        return null;
    }

    @Override
    protected Output solve(Input input) {
        return null;
    }

    static class Input {
    }

    static class Output {
    }
}
