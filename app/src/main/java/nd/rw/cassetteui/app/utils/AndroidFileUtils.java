package nd.rw.cassetteui.app.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;

public class AndroidFileUtils {

    private Context context;
    private AssetManager assetManager;
    private String externalStoragePath;
    private String internalStoragePath;

    public AndroidFileUtils(Context context) {
        this.context = context;
        this.assetManager = context.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    public boolean fileExists(String path){
        return new File(path).exists();
    }

    public boolean deleteFile(String path){
        return new File(path).delete();
    }
}
