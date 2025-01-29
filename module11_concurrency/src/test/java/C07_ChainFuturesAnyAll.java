import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class C07_ChainFuturesAnyAll {

    @Test
    public void tasks_thenAcceptBoth() {

        record User(String name) {}

        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> 1L);
        CompletableFuture<User> getUser = CompletableFuture.supplyAsync(() -> new User("first"));

        CompletableFuture<Void> getBothAndLog =
                getId.thenAcceptBoth(getUser, (id, user) -> {
                    System.out.println(id + " " + user);
                });

        getBothAndLog.join();

    }


    @Test
    public void tasks_thenCombine() {

        record User(String name) {}

        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> 1L);
        CompletableFuture<User> getUser = CompletableFuture.supplyAsync(() -> new User("first"));

        CompletableFuture<String> getBothAndLog =
                getId.thenCombine(getUser, (id, user) -> "id:"+id +"user:"+user);

        getBothAndLog.join();

    }


    @Test
    public void tasks_thenAcceptEither() {

        record User(String name) {}

        CompletableFuture<User> getUserFromDbInstance1 =
                CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(RandomGenerator.getDefault().nextInt(100));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return new User("first from db1");
                });

        CompletableFuture<User> getUserFromDbInstance2 =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(RandomGenerator.getDefault().nextInt(110));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return new User("first from db2");
                });


        CompletableFuture<Void> eitherWouldDo =
                getUserFromDbInstance1.acceptEither(getUserFromDbInstance2, user -> System.out.println(user));
        eitherWouldDo.join();
    }

    @Test
    public void tasks_thenAcceptAllOf() {
        record User(String name) {}
        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> 1L);
        CompletableFuture<User> getUser = CompletableFuture.supplyAsync(() -> new User("first"));

        CompletableFuture<Void> all =
                CompletableFuture.allOf(getUser, getId);
        Void res = all.join(); //when exits can read both

        System.out.println("got results: getId = " + getId.join() + " getUser = " + getUser.join());
    }

    @Test
    public void tasks_thenAcceptAnyOf() {
        record User(String name) {}
        CompletableFuture<Long> getId = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(RandomGenerator.getDefault().nextInt(100));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return 1L;});

        CompletableFuture<User> getUser = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(RandomGenerator.getDefault().nextInt(110));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return new User("first");});

        CompletableFuture<?> all =
                CompletableFuture.anyOf(getUser, getId);
        Object res = all.join(); //when exits can read first result...  roughly first

        System.out.println("got first results: and it is = " +res);
    }

}
