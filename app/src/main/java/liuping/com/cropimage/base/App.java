package liuping.com.cropimage.base;

import android.app.Application;
import android.content.Context;

/**
 * TODO
 * version: V1.0 <描述当前版本功能>
 * fileName: liuping.com.cropimage.base.App
 * author: liuping
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = App.this;
        super.onCreate();
    }

    public static Context getInstance(){
        return mContext;
    }
}
