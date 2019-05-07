package utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassFinder {

    private static final char PKG_SEPARATOR = '.';
    private static final String START_DIRECTORY = "src/main/java/";

    public List<String> find(String packageName) {
        String directory = START_DIRECTORY+packageName;
        List<String> classList = new ArrayList<>();
        File[] files = new File(directory).listFiles();
        Arrays.stream(files)
                .forEach(file -> {
                    String className = file.getName().split("\\.")[0];
                    classList.add(packageName+PKG_SEPARATOR+className);
                });
        return classList;
    }
}
