package com.gj.weidusore.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gj.weidusore.R;
import com.gj.weidusore.fragment.Frag01;
import com.gj.weidusore.fragment.Frag02;
import com.gj.weidusore.fragment.Frag03;
import com.gj.weidusore.fragment.Frag04;
import com.gj.weidusore.fragment.Frag05;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsHomeActivity extends AppCompatActivity {

    @BindView(R.id.frag)
    FrameLayout frag;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.radio4)
    RadioButton radio4;
    @BindView(R.id.radio5)
    RadioButton radio5;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;

    private FragmentManager manager;
    private Frag01 frag01;
    private Frag02 frag02;
    private Frag03 frag03;
    private Frag04 frag04;
    private Frag05 frag05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_home);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();//开启事务

        //添加视图
        frag01 = new Frag01();
        frag02 = new Frag02();
        frag03 = new Frag03();
        frag04 = new Frag04();
        frag05 = new Frag05();

        /* transaction.replace(R.id.frag, frag01).commit();*/
        transaction.add(R.id.frag, frag01).show(frag01);
        transaction.add(R.id.frag, frag02).hide(frag02);
        transaction.add(R.id.frag, frag03).hide(frag03);
        transaction.add(R.id.frag, frag04).hide(frag04);
        transaction.add(R.id.frag, frag05).hide(frag05);
        transaction.commit();//提交事务
        radiogroup.check(radiogroup.getChildAt(0).getId());//默认第一个页面选中


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = manager.beginTransaction();
                switch (checkedId){
                    case R.id.radio1:
                        transaction.show(frag01).hide(frag02).hide(frag03).hide(frag04).hide(frag05);
                        radiogroup.check(radiogroup.getChildAt(0).getId());
                        break;
                    case R.id.radio2:
                        transaction.show(frag02).hide(frag01).hide(frag03).hide(frag04).hide(frag05);
                        radiogroup.check(radiogroup.getChildAt(1).getId());
                        break;
                    case R.id.radio3:
                        transaction.show(frag03).hide(frag02).hide(frag01).hide(frag04).hide(frag05);
                        radiogroup.check(radiogroup.getChildAt(2).getId());
                        break;
                    case R.id.radio4:
                        transaction.show(frag04).hide(frag02).hide(frag03).hide(frag01).hide(frag05);
                        radiogroup.check(radiogroup.getChildAt(3).getId());
                        break;
                    case R.id.radio5:
                        transaction.show(frag05).hide(frag02).hide(frag03).hide(frag04).hide(frag01);
                        radiogroup.check(radiogroup.getChildAt(4).getId());
                        break;
                }
                transaction.commit();//提交事务
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
