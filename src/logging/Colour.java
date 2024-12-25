package logging;

public enum Colour {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    ORANGE("\u001B[33m"),
    BLACK("\u001B[30m"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m");

    private final String ansiCode;

    Colour(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    public String toAnsiCode() {
        return ansiCode;
    }
}