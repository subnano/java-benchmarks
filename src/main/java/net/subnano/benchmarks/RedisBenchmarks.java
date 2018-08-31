package net.subnano.benchmarks;

import io.lettuce.core.RedisFuture;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Mark Wardell
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 3)
@Fork(3)
public class RedisBenchmarks {

    private static final String KEY_1K = "bench/1k";
    private static final String KEY_4K = "bench/4k";
    private static final String KEY_100K = "bench/100k";
    private static final String KEY_1M = "bench/1M";

    public static void main(String[] args) throws RunnerException {
        System.setProperty("jmh.ignoreLock", "true");
        Options options = new OptionsBuilder()
                .include(RedisBenchmarks.class.getSimpleName())
                //.addProfiler(StackProfiler.class)
                //.addProfiler(GCProfiler.class)
                .build();
        new Runner(options).run();
    }

    static String randomString(int len) {
        Random random = new Random();
        return random.ints(48, 123)
                .filter(i -> (i < 58) || (i > 64 && i < 91) || (i > 96))
                .limit(len)
                .collect(StringBuilder::new, (sb, i) -> sb.append((char) i), StringBuilder::append).toString();
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        RedisWriter writer = new RedisWriter();
        String val_1k = randomString(1_000);
        String val_4k = randomString(4_000);
        String val_100k = randomString(100_000);
        String val_1m = randomString(1_000_000);
    }

    @Benchmark
    public void set_1k(BenchmarkState state) throws InterruptedException {
        RedisFuture<String> future = state.writer.set(KEY_1K, state.val_1k);
        future.await(10, TimeUnit.SECONDS);
    }

    @Benchmark
    public void set_4k(BenchmarkState state) throws InterruptedException {
        RedisFuture<String> future = state.writer.set(KEY_4K, state.val_4k);
        future.await(10, TimeUnit.SECONDS);
    }

    @Benchmark
    public void set_100k(BenchmarkState state) throws InterruptedException {
        RedisFuture<String> future = state.writer.set(KEY_100K, state.val_100k);
        future.await(10, TimeUnit.SECONDS);
    }

    @Benchmark
    public void set_1m(BenchmarkState state) throws InterruptedException {
        RedisFuture<String> future = state.writer.set(KEY_1M, state.val_1m);
        future.await(10, TimeUnit.SECONDS);
    }

}
