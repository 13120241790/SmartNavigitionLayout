package com.julive.smartnavigationlayout;

import com.julive.library.navigation.model.TabModel;

import java.util.ArrayList;
import java.util.List;

public class SampleDataProvider {

    //模拟网络下发假数据
    public static final String HTTP_IMAGE = "http://b-ssl.duitang.com/uploads/item/201801/29/20180129175713_54C2M.thumb.700_0.jpeg";
    public static final String HTTP_IMAGE2 = "http://img1.imgtn.bdimg.com/it/u=2069190746,1978651553&fm=26&gp=0.jpg";
    public static final String HTTP_IMAGE3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565761894061&di=4418ec80bd37ad1ef70e4906edd885de&imgtype=0&src=http%3A%2F%2Fimg.qqzhi.com%2Fuploads%2F2018-12-07%2F002719178.jpg";
    public static final String HTTP_IMAGE4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565761967416&di=43780923505e7f504a47abe55b4e3f2f&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170924%2F0520062146-0.jpg";
    public static final String HTTP_IMAGE5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565761989482&di=496229f962a53d49260d183c27f2f81c&imgtype=0&src=http%3A%2F%2Fimg.qqzhi.com%2Fuploads%2F2019-01-19%2F121240236.jpg";
    public static final String HTTP_IMAGE6 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2038187658,2000430886&fm=26&gp=0.jpg";
    public static final String HTTP_IMAGE7 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566356765&di=577d11a693ab9b68c87520d991b264e5&imgtype=jpg&er=1&src=http%3A%2F%2Fgss0.baidu.com%2F-Po3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3Dc39b4fb8d42a283443f33e0f6e85e5dc%2Fb151f8198618367a797620782e738bd4b21ce59c.jpg";
    public static final String HTTP_IMAGE8 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=159909707,1643658237&fm=26&gp=0.jpg";

    public static List<TabModel> createStyles() {
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

    public static List<TabModel> createNetWorkStyles() {
        List<TabModel> tabModels = new ArrayList<>();
        tabModels.add(new TabModel(0)
                .setImageNormal(HTTP_IMAGE)
                .setImageSelected(HTTP_IMAGE2)
                .setText("网络下发1")
        );
        tabModels.add(new TabModel(1)
                .setImageNormal(HTTP_IMAGE3)
                .setImageSelected(HTTP_IMAGE4)
                .setText("网络下发2")
        );
        tabModels.add(new TabModel(2)
                .setImageNormal(HTTP_IMAGE5)
                .setImageSelected(HTTP_IMAGE6)
                .setText("网络下发3")
        );
        tabModels.add(new TabModel(3)
                .setImageNormal(HTTP_IMAGE7)
                .setImageSelected(HTTP_IMAGE8)
                .setText("网络下4")
        );
        return tabModels;
    }
}
