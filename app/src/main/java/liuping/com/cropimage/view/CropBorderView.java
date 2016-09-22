package liuping.com.cropimage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import liuping.com.cropimage.R;


/**
 * TODO
 * version: V1.0 <剪切板>
 * author: liuping
 */
public class CropBorderView extends View {

    private int borderColor = Color.parseColor("#FFFFFF");
    private int outSideColor = Color.parseColor("#50000000");

    private float borderWidth = 2;
    private float lineWidth = 1;

    private Rect[] rects = new Rect[2];

    private Paint cutPaint;
    private Paint outSidePaint;

    private RectF cilpRectF;

    private Bitmap bitmap;
    private int iconOffset;

    private int width = 0;
    private int height = 0;

    private Options options;

    private static final int TOP_ICON_ACTION = 1;
    private static final int BOTTOM_ICON_ACTION = 2;
    private static final int ALL_ACTION = 3;

    private int action = -1;
    private float actionX;
    private float actionY;
    private float mRecWidth;
    private float mRecHeight;

    public CropBorderView(Context context) {
        super(context);
        initView();
    }

    public CropBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CropBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        cutPaint = new Paint();
        cutPaint.setColor(borderColor);
        cutPaint.setStrokeWidth(borderWidth);
        cutPaint.setStyle(Paint.Style.STROKE);

