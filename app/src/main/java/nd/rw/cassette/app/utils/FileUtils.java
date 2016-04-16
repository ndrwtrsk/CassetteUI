package nd.rw.cassette.app.utils;

import java.io.File;

public class FileUtils {

    public static boolean fileExists(String path){
        return new File(path).exists();
    }

    public static boolean deleteFile(String path){
        return new File(path).delete();
    }
}
