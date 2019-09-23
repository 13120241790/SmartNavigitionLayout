package com.julive.smartnavigationlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.julive.library.navigation.OnTabItemClickListener;
import com.julive.library.navigation.RemindType;
import com.julive.library.navigation.SmartNavigationLayout;
import com.julive.library.navigation.model.TabModel;


public class MainActivity extends AppCompatActivity {

    private SmartNavigationLayout navigationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationLayout = findViewById(R.id.smart_navigation_layout);
        navigationLayout.configRedPointByIndex(1, 150, 20, RemindType.REMIND_TEXT)
                .configRedPointByIndex(3, 150, 20, RemindType.REMIND_NORMAL);
        navigationLayout.setOnTabItemClickListener(new OnTabItemClickListener() {
            @Override
            public void itemClick(int index) {
                Toast.makeText(MainActivity.this, "Click : " + index + " Tab", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                navigationLayout.refreshTabStyle(new TabModel(2)
                        .setImageNormal(R.drawable.tab_web)
                        .setImageSelected(R.drawable.tab_web_pre)
                        .setText("情报")
                );
                break;
            case 2:
                navigationLayout.refreshTabListStyle(SampleDataProvider.createStyles(), R.color.color_tab_indicator_text_normal, R.color.color_tab_indicator_text_selected);
                break;
            case 3:
                navigationLayout.refreshTabListStyle(SampleDataProvider.createNetWorkStyles());
                break;
            case 4:
                setRedPointVisibility(1, true, 9);
                break;
            case 5:
                setRedPointVisibility(1, false, 9);
                break;
            case 6:
                setRedPointVisibility(3, true, 9);
                break;
            case 7:
                setRedPointVisibility(3, false, 9);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "切换 Tab Style");
        menu.add(1, 2, 2, "更换整体样式");
        menu.add(1, 3, 3, "模拟网络下发 Icon");
        menu.add(1, 4, 4, "展示未读小红点");
        menu.add(1, 5, 5, "隐藏未读小红点");
        menu.add(1, 6, 6, "展示提醒小红点");
        menu.add(1, 7, 7, "隐藏提醒小红点");
        return true;
    }

    /**
     * 支持某个下标的红点是否展示 和 展示 count 数量
     *
     * @param index
     * @param isVisibility
     * @param count
     */
    public void setRedPointVisibility(int index, boolean isVisibility, int count) {
        navigationLayout.setPointVisibility(index, isVisibility, count);
    }

}
