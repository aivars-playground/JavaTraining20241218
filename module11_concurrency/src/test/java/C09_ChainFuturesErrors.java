import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class C09_ChainFuturesErrors {

    @Test
    public void tasks_thenAcceptBoth() {

        record User(String name) {}

        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> 1L);
        Function<Long, Optional<User>> readUser = (longs -> Optional.of(new User( "user ")));

        getId.thenApply(readUser).exceptionally(
                err -> Optional.empty()
        );



    }
}
