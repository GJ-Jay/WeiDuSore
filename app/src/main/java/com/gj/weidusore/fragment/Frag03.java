package com.gj.weidusore.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.CarAdapter;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.car.Car;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDFragment;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.CarPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Frag03 extends WDFragment implements XRecyclerView.LoadingListener {


    @BindView(R.id.xrlv_car)
    XRecyclerView xrlvCar;
    @BindView(R.id.cb_all_car)
    CheckBox cbAllCar;
    @BindView(R.id.price_car)
    TextView priceCar;
    @BindView(R.id.js_close_btn)
    Button jsCloseBtn;
    Unbinder unbinder;
    private CarAdapter carAdapter;
    private CarPresenter carPresenter;

    @Override
    public String getPageName() {
        return "购物车 fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag03_three;
    }

    @Override
    protected void initView() {
        xrlvCar.setLayoutManager(new GridLayoutManager(getContext(),1));//布局管理器
        carAdapter = new CarAdapter(getActivity());
        xrlvCar.setAdapter(carAdapter);
        xrlvCar.setLoadingListener(this);

        carPresenter = new CarPresenter(new CarCall());
        xrlvCar.refresh();
        carAdapter.setCartlistener(new CarAdapter.Cartlistener() {
            @Override
            public void GoodsChange() {
                boolean  allshop=carAdapter.isAllshopselected();
                cbAllCar.setChecked(allshop);

                carAdapter.notifyDataSetChanged();
                changeprice();
            }

            @Override
            public void NumChange(int index, int num) {
                carAdapter.changeshopnum(index,num);
                changeprice();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        if (carPresenter.isRunning()){
            xrlvCar.refreshComplete();
            return;
        }
        carPresenter.reqeust(true,LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if (carPresenter.isRunning()) {
            xrlvCar.loadMoreComplete();
            return;
        }
        carPresenter.reqeust(false,LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
    }

    private class CarCall implements DataCall<ResultLogin<List<Car>>>{

        @Override
        public void success(ResultLogin<List<Car>> data) {
            xrlvCar.refreshComplete();
            xrlvCar.loadMoreComplete();
            if (data.getStatus().equals("0000")){
                carAdapter.addAll(data.getResult());
                carAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            xrlvCar.refreshComplete();
            xrlvCar.loadMoreComplete();
        }

    }
    @OnClick({R.id.cb_all_car, R.id.js_close_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_all_car:
                boolean checked = cbAllCar.isChecked();
                carAdapter.setAllshopselected(checked);
                carAdapter.notifyDataSetChanged();
                changeprice();
                break;
            case R.id.js_close_btn:
                break;
        }

    }


    private void changeprice() {
        float setsumprice = carAdapter.setsumprice();
        priceCar.setText("合计：￥" + setsumprice);
    }

}

