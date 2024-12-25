package fileobjects;

import java.nio.file.Path;

public class objUtils {
    public static Path getShaPath(Path objPath, String sha) {
        return objPath.resolve(sha.substring(0, 2)).resolve(sha.substring(2));
    }

    public static boolean saveObj(Path objPath, baseObj obj) {
        Path target = getShaPath(objPath, obj.getString()); // todo make this
        return fileUtils.writeToFile(target, obj.getString());
    }
}
