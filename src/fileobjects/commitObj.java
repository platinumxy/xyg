package fileobjects;

import jdk.jshell.spi.ExecutionControl;

import java.nio.file.Path;

public class commitObj implements baseObj {
    private final treeObj tree;
    private final String parentSha;
    private final String author;
    private final String message;
    public commitObj(treeObj tree, String parentSha, String author, String message) {

        this.tree = tree;
        this.parentSha = parentSha;
        this.author = author;
        this.message = message;
    }


    public String getName() { return "commit"; }

    public String getString() {
        return "";
    }

    public String getObjectType() {
        return "";
    }

    public boolean save(Path objPath) {
        return false;
    }
}
