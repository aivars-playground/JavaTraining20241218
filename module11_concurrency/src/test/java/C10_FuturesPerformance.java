import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class C10_FuturesPerformance {

    void sleep(long millis) {
        try {Thread.sleep(millis);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    CompletableFuture<Void> prepare() {
        record User(Long id, String name) {}

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return List.of(1L, 2L, 3L);
        };

        Function<List<Long>,List<User>> fetchUsers = ids -> {
            sleep(200);
            return ids.stream().map(id -> new User(id, "us_"+id)).collect(Collectors.toList());
        };

        Consumer<List<User>> saveUsers = users -> {
            sleep(200);
            System.out.println("saveUsers "+ Thread.currentThread().getName());
            users.forEach(System.out::println);
        };

        CompletableFuture<Void> start = new CompletableFuture<>();
        CompletableFuture<List<Long>> suppy = start.thenApply(nil -> supplyIDs.get());
        CompletableFuture<List<User>> fetch = suppy.thenApply(fetchUsers);
        CompletableFuture<Void> save = fetch.thenAccept(saveUsers);

        return start;
    }

    @Test
    public void tasks_example_main() {
        CompletableFuture<Void> start = prepare();
        start.complete(null);
        //runs on main
    }

    @Test
    public void tasks_example_fork_join() {
        CompletableFuture<Void> start = prepare();
        start.completeAsync(() -> null); //not what we want... supplier = null
        sleep(1000);
    }

    @Test
    public void tasks_example_executor() {
        CompletableFuture<Void> start = prepare();

        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            start.completeAsync(() -> null, executor);
        }
    }

    @Test
    public void tasks_example_http() {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.google.com"))
                .build();

        CompletableFuture<HttpResponse<String>> future =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        int code = future.join().statusCode();
        System.out.println(code);

    }

    @Test
    public void tasks_example_http_g() {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.google.com"))
                .build();

        CompletableFuture<HttpResponse<String>> future =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        future.thenAccept(res -> {
            String body = res.body();
            System.out.println("body: " + body.length() + " code:"+res.statusCode() + " " + Thread.currentThread().getName());
        });

        future.join();

    }


    @Test
    public void tasks_example_http_google() {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.google.com"))
                .build();

        CompletableFuture<Void> start = new CompletableFuture<>();

        CompletableFuture<HttpResponse<String>> future =
                start.thenCompose(
                        nil -> client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                );


        var aa = future.thenAccept(res -> {
            String body = res.body();
            System.out.println("body: " + body.length() + " code:"+res.statusCode() + " " + Thread.currentThread().getName());
        });

        start.complete(null);
        aa.join();
    }


}
