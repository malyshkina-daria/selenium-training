package utils;

import java.io.File;
import java.util.Random;

public class FileUpload {
    public static File getFolder() {
        return new File(PropertyLoader.loadProperty("file.source"));
    }

    public static String[] getFilesWithFormatFromFolder(final String formatFile, File folder) {
        return folder.list((directory, fileName) -> fileName.endsWith(formatFile));
    }

    private static File[] getFilesFromFolder(File folder) {
        return folder.listFiles();
    }

    public static File getRandomFileFromFolder(File folder) {
        File[] files = getFilesFromFolder(folder);
        return files != null ? files[new Random().nextInt(files.length)] : null;
    }
}
