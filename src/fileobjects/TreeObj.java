package fileobjects;

import hashing.Sha256;
import logging.Logger;

import java.nio.file.Path;
import java.util.Set;

public class TreeObj implements BaseObj {
    private final Set<BaseObj> children;
    private final String name;

    public TreeObj(String name, Set<BaseObj> children) {
        assert name != null && children != null :
                "Cannot create tree with null values, provide empty name and children instead";
        this.name = name;
        this.children = children;
    }

    public void addChild(BaseObj child) { this.children.add(child); }

    public BaseObj getChild(Sha256 sha) {
        for (BaseObj child : this.children) {
            if (sha.equals(child.getHash())) {
                return child;
            }
        }
        return null;
    }

    public String getObjContent() {
        StringBuilder sb = new StringBuilder();
        for (BaseObj child : this.children) {
            sb.append("000000 "); // reserved for future use
            sb.append(child.getObjectType());
            sb.append(" ");
            sb.append(child.getHash());
            sb.append("\t");
            sb.append(child.getName());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getName() { return this.name; }

    public String getObjectType() {
        return "tree";
    }

    public boolean save(Path objPath) {
        for (BaseObj child : this.children) {
            if (!child.save(objPath)) {
                Logger.error("Failed to save child " + child.getName());
                return false;
            }
        }
        return this.saveRaw(objPath);
    }
}
