package logging;

public class Logger {
    public static void dbg(String msg) {
        formatedLog('D', msg, Colour.PURPLE);
    }
    public static void info(String msg) {
        formatedLog('I', msg, Colour.BLUE);
    }

    public static void success(String msg) {
        formatedLog('S', msg, Colour.GREEN);
    }

    public static void warn(String msg) {
        formatedLog('W', msg, Colour.ORANGE);
    }
    public static void error(String msg) {
        formatedLog('E', msg, Colour.RED);
    }
    public static void todo(String msg) {
        formatedLog('T', msg, Colour.ORANGE);
    }

    public static void formatedLog(char sym, String msg, Colour c){
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
