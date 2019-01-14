package com.gj.weidusore.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.core.WDActivity;
import com.gj.weidusore.core.db.DaoMaster;
import com.gj.weidusore.core.db.UserInfoLoginDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeansActivity extends WDActivity {


    @BindView(R.id.means_name)
    TextView meansName;
    @BindView(R.id.means_password)
    TextView meansPassword;
    @BindView(R.id.means_img)
    SimpleDraweeView meansImg;

    @Override
    protected void initView() {
        UserInfoLoginDao loginDao = DaoMaster.newDevSession(getBaseContext(),
                UserInfoLoginDao.TABLENAME).getUserInfoLoginDao();//数据库
        List<UserInfoLogin> userInfoLogins = loginDao.loadAll();
        String nickName = userInfoLogins.get(0).getNickName();
        String headPic = userInfoLogins.get(0).getHeadPic();
        String phone = userInfoLogins.get(0).getPhone();

        meansImg.setImageURI(headPic);
        meansName.setText(nickName);
        meansPassword.setText(phone);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_means;
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
