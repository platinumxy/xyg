package commands.stagingmanager;

import fileobjects.BlobObj;
import hashing.Sha256;

import java.nio.file.Path;
import java.util.Arrays;

import static fileobjects.FileUtils.createBlob;

/**
 * To represent a file changes that have been staged, To represent deleted files, the hash will be null
 */
public record StagedFile(Path path, Sha256 hash, long lastModified) {
    public boolean fileChanged() {
        if (this.hash == null) {
            return this.path.toFile().exists();
        } else if (!this.path.toFile().exists() || this.path.toFile().lastModified() != this.lastModified) {
            return true;
        } else {
            BlobObj blob = createBlob(path);
            if (blob == null) {
                return true;
            }
            return !Arrays.equals(this.hash.getHash(), blob.getHash().getHash());
        }
    }

}
