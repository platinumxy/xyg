package fileobjects;

import java.nio.file.Path;

public class blobObj implements baseObj {
    private final String name;
    private final String content;

    public blobObj(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getObjContent() {
        return this.content;
    }

    public String getName() { return this.name; }

    public String getObjectType() {
        return "blob";
    }

    public boolean save(Path objPath) {
        return this.saveRaw(objPath);
    }
}
