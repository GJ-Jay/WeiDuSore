package com.gj.weidusore.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.MyAddressAdapter;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.bean.address.Address;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.db.DaoMaster;
import com.gj.weidusore.core.db.DaoSession;
import com.gj.weidusore.core.db.UserInfoLoginDao;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.AddressPresenter;
import com.gj.weidusore.presenter.MorenPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressActivity extends AppCompatActivity {

    @BindView(R.id.address_finish)
    TextView addressFinish;
    @BindView(R.id.address_add)
    Button addressAdd;
    @BindView(R.id.address_recy)
    RecyclerView addressRecy;
    private MyAddressAdapter myAddressAdapter;
    private int a;
    private long userId;
    private String sessionId;
    private static final String TAG = "AddressActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoLoginDao.TABLENAME);
        UserInfoLoginDao userInfoLoginDao = daoSession.getUserInfoLoginDao();
        List<UserInfoLogin> list = userInfoLoginDao.loadAll();
        userId = list.get(0).getUserId();
        sessionId = list.get(0).getSessionId();
        new AddressPresenter(new MyCall()).reqeust((long) userId, sessionId);
        myAddressAdapter = new MyAddressAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        addressRecy.setLayoutManager(manager);
        addressRecy.setAdapter(myAddressAdapter);
        myAddressAdapter.setOnItemclicks(new MyAddressAdapter.OnItemclick() {
            @Override
            public void onItem(int position) {
                a = position;
                Log.d(TAG, "onItem: " + a + "");
            }
        });

    }

    @OnClick({R.id.address_finish, R.id.address_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_finish://点击完成
                new MorenPresenter(new My()).reqeust((int) userId, sessionId, a);
                finish();
                break;
            case R.id.address_add://新增收货地址
                Intent intent = new Intent(this, NewAddressActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    class MyCall implements DataCall<ResultLogin<List<Address>>> {

        @Override
        public void success(ResultLogin<List<Address>> data) {
            List<Address> result = data.getResult();
            myAddressAdapter.addList(result);
            myAddressAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class My implements DataCall<ResultLogin> {
        @Override
        public void success(ResultLogin data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(MyAddressActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }
}
