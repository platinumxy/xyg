package fileobjects;

import java.nio.file.Path;

public interface baseObj {
    public String getName();
    public String getString();
    public String getObjectType();
    public boolean save(Path objPath);
}
