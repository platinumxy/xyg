package fileobjects;

import hashing.Sha256;

import java.nio.file.Path;

import static fileobjects.FileUtils.getShaPath;

public interface BaseObj {
    String getName();
    String getObjContent();
    String getObjectType();

    default String generateObjectString() {
        String type = this.getObjectType();
        String content = this.getObjContent();
        String length = Integer.toString(content.length());
        return type + " " + length + "\0" + content;
    }
    default Sha256 getHash() {
        return new Sha256(this.generateObjectString());
    }

    /**
     * Save the object to the object and it's children to the object store.
     */
    boolean save(Path objPath);

    /**
     * Save the object to the object store without care for its children. Should be called with caution.
     */
    default boolean saveRaw(Path objPath) {
        return FileUtils.writeToFile(
                getShaPath(objPath, this.getHash()),
                this.generateObjectString());
    }
}
