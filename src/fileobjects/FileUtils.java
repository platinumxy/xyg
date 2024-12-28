package fileobjects;

import hashing.Sha256;
import logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static boolean writeToFile(Path path, String content) {
        return writeToFile(path, content.getBytes());
    }

    public static boolean writeToFile(Path path, byte[] content) {
        try {
            Files.write(path, content);
            return true;
        } catch (IOException e) {
            Logger.error("Failed to write to file " + path + ": " + e.getMessage());
            return false;
        }
    }

    public static byte[] readBytesFromFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            Logger.error("Failed to read from file " + path + ": " + e.getMessage());
            return null;
        }
    }

    public static String readStringFromFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            Logger.error("Failed to read from file " + path + ": " + e.getMessage());
            return null;
        }
    }

    public static Path getShaPath(Path objPath, Sha256 sha) {
        return getShaPath(objPath, sha.toString());
    }

    public static Path getShaPath(Path objPath, String sha) {
        return objPath.resolve(sha.substring(0, 2)).resolve(sha.substring(2));
    }

    public static BlobObj createBlob(Path blobPath) {
        assert blobPath.toFile().isFile() : "Path should be a file";
        String name = blobPath.getFileName().toString();
        try {
            return new BlobObj(name, Files.readString(blobPath));
        } catch (IOException e) {
            Logger.error("Failed to read file" + name + ": " + e.getMessage());
            return null;
        }
    }
}
