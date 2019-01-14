package com.gj.weidusore.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomActivity extends WDActivity {

    @BindView(R.id.seek_text)
    TextView seekText;

    private int count = 3;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            count--;
            seekText.setText("跳过"+count+"s");
            if(count==0){
                if(LOGIN_USER!=null){
                    intent(GoodsHomeActivity.class);
                    finish();
                }else{
                    intent(LoginActivity.class);
                    finish();
                }
            }else {//消息不能复用，必须新建
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        seekText.setText("跳过"+count+"s");
        handler.sendEmptyMessageDelayed(1,1000);
    }

    @OnClick(R.id.seek_text)
    public void seek(){
        handler.removeMessages(1);
        if(LOGIN_USER!=null){//有数据登录
            intent(GoodsHomeActivity.class);
            finish();
        }else{//没有数据注册
            intent(LoginActivity.class);
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcom;
    }

    @Override
    protected void destoryData() {

    }
}
