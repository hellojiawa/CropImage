package liuping.com.cropimage.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import liuping.com.cropimage.R;
import liuping.com.cropimage.view.CropBorderView;
import liuping.com.cropimage.view.CropImageView;

public class MainActivity extends Activity  implements View.OnClickListener{

    private Button mBtn_cancel;
    private Button mBtn_done;
    private CropImageView mCropImageView;
    private CropBorderView mBorderView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBtn_done = (Button) findViewById(R.id.btn_done);
        mBtn_cancel = (Button) findViewById(R.id.btn_cancel);
        mBtn_done.setOnClickListener(this);
        mBtn_cancel.setOnClickListener(this);

        mCropImageView = (CropImageView) findViewById(R.id.iv_crop);
        mBorderView = (CropBorderView) findViewById(R.id.iv_border);
        mImageView = (ImageView) findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //重新选择
            case R.id.btn_cancel:
                mCropImageView.setVisibility(View.VISIBLE);
                mBorderView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                break;

            //裁剪图片
            case R.id.btn_done:
                mCropImageView.setVisibility(View.GONE);
                mBorderView.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);

                RectF cilpRectF = mBorderView.getCilpRectF();
                Bitmap bitmap = mCropImageView.clip(cilpRectF);
                mImageView.setImageBitmap(bitmap);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins((int) cilpRectF.left,(int)cilpRectF.top,0,0);
                mImageView.setLayoutParams(layoutParams);
                break;
        }
    }
}
