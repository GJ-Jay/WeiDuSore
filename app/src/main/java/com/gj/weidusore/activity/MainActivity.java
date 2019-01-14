package com.gj.weidusore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gj.weidusore.R;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.RegPresenter;
import com.gj.weidusore.util.MD5Utils;
import com.gj.weidusore.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 注册界面
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_reg_phone)
    EditText editRegPhone;
    @BindView(R.id.edit_reg_yz)
    EditText editRegYz;
    @BindView(R.id.text_get_num)
    TextView textGetNum;
    @BindView(R.id.edit_reg_password)
    EditText editRegPassword;
    @BindView(R.id.image_reg_eye)
    ImageView imageRegEye;
    @BindView(R.id.text_reg_login)
    TextView textRegLogin;
    @BindView(R.id.btn_reg)
    Button btnReg;
    int r = 0;
    private RegPresenter regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        regPresenter = new RegPresenter(new MyReg());
    }

    @OnClick({R.id.text_get_num,R.id.image_reg_eye,R.id.text_reg_login,R.id.btn_reg})
    public void setLoginClick(View view) {
        switch (view.getId()) {
            case R.id.text_reg_login://点击快速登录跳转
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;

            case R.id.text_get_num://点击快速登录跳转
                editRegYz.setText("123456");
                break;

            case R.id.btn_reg:
                String name = editRegPhone.getText().toString();
                String pwd = editRegPassword.getText().toString();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                regPresenter.reqeust(name, MD5Utils.md5(pwd));
                break;

            case R.id.image_reg_eye://显示隐藏密码切换
                if(editRegPassword.getInputType()==(InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)){
                    editRegPassword.setInputType(InputType.TYPE_CLASS_TEXT|
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    editRegPassword.setInputType(InputType.TYPE_CLASS_TEXT|
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
        }

    }

    class MyReg implements DataCall<ResultLogin> {

        @Override
        public void success(ResultLogin data) {
            Toast.makeText(getBaseContext(),
                    data.getResult() + "   " +
                            data.getMessage(), Toast.LENGTH_LONG).show();
            if(data.getStatus().equals("0000")){
                finish();
                editRegPhone.setText("");
                editRegPassword.setText("");
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());//封装的吐司
            // getDisplayMessage是显示方式
        }
    }
}
