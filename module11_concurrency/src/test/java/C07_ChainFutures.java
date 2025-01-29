import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class C07_ChainFutures {

    @Test
    public void tasks_chaining() {

        record User(Long id, String name) {}

        Function<List<Long>, List<User>> readUsers =
                longs -> longs.stream().map(l -> new User(l, "user " + l)).collect(Collectors.toList());

        CompletableFuture<List<User>> future =
                CompletableFuture.supplyAsync(() -> List.of(1L, 2L, 3L))
                        .thenApply(longs -> readUsers.apply(longs));

        future.thenRun(() -> System.out.println("Done") );

        future.thenAccept(users -> System.out.println("users size:"+users.size()));
    }

    @Test
    public void tasks_compose() {

        record User(Long id, String name) {}

        Function<List<Long>, CompletableFuture<List<User>>> readUsers =
                longs ->
                    CompletableFuture.supplyAsync(() -> {
                                    System.out.println(">>l:"+longs.size());
                                    return longs.stream().map(l -> new User(l, "user " + l)).collect(Collectors.toList());
                                }
                            );

        CompletableFuture<List<User>> future =
                CompletableFuture
                        .supplyAsync(() -> List.of(1L, 2L, 3L))
                        .thenCompose(readUsers);

        future.thenRun(() -> System.out.println("Done") );

        future.thenAccept(users -> System.out.println("users size:"+users.size()));
    }

}
