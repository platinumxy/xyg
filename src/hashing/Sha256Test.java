package hashing;

import org.junit.jupiter.api.Test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class Sha256Test {
    private int[] generateMessageDigestHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data);
        int[] hash = new int[hashBytes.length / 4];
        for (int i = 0; i < hash.length; i++) {
            hash[i] = (hashBytes[i * 4] & 0xff) << 24 |
                    (hashBytes[i * 4 + 1] & 0xff) << 16 |
                    (hashBytes[i * 4 + 2] & 0xff) << 8 |
                    (hashBytes[i * 4 + 3] & 0xff);
        }
        return hash;
    }

    @Test
    void string_hashing() throws NoSuchAlgorithmException {

        for (int i = 0; i < 100_000; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < Math.random() * 10; j++) {
                sb.append((char) (Math.random() * 0x10ffff));
            }
            String data = sb.toString();
            Sha256 sha256 = new Sha256(data);
            int[] shaHash = sha256.getHash();
            int[] realHash = generateMessageDigestHash(data.getBytes());
            assert (Arrays.equals(shaHash, realHash));
        }
    }

    @Test
    void byte_hashing() throws NoSuchAlgorithmException {
        for (int i = 0; i < 100_000; i++) {
            byte[] data = new byte[(int) (Math.random() * 100)];
            for (int j = 0; j < data.length; j++) {
                data[j] = (byte) (Math.random() * 256);
            }
            Sha256 sha256 = new Sha256(data);
            int[] shaHash = sha256.getHash();
            int[] realHash = generateMessageDigestHash(data);
            assert (Arrays.equals(shaHash, realHash));
        }
    }

    @Test
    void raw_creation() {
        for (int i = 0; i < 100_000; i++) {
                byte[] data = new byte[(int) (Math.random() * 100)];
                for (int j = 0; j < data.length; j++) {
                    data[j] = (byte) (Math.random() * 256);
                }
                Sha256 sha256 = new Sha256(data);
                Sha256 sha256Raw = new Sha256(sha256.getHash(), true);
                assert (Arrays.equals(sha256.getHash(), sha256Raw.getHash()));
        }
    }
}