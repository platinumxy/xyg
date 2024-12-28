package fileobjects;

import java.nio.file.Path;

public class BlobObj implements BaseObj {
    private final String name;
    private final String content;

    public BlobObj(String name, String content) {
        assert name != null && content != null : "Cannot create blob with null values";
        assert !name.isEmpty() : "Cannot create blob without a  name";
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
