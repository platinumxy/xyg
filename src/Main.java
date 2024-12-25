import hashing.Sha256;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String[] msgs = {
            "Hello, world!",
            "The quick brown fox jumps over the lazy dog",
            "a"
        };
        for (String msg : msgs) {
            System.out.println("Hash of '" + msg + "': " + (new Sha256(msg)).toString());
        }
    }
}