package commands.init;

public enum InitResult {
    SUCCESS,
    INVALID_PERMISSIONS,
    PATH_NOT_FOUND,
    PROJECT_EXISTS,
    INVALID_PATH,
    COULD_NOT_CREATE_STAGING_AREA,
    UNKNOWN_ERROR
}
