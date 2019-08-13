package com.julive.library.smartnavigitionlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.julive.library.smartnavigitionlayout.model.TabModel;

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
            tabItemView.setTag(i);
            tabItemView.setIndex(i);
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

    public void refreshTabStyle(TabModel tabModel) {
        notifyItemUpdateStyle(tabModel);
    }

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

}
