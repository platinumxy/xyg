package fileobjects;

import logging.Logger;

import java.nio.file.Path;

public class CommitObj implements BaseObj {
    private final TreeObj tree;
    private final String[] parentSha;
    private final String author;
    private final String commiter;
    private final String message;
    private final long unixTimestamp;

    public CommitObj(TreeObj tree, String parentSha, String author, String message) {
        this(tree, new String[]{parentSha}, author, author,  message);
    }
    public CommitObj(TreeObj tree, String[] parentSha, String author, String message) {
        this(tree, parentSha, author, author,  message);
    }
    public CommitObj(TreeObj tree, String parentSha, String author, String commiter, String message) {
        this(tree, new String[]{parentSha}, author, commiter, message);
    }
    public CommitObj(TreeObj tree, String[] parentSha, String author, String commiter, String message) {
        assert tree != null && parentSha != null && author != null && commiter != null && message != null :
                "Cannot create commit with null values";
        assert !(author.isEmpty() && commiter.isEmpty()) : "Cannot create commit without an author or commiter";
        assert !message.isEmpty() : "Cannot create commit without a message";
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
        if (!this.tree.save(objPath)) {
            Logger.error("Failed to save tree object");
            return false;
        }
        return this.saveRaw(objPath);
    }
}
