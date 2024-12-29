package commands;

import commands.init.InitProject;
import logging.Logger;

import java.nio.file.Path;

public class CommandManager {
    public static int executeCommand(Path cwd, Command command, String[] args) {
        assert  cwd != null && command != null && args != null : "Cannot execute command with null values";
        return switch (command) {
            case ADD, COMMIT -> {
                Logger.error("Not implemented yet");
                yield 1;
            }
            case INIT -> init(cwd, args);
        };
    }

    private static int init(Path cwd, String[] args) {
        if (args.length != 0) {
            Logger.error("Invalid number of arguments for init");
            return 5;
        }
        return switch (InitProject.init(cwd)) {
            case SUCCESS -> {
                Logger.info("Initialized empty repository in " + cwd);
                yield 0;
            }
            case INVALID_PATH, PATH_NOT_FOUND -> {
                Logger.error("Error initializing repository: Invalid path, " + cwd);
                yield 1;
            }
            case INVALID_PERMISSIONS -> {
                Logger.error("Error initializing repository: Missing read or write privileges permissions");
                yield 2;
            }
            case PROJECT_EXISTS -> {
                Logger.warn(" xyg project already exits at " + cwd);
                yield 3;
            }
            case COULD_NOT_CREATE_STAGING_AREA -> {
                Logger.error("Error initializing repository: Could not create staging area");
                yield 4;
            }
            case UNKNOWN_ERROR -> {
                Logger.error("An unknown error occurred while initializing repository");
                yield 5;
            }
        };
    }
}
