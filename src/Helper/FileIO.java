package Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileIO {
    /**
     * read data from local file, implemented by NIO
     * @param path the path you want to read
     * @return lines from the file, with \n
     * @throws IOException
     */
    public static String read(String path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Stream<String> stringStream = Files.lines(Paths.get(path).toAbsolutePath());
        stringStream.forEach(x -> stringBuilder.append(x.equals("\n") ? null : x).append("\n"));
        return stringBuilder.toString();
    }

    /**
     * write data into local file, implemented by NIO
     * @param path the path you want to read
     * @param content the content you want to write
     * @throws IOException
     */
    public static void write(String path, String content) throws IOException {
        Files.write(Paths.get(path).toAbsolutePath(), content.getBytes());
    }
}
