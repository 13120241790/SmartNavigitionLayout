package com.julive.library.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.julive.library.navigation.bimap.BitmapManager;
import com.julive.library.navigation.bimap.ResultBitmapListener;
import com.julive.library.navigation.dragview.DraggableFlagView;
import com.julive.library.navigation.model.TabModel;


public class TabItemView extends LinearLayout implements Observer {

    protected TextView mTabTextView;

    protected ImageView mImageView;

    private DraggableFlagView mRedPointView;

    private int mColorNormal;

    private int mColorSelected;

    private Bitmap bitmapNormal;

    private Bitmap bitmapSelected;

    private int mDrawableNormal;

    private int mDrawableSelected;

    private Context mContext;

    private RemindType mCurrentRemindType = RemindType.REMIND_NORMAL;

    public TabItemView(Context context) {
        super(context);
        initView(context, null);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        initSubView(context);
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
        setSelected(index == (int) getTag());
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
                        BitmapManager.getInstance().httpToBitMap(imageNormalString, new ResultBitmapListener() {
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
                        BitmapManager.getInstance().httpToBitMap(imageSelectedString, new ResultBitmapListener() {
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
            setRedPointVisibility(isVisibility);
            if (mCurrentRemindType == RemindType.REMIND_TEXT) {
                mRedPointView.setText(String.valueOf(count));
            }
        }
    }

    @Override
    public void pointStyleConfig(int index, int leftOffset, int topOffset, RemindType remindType) {
        if (index == (int) getTag()) {
            mCurrentRemindType = remindType;
            setRedPointViewOffset(leftOffset, topOffset, remindType);
        }
    }

    public void setTextColor(@ColorInt int colorNormal, @ColorInt int colorSelected) {
        mColorNormal = colorNormal;
        mColorSelected = colorSelected;
        mTabTextView.setTextColor(isSelected() ? colorSelected : colorNormal);
    }

    private void initSubView(Context context) {
        mContext = context;
        inflate(context, providerItemLayout(), this);
        mImageView = findViewById(R.id.iv_tab_indicator);
        mTabTextView = findViewById(R.id.tv_tab_indicator);
        mRedPointView = findViewById(R.id.v_red_point);
        int TAB_LEFT_OFFSET = 0;
        int TAB_TOP_OFFSET = 0;
        boolean IS_DRAGGABLE = false;
        try {
            TAB_LEFT_OFFSET = getResources().getInteger(getResources().getIdentifier("tab_left_offset", "integer", getContext().getPackageName()));
            TAB_TOP_OFFSET = getResources().getInteger(getResources().getIdentifier("tab_top_offset", "integer", getContext().getPackageName()));
            IS_DRAGGABLE = getResources().getBoolean(getResources().getIdentifier("draggable", "bool", getContext().getPackageName()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        setRedPointViewOffset(TAB_LEFT_OFFSET, TAB_TOP_OFFSET, RemindType.REMIND_NORMAL);
        mRedPointView.setDraggable(IS_DRAGGABLE);
    }

    private void setRedPointViewOffset(int leftOffset, int topOffset, RemindType remindType) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRedPointView.getLayoutParams();
        if (remindType == RemindType.REMIND_NORMAL) {
            mRedPointView.setText(null);
            layoutParams.width = (int) getResources().getDimension(R.dimen.dimen_size_8);
            layoutParams.height = (int) getResources().getDimension(R.dimen.dimen_size_8);
        } else {
            layoutParams.width = (int) getResources().getDimension(R.dimen.dimen_size_16);
            layoutParams.height = (int) getResources().getDimension(R.dimen.dimen_size_16);
        }
        layoutParams.setMargins(leftOffset, topOffset, layoutParams.rightMargin, layoutParams.bottomMargin);
        mRedPointView.setLayoutParams(layoutParams);
    }
}
