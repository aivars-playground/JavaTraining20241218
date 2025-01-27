package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ProbablePrime{

    @Param({"10","100","1000"})
    private int N;

    @Param({"64"})
    private int BIT_LENGTH;

    BigInteger probablePrime() {
        return BigInteger.probablePrime(BIT_LENGTH,
                ThreadLocalRandom.current());
    }

    @Benchmark
    public List<BigInteger> sum_of_primes(){
        List<BigInteger> pps = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            BigInteger pp =
                    BigInteger.probablePrime(BIT_LENGTH, ThreadLocalRandom.current());
            pps.add(pp);
        }
        return pps;
    }

    @Benchmark
    public List<BigInteger> sum_of_primes_resize(){
        List<BigInteger> pps = new ArrayList<>(N);  //we do not want measuring resize
        for (int i = 0; i < N; i++) {
            BigInteger pp =
                    BigInteger.probablePrime(BIT_LENGTH, ThreadLocalRandom.current());
            pps.add(pp);
        }
        return pps;
    }

    @Benchmark
    public List<BigInteger> generate_n_primes(){
        return IntStream.range(0, N)
                .mapToObj(i -> probablePrime())
                .collect(Collectors.toList());
    }

    private Random random = new Random();
    private List<Integer> integers;
        @Setup
    public void setup(){
            integers = IntStream.range(0,N)
                    .mapToObj(indx -> random.nextInt())
                    .collect(Collectors.toList());
    }

    //Benchmark                     (BIT_LENGTH)   (N)  Mode  Cnt   Score    Error  Units
    //ProbablePrime.sum_sequential            64    10  avgt   15  ? 10??           ms/op
    //ProbablePrime.sum_sequential            64   100  avgt   15  ? 10??           ms/op
    //ProbablePrime.sum_sequential            64  1000  avgt   15   0.001 ?  0.001  ms/op
    @Benchmark
    public double sum_sequential() {
        return integers.stream().mapToInt(i -> i).sum();
    }

    //Benchmark                   (BIT_LENGTH)   (N)  Mode  Cnt  Score    Error  Units
    //ProbablePrime.sum_parallel            64    10  avgt   15  0.005 ?  0.001  ms/op
    //ProbablePrime.sum_parallel            64   100  avgt   15  0.007 ?  0.001  ms/op
    //ProbablePrime.sum_parallel            64  1000  avgt   15  0.005 ?  0.001  ms/op
    @Benchmark
    public double sum_parallel() {
            //slower!!!!!!!!!!
        return integers.stream().mapToInt(i -> i).parallel().sum();
    }

    public static void main(String[] args) throws RunnerException
{
        Options opt = new OptionsBuilder()
                .include(ProbablePrime.class.getName() + ".sum_of_primes")
                .build();
        new Runner(opt).run();
    }
}