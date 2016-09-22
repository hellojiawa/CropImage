package liuping.com.cropimage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * TODO
 * version: V1.0 <背景>
 * author: liuping
 */
public class CropImageView extends ImageView {
    public CropImageView(Context context) {
        super(context);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Bitmap clip(RectF rectF) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        bitmap = Bitmap.createBitmap(bitmap, (int) rectF.left,
                (int) rectF.top, (int) rectF.width(), (int) rectF.height());
        return bitmap;
    }

}
