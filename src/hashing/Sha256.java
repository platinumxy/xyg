package hashing;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Implementation of the SHA-256 hashing algorithm
 * based on the pseudocode from <a href="https://en.wikipedia.org/wiki/SHA-2">wikipedia</a>
 * and code from <a href="https://rosettacode.org/wiki/SHA-256#Java">Rosetta code</a>
 */
public class Sha256 {
    public static final Sha256 ZERO_HASH = new Sha256(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }, true);

    public static final int BLOCK_SIZE = 64;
    private static final int[] INIT_HASH = new int[] { 0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19 };
    private static final int[] K = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2 };

    private final int[] hash;

    /**
     * @param data hashes provided string
     */
    public Sha256(String data) {
        this(data.getBytes());
    }

    /**
     * @param data hashes provided bytes
     */
    public Sha256(byte[] data) {
        this(data, false);
    }

    public Sha256(byte[] hash, boolean raw) {
        if (!raw) {
            this.hash = hashData(hash);
            return;
        }

        int[] temp = new int[hash.length / 4];
        ByteBuffer buffer = ByteBuffer.wrap(hash);
        for (int i = 0; i < temp.length; i++) {
            temp[i] = buffer.getInt();
        }
        this.hash = temp;
    }

    public Sha256(int[] hash, boolean raw) {
        if (raw) {
            this.hash = hash;
            return;
        }
        ByteBuffer buffer = ByteBuffer.allocate(hash.length * 4);
        for (int value : hash) {
            buffer.putInt(value);
        }
        this.hash = hashData(buffer.array());
    }

    /**
     * Alias for {@link #toString()}
     */
    public String getHashString() {
        return toString();
    }

    /**
     * returns the hash as a string of hexadecimal characters
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int b : hash) {
            sb.append(String.format("%08x", b));
        }
        return sb.toString();
    }

    /**
     * returns the raw hash
     */
    public int[] getHash() {
        return hash;
    }

    public byte[] getHashBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(hash.length * 4);
        for (int value : hash) {
            buffer.putInt(value);
        }
        return buffer.array();
    }

    public boolean equals(Sha256 other) {
        return Arrays.equals(hash, other.hash);
    }

    private int[] hashData(byte[] data) {
        int[] hash = Arrays.copyOf(INIT_HASH, INIT_HASH.length);
        byte[] padded = pad(data);

        for (int i = 0; i < padded.length / BLOCK_SIZE; i++) {
            int[] w =  new int[BLOCK_SIZE];
            for (int j = 0; j < 16; j++) {
                w[j] = (padded[i * BLOCK_SIZE + j * 4] & 0xff) << 24 |
                        (padded[i * BLOCK_SIZE + j * 4 + 1] & 0xff) << 16 |
                        (padded[i * BLOCK_SIZE + j * 4 + 2] & 0xff) << 8 |
                        (padded[i * BLOCK_SIZE + j * 4 + 3] & 0xff);
            }

            for (int j = 16; j < BLOCK_SIZE; j++) {
                int s0 = Integer.rotateRight(w[j - 15], 7)
                        ^ Integer.rotateRight(w[j - 15], 18)
                        ^ (w[j - 15] >>> 3);
                int s1 = Integer.rotateRight(w[j - 2], 17)
                        ^ Integer.rotateRight(w[j - 2], 19)
                        ^ (w[j - 2] >>> 10);
                w[j] = w[j - 16] + s0 + w[j - 7] + s1;
            }

            int a = hash[0] , b = hash[1], c = hash[2], d = hash[3],
                    e = hash[4], f = hash[5], g = hash[6], h = hash[7];

            for (int j = 0; j < BLOCK_SIZE; j++) {
                int S1 = Integer.rotateRight(e, 6)
                        ^ Integer.rotateRight(e, 11)
                        ^ Integer.rotateRight(e, 25);
                int ch = (e & f) ^ (~e & g);
                int temp1 = h + S1 + ch + K[j] + w[j];
                int S0 = Integer.rotateRight(a, 2)
                        ^ Integer.rotateRight(a, 13)
                        ^ Integer.rotateRight(a, 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);
                int temp2 = S0 + maj;

                h = g; g = f; f = e; e = d + temp1;
                d = c; c = b; b = a; a = temp1 + temp2;
            }

            hash[0] += a; hash[1] += b; hash[2] += c; hash[3] += d;
            hash[4] += e; hash[5] += f; hash[6] += g; hash[7] += h;
        }
        return hash;
    }

    private byte[] pad(byte[] data) {
        int originalLength = data.length;
        int totalLength = originalLength + 1 + 8;
        int paddingLength = (BLOCK_SIZE - (totalLength % BLOCK_SIZE)) % BLOCK_SIZE;
        int newLength = originalLength + 1 + paddingLength + 8;

        byte[] padded = Arrays.copyOf(data, newLength);
        padded[originalLength] = (byte) 0x80;

        long bitLength = (long) originalLength * 8;
        for (int i = 0; i < 8; i++) {
            padded[newLength - 1 - i] = (byte) (bitLength >>> (8 * i));
        }

        return padded;
    }
}
