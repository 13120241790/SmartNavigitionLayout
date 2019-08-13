package com.julive.library.smartnavigitionlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.julive.library.smartnavigitionlayout.model.TabModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TabItemView extends LinearLayout implements Observer {

    protected TextView mTabTextView;

    protected ImageView mImageView;

    private View mRedPointView;

    private int mColorNormal;

    private int mColorSelected;

    private Bitmap bitmapNormal;

    private Bitmap bitmapSelected;

    private int mDrawableNormal;

    private int mDrawableSelected;

    private Context mContext;


    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate(context, providerItemLayout(), this);
        mImageView = findViewById(R.id.iv_tab_indicator);
        mTabTextView = findViewById(R.id.tv_tab_indicator);
        mRedPointView = findViewById(R.id.v_red_point);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabItemView);
        Drawable drawable = typedArray.getDrawable(R.styleable.TabItemView_tab_image_src);
        if (drawable != null) {
            mImageView.setImageDrawable(drawable);
        }
        CharSequence text = typedArray.getText(R.styleable.TabItemView_tab_text_string);
        if (!TextUtils.isEmpty(text)) {
            mTabTextView.setText(text);
        }
        typedArray.recycle();
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mTabTextView.setTextColor(selected ? mColorSelected : mColorNormal);
        if (bitmapNormal != null && bitmapSelected != null) {
            mImageView.setImageBitmap(isSelected() ? bitmapSelected : bitmapNormal);
        } else if (mDrawableNormal != 0 && mDrawableSelected != 0) {
            mImageView.setImageDrawable(isSelected() ? getResources().getDrawable(mDrawableSelected) : getResources().getDrawable(mDrawableNormal));
        }
    }

    @Override
    public void update(int index) {
        if (index == (int) getTag()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    @Override
    public void updateItemStyle(TabModel tabModel) {
        if (tabModel.getIndex() == (int) getTag()) {

            if (!TextUtils.isEmpty(tabModel.getText())) {
                mTabTextView.setText(tabModel.getText());
            }

            if (tabModel.getTextColorNormal() != 0 && tabModel.getTextColorSelected() != 0) {
                setTextColor(tabModel.getTextColorNormal(), tabModel.getTextColorSelected());
            }

            if (tabModel.getImageNormal() != null && tabModel.getImageSelected() != null) {
                if (tabModel.getImageNormal() instanceof String && !TextUtils.isEmpty((String) tabModel.getImageNormal())) {
                    String imageNormalString = (String) tabModel.getImageNormal();
                    String imageSelectedString = (String) tabModel.getImageSelected();
                    if (imageNormalString.startsWith("http") || imageNormalString.startsWith("HTTP")) {
                        httpBitMap(imageNormalString, new ResultBitmapListener() {
                            @Override
                            public void resultBitmap(final Bitmap bitmap) {
                                bitmapNormal = bitmap;
                                if (!isSelected()) {
                                    new Handler(mContext.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mImageView.setImageBitmap(bitmap);
                                        }
                                    });

                                }

                            }
                        });
                        httpBitMap(imageSelectedString, new ResultBitmapListener() {
                            @Override
                            public void resultBitmap(final Bitmap bitmap) {
                                bitmapSelected = bitmap;
                                if (isSelected()) {
                                    new Handler(mContext.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mImageView.setImageBitmap(bitmap);
                                        }
                                    });
                                }
                            }
                        });

                    }
                } else if (tabModel.getImageNormal() instanceof Integer && tabModel.getImageSelected() instanceof Integer) {
                    bitmapSelected = null;
                    bitmapNormal = null;
                    mDrawableNormal = (int) tabModel.getImageNormal();
                    mDrawableSelected = (int) tabModel.getImageSelected();
                    mImageView.setImageDrawable(isSelected() ? getResources().getDrawable(mDrawableSelected) : getResources().getDrawable(mDrawableNormal));
                }
            }
        }
    }

    public void setIndex(int index) {
//        this.index = index;
    }

    /**
     * @return TabItemView 的布局
     */
    protected int providerItemLayout() {
        return R.layout.tab_item_view;
    }

    /**
     * 设置小红点是否展示
     *
     * @param visibility 是否展示
     */
    public void setRedPointVisibility(boolean visibility) {
        mRedPointView.setVisibility(visibility ? VISIBLE : GONE);
    }

    @Override
    public void pointVisibility(int index, boolean isVisibility, int count) {
        if (index == (int) getTag()) {
            if (isVisibility) {
            }
            setRedPointVisibility(isVisibility);
        }
    }

    public void setTextColor(@ColorInt int colorNormal, @ColorInt int colorSelected) {
        mColorNormal = colorNormal;
        mColorSelected = colorSelected;
        mTabTextView.setTextColor(isSelected() ? colorSelected : colorNormal);
    }


    public void httpBitMap(final String url, final ResultBitmapListener resultBitmapListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageUrl = null;

                try {
                    imageUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    if (bitmap != null) {
                        resultBitmapListener.resultBitmap(bitmap);
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public interface ResultBitmapListener {
        void resultBitmap(Bitmap bitmap);
    }
}
