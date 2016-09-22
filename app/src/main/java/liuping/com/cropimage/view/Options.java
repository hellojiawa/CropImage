package liuping.com.cropimage.view;

import android.content.Context;

import liuping.com.cropimage.base.App;


public class Options {
    //最小宽高
    public int min_height = dp2px(App.getInstance(), 20);
    public int min_width = dp2px(App.getInstance(), 20);
    //默认宽高
    public int cilpHeight = dp2px(App.getInstance(), 100);
    //图片点击范围
    public int iconClick = 20;

    /**
     * dp2px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
