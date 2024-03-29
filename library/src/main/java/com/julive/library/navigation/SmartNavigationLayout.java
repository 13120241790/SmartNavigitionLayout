package com.julive.library.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.julive.library.navigation.model.TabModel;

import java.util.ArrayList;
import java.util.List;


public class SmartNavigationLayout extends LinearLayout implements View.OnClickListener {

    private List<Observer> mObservers = new ArrayList<>();

    public SmartNavigationLayout(Context context) {
        super(context);
        initView(null, context);
    }

    public SmartNavigationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, context);
    }

    private void initView(AttributeSet attrs, Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartNavigationLayout);
        int textColorNormal = typedArray.getColor(R.styleable.SmartNavigationLayout_text_color_normal, getResources().getColor(R.color.colorText));
        int textColorSelected = typedArray.getColor(R.styleable.SmartNavigationLayout_text_color_selected, getResources().getColor(R.color.color_FF3E4A59));
        int defaultIndex = typedArray.getInt(R.styleable.SmartNavigationLayout_default_index, 0);
        typedArray.recycle();
        LinearLayout rootView = (LinearLayout) inflate(context, R.layout.smart_navigation_layout, this);
        LinearLayout itemsView = (LinearLayout) rootView.getChildAt(0);
        for (int i = 0; i < itemsView.getChildCount(); i++) {
            TabItemView tabItemView = (TabItemView) itemsView.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
            tabItemView.setLayoutParams(layoutParams);
            tabItemView.setTag(i);
            tabItemView.setTextColor(textColorNormal, textColorSelected);
            tabItemView.setOnClickListener(this);
            tabItemView.setSelected(defaultIndex == i);
            register(tabItemView);
        }
    }


    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        notifyEveryOne(index);
        if (mOnTabItemClickListener != null) {
            mOnTabItemClickListener.itemClick(index);
        }
    }

    private OnTabItemClickListener mOnTabItemClickListener;

    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        mOnTabItemClickListener = onTabItemClickListener;
    }

    /**
     * 刷新某个样式
     */
    public void refreshTabStyle(TabModel tabModel) {
        notifyItemUpdateStyle(tabModel);
    }

    /**
     * 刷新整体样式
     */
    public void refreshTabListStyle(List<TabModel> tabModelList) {
        if (tabModelList != null && tabModelList.size() > 0) {
            if (tabModelList.size() == 1) {
                notifyItemUpdateStyle(tabModelList.get(0));
                return;
            }
            for (TabModel t : tabModelList) {
                notifyItemUpdateStyle(t);
            }
        }
    }

    /**
     * 刷新整体样式
     *
     * @param colorNormal   文字正常颜色
     * @param colorSelected 文字选中颜色
     */
    public void refreshTabListStyle(List<TabModel> tabModelList, int colorNormal, int colorSelected) {
        if (tabModelList != null && tabModelList.size() > 0) {
            if (tabModelList.size() == 1) {
                notifyItemUpdateStyle(tabModelList.get(0));
                return;
            }
            for (TabModel t : tabModelList) {
                t.setTextColorSelected(colorSelected);
                t.setTextColorNormal(colorNormal);
                notifyItemUpdateStyle(t);
            }
        }
    }

    /**
     * 设置某个 Tab 小红点的展示与展示数量
     *
     * @param index        Tab
     * @param isVisibility 是否展示
     * @param count        展示未读数量
     */
    public void setPointVisibility(int index, boolean isVisibility, int count) {
        notifyItemPointVisibility(index, isVisibility, count);
    }

    /**
     * 小红点的配置项
     *
     * @param index      指定哪个下标的小红点，下标从 0 开始
     * @param leftOffset 距 Tab 左上角 X 轴 从 0 开始的偏移量 单位 px
     * @param topOffset  距 Tab 顶部 Y 轴 从 0 开始的偏移量 单位 px
     * @param remindType 提醒状态
     * @return SmartNavigationLayout
     */
    public SmartNavigationLayout configRedPointByIndex(int index, int leftOffset, int topOffset, RemindType remindType) {
        if (index < 0 || leftOffset < 0 || topOffset < 0) {
            throw new IllegalArgumentException();
        }
        notifyItemPointConfig(index, leftOffset, topOffset, remindType);
        return this;
    }

    private void register(Observer observer) {
        if (mObservers.contains(observer)) {
            return;
        }
        mObservers.add(observer);
    }

    private void notifyEveryOne(int index) {
        for (Observer observer : mObservers) {
            observer.update(index);
        }
    }

    private void notifyItemUpdateStyle(TabModel tabModel) {
        for (Observer observer : mObservers) {
            observer.updateItemStyle(tabModel);
        }
    }

    private void notifyItemPointVisibility(int index, boolean isVisibility, int count) {
        for (Observer observer : mObservers) {
            observer.pointVisibility(index, isVisibility, count);
        }
    }

    private void notifyItemPointConfig(int index, int leftOffset, int topOffset, RemindType remindType) {
        for (Observer observer : mObservers) {
            observer.pointStyleConfig(index, leftOffset, topOffset, remindType);
        }
    }

}
