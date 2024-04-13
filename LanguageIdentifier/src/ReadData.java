import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.ByteArrayOutputStream;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;


public class ReadData extends SimpleFileVisitor<Path> {

    private String path;
    private static List<File> files = new ArrayList<>();

    public ReadData(String path) {
        this.path = path;
        try{
            Files.walkFileTree(Paths.get(path), this);

        }   catch (Exception e){
            e.printStackTrace();
        }
    }
    public FileVisitResult visitFile(Path file, BasicFileAttributes atts)
    {
        if (file.getFileName().toString().endsWith(".txt")) {
            files.add(file.toFile());
        }
        return FileVisitResult.CONTINUE;
    }

    public Map<String, List<String>> readData() {
        Map<String, List<String>> result = new HashMap<>();
        for(File file : files) {
            try (
                    FileChannel inputChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
            ) {
                ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                int bytesRead;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while ((bytesRead = inputChannel.read(buffer)) != -1) {
                    buffer.flip();
                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        byteArrayOutputStream.write(buffer.get());
                    }
                    buffer.clear();
                }
                String[] resultPath = file.getAbsolutePath().split(Pattern.quote(File.separator));
                result.putIfAbsent(resultPath[resultPath.length-2].toLowerCase(), new ArrayList<>());
                result.get(resultPath[resultPath.length-2].toLowerCase()).add(byteArrayOutputStream.toString(StandardCharsets.UTF_8).toLowerCase());

            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return result;

    }
}
