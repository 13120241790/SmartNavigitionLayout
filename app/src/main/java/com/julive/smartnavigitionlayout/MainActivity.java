package com.julive.smartnavigitionlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.julive.library.navigation.OnTabItemClickListener;
import com.julive.library.navigation.SmartNavigationLayout;
import com.julive.library.navigation.model.TabModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SmartNavigationLayout navigationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationLayout = findViewById(R.id.smart_navigation_layout);
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
                navigationLayout.refreshTabListStyle(createStyles(), R.color.color_tab_indicator_text_normal, R.color.color_tab_indicator_text_selected);
                break;
            case 3:
                navigationLayout.refreshTabStyle(new TabModel(3)
                        .setImageNormal(HTTP_IMAGE2)
                        .setImageSelected(HTTP_IMAGE)
                        .setText("网络下发")
                );
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 支持某个下标的红点是否展示 和 展示 count 数量
     *
     * @param index
     * @param isVisibility
     * @param count
     */
    public void setRedPointVisiblity(int index, boolean isVisibility, int count) {
        navigationLayout.setPointVisibility(index, isVisibility, count);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "切换 Tab Style");
        menu.add(1, 2, 2, "更换整体样式");
        menu.add(1, 3, 3, "模拟网络下发 Icon");
        return true;
    }

    //模拟网络下发假数据
    public static final String HTTP_IMAGE = "http://b-ssl.duitang.com/uploads/item/201801/29/20180129175713_54C2M.thumb.700_0.jpeg";
    public static final String HTTP_IMAGE2 = "http://img1.imgtn.bdimg.com/it/u=2069190746,1978651553&fm=26&gp=0.jpg";


    public List<TabModel> createStyles() {
        List<TabModel> tabModels = new ArrayList<>();
        tabModels.add(new TabModel(0)
                .setImageNormal(R.drawable.rce_ic_tab_chat)
                .setImageSelected(R.drawable.rce_ic_tab_chat_selected)
                .setText("消息")
        );
        tabModels.add(new TabModel(1)
                .setImageNormal(R.drawable.rce_ic_tab_contact)
                .setImageSelected(R.drawable.rce_ic_tab_contact_selected)
                .setText("通讯录")
        );
        tabModels.add(new TabModel(2)
                .setImageNormal(R.drawable.rce_ic_tab_oa)
                .setImageSelected(R.drawable.rce_ic_tab_oa_selected)
                .setText("工作")
        );
        tabModels.add(new TabModel(3)
                .setImageNormal(R.drawable.rce_ic_tab_me)
                .setImageSelected(R.drawable.rce_ic_tab_me_selected)
                .setText("我的")
        );
        return tabModels;
    }
}
