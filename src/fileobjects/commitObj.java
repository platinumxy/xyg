package fileobjects;

import java.nio.file.Path;

public class commitObj implements baseObj {
    private final treeObj tree;
    private final String[] parentSha;
    private final String author;
    private final String commiter;
    private final String message;
    private final long unixTimestamp;

    public commitObj(treeObj tree, String parentSha, String author, String message) {
        this(tree, new String[]{parentSha}, author, author,  message);
    }
    public commitObj(treeObj tree, String[] parentSha, String author, String message) {
        this(tree, parentSha, author, author,  message);
    }
    public commitObj(treeObj tree, String parentSha, String author, String commiter, String message) {
        this(tree, new String[]{parentSha}, author, commiter, message);
    }
    public commitObj(treeObj tree, String[] parentSha, String author, String commiter, String message) {
        this.tree = tree;
        this.parentSha = parentSha;
        this.author = author;
        this.commiter = commiter;
        this.message = message;
        this.unixTimestamp = System.currentTimeMillis() / 1000L;
    }


    public String getName() { return "commit"; }

    public String getObjContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("tree ");
        sb.append(this.tree.getHash());
        sb.append("\n");
        for (String parent : this.parentSha) {
            sb.append("parent ");
            sb.append(parent);
            sb.append("\n");
        }
        sb.append("author ");
        sb.append(this.author);
        sb.append(" ");
        sb.append(this.unixTimestamp);
        sb.append("\n");
        sb.append("committer ");
        sb.append(this.commiter);
        sb.append(" ");
        sb.append(this.unixTimestamp);
        sb.append("\n");
        sb.append("\n");
        sb.append(this.message);
        return sb.toString();
    }

    public String getObjectType() {
        return "commit";
    }

    public boolean save(Path objPath) {
        return this.tree.save(objPath) && this.saveRaw(objPath);
    }
}
