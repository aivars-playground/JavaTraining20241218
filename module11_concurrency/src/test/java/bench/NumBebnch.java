package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class NumBebnch {

    @Param({"10"})
    private int N;

    @Param({"1000000"})
    private int NS;


    Integer[] arrayOfIntegers = null;

    @Setup
    public void setup() {
        arrayOfIntegers = new Integer[N];
        for (int i = 0; i < N; i++) {
            arrayOfIntegers[i] = i;
        }
    }

    @Benchmark
    public int benchmark_sum() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arrayOfIntegers[i];
        }
        return sum;
    }

    ArrayList<Integer> arrayList = new ArrayList<>();
    @Benchmark
    public void benchmark_Arr() {
        arrayList = IntStream.range(0, NS)
                .map(i -> 1*10)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    LinkedList<Integer> linkedListList = new LinkedList<>();
    @Benchmark
    public void benchmark_linkedListList() {
        linkedListList = IntStream.range(0, NS)
                .map(i -> 1*10)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
    }




    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NumBebnch.class.getName())
                .build();
        new Runner(opt).run();
    }
}