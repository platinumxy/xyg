import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Logger.dbg(InitProject.init(Path.of("/home/archi/proj/tmp")).toString());
    }
}