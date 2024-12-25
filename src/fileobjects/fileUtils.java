package fileobjects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class fileUtils {
    public static boolean writeToFile(Path path, String content) {
        try {
            Files.write(path, content.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