        outSidePaint = new Paint();
        outSidePaint.setAntiAlias(true);
        outSidePaint.setColor(outSideColor);
        outSidePaint.setStyle(Paint.Style.FILL);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point);
        iconOffset = bitmap.getWidth() / 2;

        options = new Options();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cilpRectF == null) {
            cilpRectF = new RectF((getWidth() - options.cilpHeight) / 2, (getHeight() - options.cilpHeight) / 2, (getWidth() + options.cilpHeight) / 2, (getHeight() + options.cilpHeight) / 2);
        }
        if (width == 0)
            width = getWidth();
        if (height == 0)
            height = getHeight();
        canvas.save();

        drawLine(canvas);
        drawRound(canvas);
        drawIcon(canvas);

        canvas.restore();

        super.onDraw(canvas);
    }

    private void drawIcon(Canvas canvas) {
        canvas.drawBitmap(bitmap, cilpRectF.left - iconOffset, cilpRectF.top - iconOffset, null);
        canvas.drawBitmap(bitmap, cilpRectF.right - iconOffset, cilpRectF.bottom - iconOffset, null);

        Rect rect = new Rect((int) (cilpRectF.left - iconOffset) - options.iconClick, (int) (cilpRectF.top - iconOffset) - options.iconClick,
                (int) (cilpRectF.left + iconOffset) + options.iconClick, (int) (cilpRectF.top + iconOffset) + options.iconClick);
        rects[0] = rect;

        rect = new Rect((int) (cilpRectF.right - iconOffset) - options.iconClick, (int) (cilpRectF.bottom - iconOffset) - options.iconClick,
                (int) (cilpRectF.right + iconOffset) + options.iconClick, (int) (cilpRectF.bottom + iconOffset) + options.iconClick);
        rects[1] = rect;
    }

    private void drawLine(Canvas canvas) {
        cutPaint.setStrokeWidth(lineWidth);
        //画水平线
        float p = cilpRectF.top + cilpRectF.height() / 3;
        canvas.drawLine(cilpRectF.left, p, cilpRectF.right, p, cutPaint);
        p = cilpRectF.top + cilpRectF.height() * 2 / 3;
        canvas.drawLine(cilpRectF.left, p, cilpRectF.right, p, cutPaint);

        //画垂直线
        p = cilpRectF.left + cilpRectF.width() / 3;
        canvas.drawLine(p, cilpRectF.top, p, cilpRectF.bottom, cutPaint);
        p = cilpRectF.left + cilpRectF.width() * 2 / 3;
        canvas.drawLine(p, cilpRectF.top, p, cilpRectF.bottom, cutPaint);
    }

    private void drawRound(Canvas canvas) {
        cutPaint.setStrokeWidth(borderWidth);
        canvas.drawRect(cilpRectF, cutPaint);

        //画暗色背景
        canvas.drawRect(0, 0, cilpRectF.left, height, outSidePaint);
        canvas.drawRect(cilpRectF.right, 0, width, height, outSidePaint);
        canvas.drawRect(cilpRectF.left, 0, cilpRectF.right, cilpRectF.top, outSidePaint);
        canvas.drawRect(cilpRectF.left, cilpRectF.bottom, cilpRectF.right, height, outSidePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (cilpRectF.contains((int) event.getX(), (int) event.getY())) {
                    action = ALL_ACTION;
                    mRecWidth = cilpRectF.width();
                    mRecHeight = cilpRectF.height();
                }
                if (rects[0].contains((int) event.getX(), (int) event.getY())) {
                    action = TOP_ICON_ACTION;
                }
                if (rects[1].contains((int) event.getX(), (int) event.getY())) {
                    action = BOTTOM_ICON_ACTION;
                }
                actionY = event.getY();
                actionX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = actionY - event.getY();
                float x = actionX - event.getX();
                switch (action) {
                    case TOP_ICON_ACTION:
                        cilpRectF.top = cilpRectF.top - y;
                        cilpRectF.left = cilpRectF.left - x;

                        break;
                    case BOTTOM_ICON_ACTION:
                        cilpRectF.bottom = cilpRectF.bottom - y;
                        cilpRectF.right = cilpRectF.right - x;
                        break;
                    case ALL_ACTION:
                        if (cilpRectF.top > iconOffset) {
                            cilpRectF.bottom -= y;

                        }

                        if (cilpRectF.bottom < height - iconOffset) {
                            cilpRectF.top -= y;
                        }

                        if (cilpRectF.left > iconOffset) {
                            cilpRectF.right -= x;
                        }

                        if (cilpRectF.right < width - iconOffset) {
                            cilpRectF.left -= x;
                        }

                        break;
                }
                checkBroad();
                actionY = event.getY();
                actionX = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                action = -1;
                break;
        }
        return true;
    }

    /**
     * 检查边界
     */
    private void checkBroad() {

        if ((cilpRectF.bottom - cilpRectF.top) < options.min_height) {//高度小于最小高度
            switch (action) {
                case TOP_ICON_ACTION:
                    cilpRectF.top = cilpRectF.bottom - options.min_height;
                    break;
                case BOTTOM_ICON_ACTION:
                    cilpRectF.bottom = cilpRectF.top + options.min_height;
                    break;
            }
        }

        if ((cilpRectF.right - cilpRectF.left) < options.min_width) {//宽度小于最小宽度
            switch (action) {
                case TOP_ICON_ACTION:
                    cilpRectF.left = cilpRectF.right - options.min_width;
                    break;
                case BOTTOM_ICON_ACTION:
                    cilpRectF.right = cilpRectF.left + options.min_width;
                    break;
            }
        }

        //与屏幕的边界
        if (cilpRectF.top < iconOffset) {
            cilpRectF.top = iconOffset;
            if (action == ALL_ACTION) {
                cilpRectF.bottom = cilpRectF.top + mRecHeight;
            }
        }

        if (cilpRectF.bottom > height - iconOffset) {
            cilpRectF.bottom = height - iconOffset;
            if (action == ALL_ACTION) {
                cilpRectF.top = cilpRectF.bottom - mRecHeight;
            }
        }

        if (cilpRectF.left < iconOffset) {
            cilpRectF.left = iconOffset;
            if (action == ALL_ACTION) {
                cilpRectF.right = cilpRectF.left + mRecWidth;
            }
        }

        if (cilpRectF.right > width - iconOffset) {
            cilpRectF.right = width - iconOffset;
            if (action == ALL_ACTION) {
                cilpRectF.left = cilpRectF.right - mRecWidth;
            }
        }

    }

    public RectF getCilpRectF() {
        return cilpRectF;
    }
}
