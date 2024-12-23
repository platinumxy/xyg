import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class InitProject {
    public static InitResult check(Path path) {
        Optional<InitResult> result = checkPath(path);
        if (result.isPresent()) {
            return result.get();
        }



        return InitResult.SUCCESS;
    }
    private static Optional<InitResult> checkPath(Path path) {
        assert path != null : "Path shouldn't be null";
        File f = path.toFile();

        if (!f.exists()) {
            return Optional.of(InitResult.PATH_NOT_FOUND);
        }
        if (!(f.canWrite() && f.canRead())) {
            return Optional.of(InitResult.INVALID_PERMISSIONS);
        }
        if (!f.isDirectory()){
            return Optional.of(InitResult.INVALID_PATH);
        }
        return Optional.empty();
    }
}
