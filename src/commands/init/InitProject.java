package commands.init;

import commands.stagingmanager.StagingEnvironment;
import logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Optional;

public class InitProject {
    public static InitResult init(Path path) {
        return checkPath(path).orElseGet(() -> createProject(path));
    }

    private static Optional<InitResult> checkPath(Path path) {
        assert path != null : "Path shouldn't be null";
        File f = path.toFile();

        if (!f.exists()) {
            return Optional.of(InitResult.PATH_NOT_FOUND);
        } else if (!(f.canWrite() && f.canRead())) { // check to avoid IOErrors
            return Optional.of(InitResult.INVALID_PERMISSIONS);
        } else if (!f.isDirectory()){
            return Optional.of(InitResult.INVALID_PATH);
        }
        return Optional.empty();
    }

    private static InitResult createProject(Path path) {
        Path projPath = path.resolve(".xyg");
        File projDir = projPath.toFile();
        if (projDir.exists()) {
            return InitResult.PROJECT_EXISTS;
        } else if (!projDir.mkdir()) {
            return InitResult.INVALID_PERMISSIONS;
        } else if (!createDirectories(projPath) || !createHeadFile(projPath) || !projectSetupCorrectly(projPath)) {
            return InitResult.UNKNOWN_ERROR;
        } else if (!createStagingArea(projPath)) {
            return InitResult.COULD_NOT_CREATE_STAGING_AREA;
        }
        return InitResult.SUCCESS;
    }

    private static boolean createDirectories(Path projPath) {
        File refsFile = projPath.resolve("refs").toFile();
        File refsHeadsFile = projPath.resolve("refs").resolve("heads").toFile();
        File objsFile = projPath.resolve("objects").toFile();
        return refsFile.mkdir() && refsHeadsFile.mkdir() && objsFile.mkdir();
    }

    private static boolean createHeadFile(Path projPath) {
        Path HeadPath = projPath.resolve("HEAD");
        try {
            if (HeadPath.toFile().createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(HeadPath.toString()));
                writer.write("refs/heads/main");
                writer.close();
                return true;
            }
        } catch (Exception _e) { /*...*/ }
        return false;
    }

    private static boolean createStagingArea(Path projPath) {
        Path stagePath = projPath.resolve("index");
        StagingEnvironment stage = new StagingEnvironment();
        return stage.saveIndex(stagePath);
    }

    private static boolean projectSetupCorrectly(Path proj_path) {
        Logger.todo("Project setup correctly");
        return true;
    }

}
