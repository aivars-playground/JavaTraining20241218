import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InOut {

    static String filePath = "src/test/resources/in.txt";

    @Test
    void readfile() {

        File file = new File(filePath);
        System.out.println(file.exists());

        Path path = Path.of(filePath);
        System.out.println(Files.exists(path));  //understands symbolic links

        Path withVarargs = Path.of("src", "test", "resources", "in.txt");
        System.out.println(Files.exists(withVarargs));

        Path onClassPath = Path.of("src", "test", "resources", "in.txt");
        System.out.println(Files.exists(withVarargs));

    }

    @Test
    void readFile() throws IOException {

        assertThrows(
            IOException.class,
            () -> Files.readString(Path.of(filePath+"_copy"))    //this one throws an exception
        );


        var path = Path.of(filePath);
        if (Files.exists(Path.of(filePath)) && Files.isReadable(path)) {
            var fileContent = Files.readString(path);
            System.out.println(fileContent);
        } else {
            System.out.println(filePath + " does not eist or is not readable");
        }
    }


    static boolean isFileReadible(Path path) {
        Objects.requireNonNull(path);
        return Files.isRegularFile(path) && Files.isReadable(path);
    }

    static String readSmallFile(Path path) throws IOException {
        return Files.readString(path);
    }

    @Test
    void readableFileFile() throws IOException {
        var path = Path.of(filePath);
        if (isFileReadible(path)) {
            var fileContent = readSmallFile(path);
            System.out.println(fileContent);
        }

        var pathDoesNotExist = Path.of(filePath+"_copy");
        if (isFileReadible(pathDoesNotExist)) {
            var fileContent = readSmallFile(pathDoesNotExist);
        } else {
            System.out.println(pathDoesNotExist + " does not exist");
        }
    }



    static List<String> readLineByLine(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    @Test
    void readableFileLineByLine() throws IOException {
        var path = Path.of(filePath);
        if (isFileReadible(path)) {
            var fileContent = readLineByLine(path);
            fileContent.stream().forEach(System.out::println);
        }
    }

    static BufferedReader readLargeFile(Path path) throws IOException {
        return Files.newBufferedReader(path);
    }

    @Test
    void readableLargeFileLine() throws IOException {
        var path = Path.of(filePath);
        if (isFileReadible(path)) {
            var fileContent = readLargeFile(path);
            String line;
            while((line = fileContent.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    @Test
    void readableLargeFileStream() throws IOException {
        var path = Path.of(filePath);
        if (isFileReadible(path)) {
            var fileContent = readLargeFile(path).lines();
            fileContent.forEach(System.out::println);
        }
    }

    @Test
    void readableLargeFileStreamOptimised() throws IOException {
        var path = Path.of(filePath);
        if (isFileReadible(path)) {
            Files.lines(path).forEach(System.out::println);
        }
    }

    static void writeSmallFile(String path, String content, boolean append) throws IOException {
        Files.writeString(Path.of(path), content, (append)?StandardOpenOption.APPEND:StandardOpenOption.CREATE_NEW); //do not overwrite option
    }


    static String filePathOut = "src/test/resources/out.txt";

    @Test
    void testWriteSmallFIle() throws IOException {

        if (isFileReadible(Path.of(filePathOut))) {
            Files.delete(Path.of(filePathOut));
        }

        writeSmallFile(filePathOut,
                """
                aa
                bb
                cc
                """, false);

        assertThrows(
                FileAlreadyExistsException.class,
                () -> writeSmallFile(filePathOut,"ddd", false)
        );


        //not optional
        writeSmallFile(filePathOut,"eee", true);
    }

    @Test
    void testWriteToFile_NotOptional_LineByLineAppend() throws IOException {

        if (isFileReadible(Path.of(filePathOut))) {
            Files.delete(Path.of(filePathOut));
        }

        List<String> lines = List.of("line1", "line2", "line3");

        Files.writeString(Path.of(filePathOut), "");
        for (String line : lines) {
            Files.writeString(Path.of(filePathOut), line + System.lineSeparator(), StandardOpenOption.APPEND);
        }

    }


    @Test
    void testWriteToFile_SmallFileWhole() throws IOException {

        if (isFileReadible(Path.of(filePathOut))) {
            Files.delete(Path.of(filePathOut));
        }

        List<String> lines = List.of("line1", "line2", "line3");

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line + System.lineSeparator());
        }
        Files.writeString(Path.of(filePathOut), sb.toString(), StandardOpenOption.CREATE_NEW);

    }

    @Test
    void testWriteToFile_LargerFile() throws IOException {

        if (isFileReadible(Path.of(filePathOut))) {
            Files.delete(Path.of(filePathOut));
        }

        List<String> lines = List.of("line1", "line2", "line3");

        try (FileWriter fileWriter = new FileWriter(filePathOut)) {
            lines.stream().forEach(line -> {
                try {
                    fileWriter.write(line+System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    void testCopyFile() throws IOException {

        //see options
        Files.copy(Path.of(filePath), Path.of(filePathOut));
    }

}
