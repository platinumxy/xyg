package fileobjects;

import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Logger;

public class treeObj implements baseObj {
    private final Set<baseObj> children;
    private final String name;

    public treeObj(String name, Set<baseObj> children) {
        this.name = name;
        this.children = children;
    }

    public void addChild(baseObj child) {
        this.children.add(child);
    }

    public baseObj getChild(String sha256) {
        for (baseObj child : this.children) {
            if (child.getString().equals(sha256)) {
                return child;
            }
        }
        return null;
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (baseObj child : this.children) {
            sb.append(child.getObjectType());
            sb.append(" ");
            sb.append(child.getName());
            sb.append(" ");
            sb.append(child.getString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getObjectString() {
        return "tree:" + this.name;
    }

    public String getName() {
        return this.name;
    }

    public String getObjectType() {
        return "tree";
    }

    public boolean save(Path objPath) {
        for (baseObj child : this.children) {
            if (!child.save(objPath)) {
                Logger.getGlobal().severe("Failed to save child " + child.getName());
                return false;
            }
        }
        return objUtils.saveObj(objUtils.getShaPath(objPath, this.getObjectString()), this);
    }
}
