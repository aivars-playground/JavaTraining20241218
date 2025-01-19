import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZipFileSystem {

    @Test
    void copy_zip_file() throws IOException, URISyntaxException {

        try (FileSystem zip = openZip(Paths.get("../logs/example.zip"))) {
            copyTo(zip, "example");
        }

        try (FileSystem zip = openZip(Paths.get("../logs/example.zip"))) {
            writeFileIn(zip,  new String []{"as", "if", "even", "care","dot,dot,dot"});
        }
    }

    private FileSystem openZip(Path path) throws IOException, URISyntaxException {
        Map<String,String> props = new HashMap<>();
        props.put("create", "true");
        URI uri = new URI("jar:file",path.toUri().getPath(), null);
        FileSystem zipFs = FileSystems.newFileSystem(uri, props);
        return zipFs;
    }

    private void copyTo(FileSystem target, String content) throws IOException, URISyntaxException {
        Path destFile = target.getPath("/meta.inf");
        Files.write(destFile, content.getBytes());
    }

    private void writeFileIn(FileSystem target, String[] texr) throws IOException, URISyntaxException {
        try (BufferedWriter writer = Files.newBufferedWriter(target.getPath("/anotherfile.txt"))) {
            for (String s : texr) {
                writer.write(s);
                writer.newLine();
            }
        }
    }
}
