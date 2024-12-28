package commands.stagingmanager;

import fileobjects.FileUtils;
import hashing.Sha256;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class StagingEnvironment {
    public final static byte CURRENT_INDEX_VERSION = 1;

    private final Set<StagedFile> stagedFiles;

    public StagingEnvironment() { stagedFiles = Set.of(); }
    public StagingEnvironment(Set<StagedFile> stagedFiles) { this.stagedFiles = stagedFiles; }
    public StagingEnvironment(Path indexFile) throws IllegalArgumentException {
        this.stagedFiles = loadIndex(indexFile);
    }

    private static Set<StagedFile> loadIndex(Path indexPath) throws IllegalArgumentException {
        Set<StagedFile> staged = new HashSet<>();
        byte[] indexBytes = FileUtils.readBytesFromFile(indexPath);

        if (indexBytes == null || indexBytes.length < 8 || !new String(indexBytes, 0, 4).equals("INDX")) {
            throw new IllegalArgumentException("Invalid index file format");
        }

        byte version = indexBytes[4];
        if (version > CURRENT_INDEX_VERSION) {
            throw new IllegalArgumentException("Unsupported index version");
        }

        int fileCount = ByteBuffer.wrap(indexBytes, 5, 4).getInt();
        int offset = 9;

        for (int i = 0; i < fileCount; i++) {
            ByteBuffer buffer = ByteBuffer.wrap(indexBytes, offset, indexBytes.length - offset);

            int pathLength = buffer.getInt();
            offset += 4;
            byte[] pathBytes = new byte[pathLength];
            buffer.get(pathBytes);
            offset += pathLength;

            byte[] hashBytes = new byte[32];
            buffer.get(hashBytes);
            offset += 32;

            long lastModified = buffer.getLong();
            offset += 8;

            staged.add(new StagedFile(Path.of(new String(pathBytes)), new Sha256(hashBytes, true), lastModified));
        }
        return staged;
    }

    public boolean saveIndex(Path indexPath) {
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            buf.write("INDX".getBytes());
            buf.write(CURRENT_INDEX_VERSION);
            buf.write(ByteBuffer.allocate(4).putInt(stagedFiles.size()).array());

            for (StagedFile file : stagedFiles) {
                byte[] pathBytes = file.path().toAbsolutePath().toString().getBytes();
                byte[] hashBytes = file.hash() == null ? new byte[32] : file.hash().getHashBytes();
                byte[] lastModified = ByteBuffer.allocate(8).putLong(file.lastModified()).array();
                byte[] pathLength = ByteBuffer.allocate(4).putInt(pathBytes.length).array();

                buf.write(pathLength);
                buf.write(pathBytes);
                buf.write(hashBytes);
                buf.write(lastModified);
            }

            return FileUtils.writeToFile(indexPath, buf.toByteArray());
        } catch (IOException e) {
            return false;
        }
    }
}
