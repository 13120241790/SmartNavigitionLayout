package com.julive.library.smartnavigitionlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.julive.library.smartnavigitionlayout.model.TabModel;

public class TabItemView extends LinearLayout implements Observer {

    protected TextView mTabTextView;

    protected ImageView mImageView;

    private View mRedPointView;

    private int index;


    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, providerItemLayout(), this);
        mImageView = findViewById(R.id.iv_tab_indicator);
        mTabTextView = findViewById(R.id.tv_tab_indicator);
        mRedPointView = findViewById(R.id.v_red_point);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mTabTextView.setTextColor(selected ? getResources().getColor(R.color.color_4a90e2) : getResources().getColor(R.color.colorText));
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
            if (tabModel.getImageNormal() != null && tabModel.getImageSelected() != null) {
                if (tabModel.getImageNormal() instanceof String) {
                    //TODO Url 处理
                } else if (tabModel.getImageNormal() instanceof Integer) {
                    //TODO
                }
            }
        }
    }

    public void setIndex(int index) {
        this.index = index;
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
}
