import java.io.Console;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class InitProject {
    public static InitResult init(Path path) {
        Optional<InitResult> result = checkPath(path);
        if (result.isPresent()) {
            return result.get();
        }
        return createProject(path);
    }

    private static Optional<InitResult> checkPath(Path path) {
        assert path != null : "Path shouldn't be null";
        File f = path.toFile();

        // these checks are preformed first because
        // exists will return false on IOErrors hence
        // to avoid returning the wrong error code
        // we check for permissions first
        if (!(f.canWrite() && f.canRead())) {
            return Optional.of(InitResult.INVALID_PERMISSIONS);
        }

        if (!f.exists()) {
            return Optional.of(InitResult.PATH_NOT_FOUND);
        }

        if (!f.isDirectory()){
            return Optional.of(InitResult.INVALID_PATH);
        }
        return Optional.empty();
    }

    private static InitResult createProject(Path path) {
        Path projPath = path.resolve(".xyg");
        File projDir = projPath.toFile();
        if (projDir.exists()) { return InitResult.PROJECT_EXISTS; }
        if (!projDir.mkdir()) { return InitResult.INVALID_PERMISSIONS; }

        Logger.todo("Project created at " + projPath);

        if (projectSetupCorrectly(projPath)) {
            return InitResult.SUCCESS;
        }
        return InitResult.UNKNOWN_ERROR;
    }

    private static boolean projectSetupCorrectly(Path proj_path) {
        Logger.todo("Project setup correctly");
        return true;
    }

}
