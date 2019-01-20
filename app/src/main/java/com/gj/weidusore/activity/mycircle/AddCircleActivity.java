package com.gj.weidusore.activity.mycircle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.mycircle.ImageAdapter;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDActivity;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.fragment.Frag02;
import com.gj.weidusore.presenter.mycircle.PublishCirclePresenter;
import com.gj.weidusore.util.StringUtils;
import com.gj.weidusore.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCircleActivity extends WDActivity implements DataCall<ResultLogin> {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.send)
    ImageView send;
    @BindView(R.id.bo_text)
    EditText boText;
    @BindView(R.id.bo_image_list)
    RecyclerView boImageList;
    @BindView(R.id.bo_address)
    TextView boAddress;
    @BindView(R.id.topic_add_layout)
    RelativeLayout topicAddLayout;
    private ImageAdapter imageAdapter;
    private PublishCirclePresenter publishCirclePresenter;


    @Override
    protected void initView() {
        imageAdapter = new ImageAdapter();
        imageAdapter.setSign(1);
        imageAdapter.add(R.drawable.mask_01);
        boImageList.setLayoutManager(new GridLayoutManager(this,3));
        boImageList.setAdapter(imageAdapter);

        publishCirclePresenter = new PublishCirclePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_circle;
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.back)
    public void back(){
        finish();
    }
    @OnClick(R.id.send)
    public void publish(){
        publishCirclePresenter.reqeust(LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId(),
                1,boText.getText().toString(),imageAdapter.getList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
            if (!StringUtils.isEmpty(filePath)) {
                imageAdapter.add(filePath);
                imageAdapter.notifyDataSetChanged();
//                Bitmap bitmap = UIUtils.decodeFile(new File(filePath),200);
//                mImage.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public void success(ResultLogin data) {
        if (data.getStatus().equals("0000")){
            Frag02.addCircle = true;
            finish();
        }else{
            UIUtils.showToastSafe(data.getStatus()+"  "+data.getMessage());
        }
    }

    @Override
    public void fail(ApiException e) {
        UIUtils.showToastSafe(e.getCode()+"  "+e.getDisplayMessage());
    }
}

