package com.gj.weidusore.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.activity.LoginActivity;
import com.gj.weidusore.activity.MyCircleActivity;
import com.gj.weidusore.activity.MyFootActivity;
import com.gj.weidusore.activity.MyMeansActivity;
import com.gj.weidusore.activity.address.MyAddressActivity;
import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.core.WDFragment;
import com.gj.weidusore.core.db.DaoMaster;
import com.gj.weidusore.core.db.UserInfoLoginDao;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Frag05 extends WDFragment {
    @BindView(R.id.text_myname)
    TextView textMyname;
    @BindView(R.id.my_two_circle)
    LinearLayout myTwoCircle;
    @BindView(R.id.my_three_foot)
    LinearLayout myThreeFoot;
    @BindView(R.id.my_four_wallet)
    LinearLayout myFourWallet;
    @BindView(R.id.my_five_address)
    LinearLayout myFiveAddress;
    @BindView(R.id.lay)
    LinearLayout lay;
    Unbinder unbinder;
    @BindView(R.id.my_myphoto)
    SimpleDraweeView myMyphoto;
    @BindView(R.id.my_one_means)
    LinearLayout myOneMeans;
    @BindView(R.id.btn_back)
    Button btnBack;

    private UserInfoLoginDao loginDao;

    @Override
    public String getPageName() {
        return "我的 退出登录";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag05_five;
    }

    @Override
    protected void initView() {
        //数据库
        loginDao = DaoMaster.newDevSession(getActivity(),
                UserInfoLoginDao.TABLENAME).getUserInfoLoginDao();
        List<UserInfoLogin> userInfoLogins = loginDao.loadAll();
        String headPic = userInfoLogins.get(0).getHeadPic();
        myMyphoto.setImageURI(headPic);
    }

    @OnClick({R.id.my_one_means, R.id.my_two_circle,R.id.my_three_foot, R.id.btn_back,R.id.my_five_address})
    public void setOnClicke(View view) {
        switch (view.getId()) {
            case R.id.my_one_means:
                startActivity(new Intent(getActivity(), MyMeansActivity.class));
                break;
            case R.id.my_two_circle:
                startActivity(new Intent(getActivity(), MyCircleActivity.class));
                break;
            case R.id.btn_back:
                loginDao.delete(LOGIN_USER);//Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
                                            //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//重点
                startActivity(intent);//重点
                break;
            case R.id.my_three_foot:
                startActivity(new Intent(getActivity(), MyFootActivity.class));
                break;

            case R.id.my_five_address:
                startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;
        }
    }

}
