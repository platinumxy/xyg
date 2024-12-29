import commands.init.InitProject;
import commands.stagingmanager.StagedFile;
import commands.stagingmanager.StagingEnvironment;

import java.nio.file.Path;

public class Main {
    private static final Path TestingEnv = Path.of("/home/archi/proj/xyg/testingEnv");
    private static final Path xygTestingDir = TestingEnv.resolve(".xyg");

    public static void main(String[] args) {
        InitProject.init(TestingEnv);
        StagingEnvironment se = new StagingEnvironment(xygTestingDir.resolve("index"));
        se.addToIndex(new StagedFile(TestingEnv.resolve("file4"), null, 0));
        for (StagedFile sf : se.getStagedFiles()) {
            System.out.println(sf);
            System.out.println(sf.fileChanged());
        }
        se.saveIndex(xygTestingDir.resolve("index"));
    }
}