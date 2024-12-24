public class Logger {
    public static void dbg(String msg) {
        log('D', msg, Colour.PURPLE);
    }
    public static void info(String msg) {
        log('I', msg, Colour.BLUE);
    }

    public static void success(String msg) {
        log('S', msg, Colour.GREEN);
    }

    public static void warn(String msg) {
        log('W', msg, Colour.ORANGE);
    }
    public static void error(String msg) {
        log('E', msg, Colour.RED);
    }
    public static void todo(String msg) {
        log('T', msg, Colour.ORANGE);
    }

    private static void log(char sym, String msg, Colour c){
        System.out.println(c.toAnsiCode() + "[" + sym + "] " + msg + Colour.RESET.toAnsiCode());
    }

    public static void test_logs() {
        dbg("Debug message");
        info("Info message");
        success("Success message");
        warn("Warning message");
        error("Error message");
        todo("Todo message");
        System.out.println("Colourless message");
    }
}
