package com.julive.library.navigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class RedPointTabItemView extends TabItemView {
    public RedPointTabItemView(Context context) {
        super(context);
    }

    public RedPointTabItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RedPointTabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int providerItemLayout() {
        return R.layout.red_point_tab_item_view;
    }
}
