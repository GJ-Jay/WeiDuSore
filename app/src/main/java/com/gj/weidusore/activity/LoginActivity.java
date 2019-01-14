package com.gj.weidusore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gj.weidusore.R;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDActivity;
import com.gj.weidusore.core.WDApplication;
import com.gj.weidusore.core.db.DaoMaster;
import com.gj.weidusore.core.db.UserInfoLoginDao;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.LoginPresenter;
import com.gj.weidusore.util.MD5Utils;
import com.gj.weidusore.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面 封装了butterknif和引用布局 吐司 以及记住密码
 */
public class LoginActivity extends WDActivity {


    @BindView(R.id.edit_login_phone)
    EditText editLoginPhone;
    @BindView(R.id.edit_login_password)
    EditText editLoginPassword;
    @BindView(R.id.image_login_eye)
    ImageView imageLoginEye;
    @BindView(R.id.check_rember)
    CheckBox mRemPas;
    /*ImageView checkRember;*/
    @BindView(R.id.text_reg)
    TextView textReg;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private LoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {//写布局文件
        return R.layout.activity_login;
    }

    @Override
    protected void destoryData() {//防止内存泄漏
        loginPresenter.unBind();
    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(new LoginCall());
        boolean remPas = WDApplication.getShare().
                getBoolean("remPas", true);//记住密码判断
        if (remPas) {
            mRemPas.setChecked(true);
            editLoginPhone.setText(WDApplication.getShare()
                    .getString("mobile", ""));
            editLoginPassword.setText(WDApplication.getShare().
                    getString("pas", ""));
        }
    }

    @OnClick({R.id.check_rember, R.id.text_reg, R.id.btn_login, R.id.image_login_eye})
    public void setLoginClick(View view) {
        switch (view.getId()) {
            case R.id.check_rember://判断是否记住密码
                WDApplication.getShare().edit().putBoolean("remPas",
                        mRemPas.isChecked()).commit();
                break;

            case R.id.text_reg://点击快速登录跳转
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login://登录按钮
                String name = editLoginPhone.getText().toString();
                String pwd = editLoginPassword.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mRemPas.isChecked()) {//如果记住密码选中
                    WDApplication.getShare().edit().putString("mobile", name)
                            .putString("pas", pwd).commit();//设置账户密码
                }
                loginPresenter.reqeust(name, MD5Utils.md5(pwd));
                break;

            case R.id.image_login_eye://显示隐藏密码切换
                if (editLoginPassword.getInputType() == (InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                    editLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    editLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
        }

    }

    class LoginCall implements DataCall<ResultLogin<UserInfoLogin>> {
        @Override
        public void success(ResultLogin<UserInfoLogin> data) {
            UserInfoLogin result = data.getResult();
            Log.e("UerId:" + result.getUserId(), "SessionId:" + result.getSessionId());
            mLoadDialog.cancel();
            if (data.getStatus().equals("0000")) {
                data.getResult().setStatus(1);//设置登录状态，保存到数据库
                UserInfoLoginDao infoLoginDao = DaoMaster.newDevSession(getBaseContext(),
                        UserInfoLoginDao.TABLENAME).getUserInfoLoginDao();//信息存入数据库
                infoLoginDao.insertOrReplace(data.getResult());
                startActivity(new Intent(LoginActivity.this, GoodsHomeActivity.class));//跳转页面
                finish();
            } else {
                UIUtils.showToastSafe(data.getStatus() + "  " + data.getMessage());//吐司
            }
        }

        @Override
        public void fail(ApiException e) {
            mLoadDialog.cancel();
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }
}
