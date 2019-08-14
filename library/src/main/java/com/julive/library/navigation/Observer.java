package com.julive.library.navigation;

import com.julive.library.navigation.model.TabModel;

public interface Observer {
    void update(int index);

    /**
     * 刷新某个 Tab 样式
     *
     * @param tabModel 样式实体
     */
    void updateItemStyle(TabModel tabModel);

    /**
     * 控制红点是否展示
     *
     * @param index        指向 Tab 的下标
     * @param isVisibility 是否展示
     * @param count        小红点展示的数量
     */
    void pointVisibility(int index, boolean isVisibility, int count);
}
