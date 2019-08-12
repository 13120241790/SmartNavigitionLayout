package com.julive.smartnavigitionlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.julive.library.smartnavigitionlayout.OnTabItemClickListener;
import com.julive.library.smartnavigitionlayout.SmartNavigationLayout;
import com.julive.library.smartnavigitionlayout.model.TabModel;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmartNavigationLayout navigationLayout = findViewById(R.id.smart_navigation_layout);
        navigationLayout.setOnTabItemClickListener(new OnTabItemClickListener() {
            @Override
            public void itemClick(int index) {
                Toast.makeText(MainActivity.this, "点击了第: " + index + "个Tab", Toast.LENGTH_SHORT).show();
            }
        });
        new TabModel(0)
                .setText("首页")
//                .setTextColorNormal(R.drawable.tab_home)
//                .setTextColorSelected()
                .setImageNormal("")
                .setImageSelected("");


    }
}
