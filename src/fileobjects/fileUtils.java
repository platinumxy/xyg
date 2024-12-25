package fileobjects;

import hashing.Sha256;

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

    public static Path getShaPath(Path objPath, Sha256 sha) {
        return getShaPath(objPath, sha.toString());
    }

    public static Path getShaPath(Path objPath, String sha) {
        return objPath.resolve(sha.substring(0, 2)).resolve(sha.substring(2));
    }

}
