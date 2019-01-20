package com.gj.weidusore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gj.weidusore.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Frag04 extends Fragment {
    @BindView(R.id.radio_indent)
    RadioButton radioIndent;
    @BindView(R.id.radio_pay)
    RadioButton radioPay;
    @BindView(R.id.radio_receive)
    RadioButton radioReceive;
    @BindView(R.id.radio_estimate)
    RadioButton radioEstimate;
    @BindView(R.id.radio_finish)
    RadioButton radioFinish;
    @BindView(R.id.radioGroup01)
    RadioGroup radioGroup01;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    Unbinder unbinder;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment01 fragment01;
    private Fragment02 fragment02;
    private Fragment03 fragment03;
    private Fragment04 fragment04;
    private Fragment05 fragment05;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04_four, container, false);
        unbinder = ButterKnife.bind(this, view);
        //获取管理者
        manager = getActivity().getSupportFragmentManager();
        //开启事务
        transaction = manager.beginTransaction();
        //添加Fragmnet
        fragment01 = new Fragment01();
        fragment02 = new Fragment02();
        fragment03 = new Fragment03();
        fragment04 = new Fragment04();
        fragment05 = new Fragment05();

        transaction.add(R.id.framelayout, fragment01).show(fragment01);
        transaction.add(R.id.framelayout, fragment02).hide(fragment02);
        transaction.add(R.id.framelayout, fragment03).hide(fragment03);
        transaction.add(R.id.framelayout, fragment04).hide(fragment04);
        transaction.add(R.id.framelayout, fragment05).hide(fragment05);
        //提交
        transaction.commit();
        //默认第一个选中
        radioGroup01.check(radioGroup01.getChildAt(0).getId());
        //切换按钮改变状态
        radioGroup01.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId){
                    case R.id.radio_indent:
                        transaction1.show(fragment01).hide(fragment02).hide(fragment03).hide(fragment04).hide(fragment05);
                        break;
                    case R.id.radio_pay:
                        transaction1.show(fragment02).hide(fragment01).hide(fragment03).hide(fragment04).hide(fragment05);
                        break;
                    case R.id.radio_receive:
                        transaction1.show(fragment03).hide(fragment02).hide(fragment01).hide(fragment04).hide(fragment05);
                        break;
                    case R.id.radio_estimate:
                        transaction1.show(fragment04).hide(fragment02).hide(fragment03).hide(fragment01).hide(fragment05);
                        break;
                    case R.id.radio_finish:
                        transaction1.show(fragment05).hide(fragment02).hide(fragment03).hide(fragment04).hide(fragment01);
                        break;
                }
                transaction1.commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
