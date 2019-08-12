package com.julive.library.smartnavigitionlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.julive.library.smartnavigitionlayout.model.TabModel;

import java.util.ArrayList;
import java.util.List;


public class SmartNavigationLayout extends LinearLayout implements View.OnClickListener {

    private List<Observer> mObservers = new ArrayList<>();
    private List<TabItemView> itemViews = new ArrayList<>();
    private int totalItem;

    public SmartNavigationLayout(Context context) {
        super(context);
        initView(null, context);
    }

    public SmartNavigationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, context);
    }

    private void initView(AttributeSet attrs, Context context) {
        LinearLayout rootView = (LinearLayout) inflate(context, R.layout.smart_navigation_layout, this);
        LinearLayout itemsView = (LinearLayout) rootView.getChildAt(0);
        totalItem = itemsView.getChildCount();
        for (int i = 0; i < itemsView.getChildCount(); i++) {
            TabItemView tabItemView = (TabItemView) itemsView.getChildAt(i);
            tabItemView.setTag(i);
            tabItemView.setIndex(i);
            tabItemView.setOnClickListener(this);
            register(tabItemView);
        }
    }

    private void register(Observer observer) {
        if (mObservers.contains(observer)) {
            return;
        }
        mObservers.add(observer);
    }

    private void unRegister(Observer observer) {
        mObservers.remove(observer);
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

    public void setTabStyle(TabModel tabModel) {
        notifyItemUpdateStyle(tabModel);
    }

    public void setTabListStyle(List<TabModel> tabModelList) {
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
     * 设置某个 Tab 小红点的展示与展示数量
     *
     * @param index        Tab
     * @param isVisibility 是否展示
     * @param count        展示未读数量
     */
    public void setPointVisibility(int index, boolean isVisibility, int count) {
        notifyItemPointVisibility(index, isVisibility, count);
    }
}
