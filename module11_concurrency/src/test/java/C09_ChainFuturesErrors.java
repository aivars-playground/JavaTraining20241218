import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class C09_ChainFuturesErrors {

    @Test
    public void tasks_exceptionally() {

        record User(String name) {}

        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> {
            if(true) throw new RuntimeException();
            return 1L;
        });
        Function<Long, Optional<User>> readUser = (longs -> Optional.of(new User( "user ")));

        var cf = getId.thenApply(readUser).exceptionally(
                err -> Optional.empty()
        );

        System.out.println(cf.join());

        try {
            getId.join();
        } catch (CompletionException e) {
            System.out.println("as expected, e: " + e.getMessage());
        }
    }


    @Test
    public void tasks_whencomplet() {

        record User(String name) {}

        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> {
            if(true) {
                throw new RuntimeException();
            }
            return 1L;
        });
        Function<Long, Optional<User>> readUser = (longs -> Optional.of(new User( "user ")));

        var cf = getId.thenApply(readUser).whenComplete(
                (result, err) -> {
                    if (err != null) {
                        System.out.println("err, e: " + err.getMessage());
                    } else if (result != null) {
                        System.out.println("result: " + result);
                    }
                }
        );   //rethrows error

        try {
            cf.join();
        } catch (CompletionException e) {
            System.out.println("as expected, e: " + e.getMessage());
        }


    }



    @Test
    public void tasks_handle() {

        record User(String name) {}

        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> {
            if(true) {
                throw new RuntimeException();
            }
            return 1L;
        });
        Function<Long, Optional<User>> readUser = (longs -> Optional.of(new User( "user ")));

        var cf = getId.thenApply(readUser).handle(
                (a,b) ->  {
                        if(a!=null)
                            return a;
                        else
                            return Optional.empty();
                }
        );

        System.out.println(cf.join());
    }



}
