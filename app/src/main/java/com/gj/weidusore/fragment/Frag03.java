package com.gj.weidusore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.activity.car.AccountActivity;
import com.gj.weidusore.adapter.CarAdapter;
import com.gj.weidusore.adapter.car.ShopAdapter;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.bean.car.Car;
import com.gj.weidusore.bean.car.ShopCar;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDFragment;
import com.gj.weidusore.core.db.DaoMaster;
import com.gj.weidusore.core.db.DaoSession;
import com.gj.weidusore.core.db.ShopCarDao;
import com.gj.weidusore.core.db.UserInfoLoginDao;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.CarPresenter;
import com.gj.weidusore.presenter.details.ShoppingPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Frag03 extends Fragment implements ShopAdapter.TotalPriceListener{

    @BindView(R.id.expandableListView)
    SwipeMenuRecyclerView expandableListView;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.sumss)
    TextView sumss;
    @BindView(R.id.jiesuan)
    Button jiesuan;
    Unbinder unbinder;
    private UserInfoLoginDao userDao;
    private long userId;
    private String sessionId;
    private ShopAdapter shopAdapter;
    private ShoppingPresenter shoppingPresenter;
    private List<ShopCar> result;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag03_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaoSession daoSession1 = DaoMaster.newDevSession(getContext(), UserInfoLoginDao.TABLENAME);
        userDao = daoSession1.getUserInfoLoginDao();
        final List<UserInfoLogin> list2 = userDao.loadAll();
        userId = (int) list2.get(0).getUserId();
        sessionId = list2.get(0).getSessionId();

        //计算总价
        sumss = view.findViewById(R.id.sumss);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        expandableListView.setLayoutManager(linearLayoutManager);
        //创建适配器
        shopAdapter = new ShopAdapter(getActivity());
        //设置适配器

        shopAdapter.setTotalPriceListener((ShopAdapter.TotalPriceListener) this);//设置总价回调接口
        //获取全选控件
        CheckBox checkboxs = view.findViewById(R.id.checkbox1);
        checkboxs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //调用adapter里面的全选/全不选方法
                shopAdapter.checkAll(isChecked);
            }
        });

        expandableListView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(getContext());
                swipeMenuItem.setBackgroundColor(0xff00000)
                        .setText("删除")
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//设置高，这里使用match_parent，就是与item的高相同
                        .setWidth(80);//设置宽
                rightMenu.addMenuItem(swipeMenuItem);//设置右边的侧滑
            }
        });
        expandableListView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                //int adapterPosition = menuBridge.get; // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                //Toast.makeText(getContext(), "删除" + position, Toast.LENGTH_SHORT).show();
                result.remove(position);
                //ShopCar shoppingTrolleyAdapter = new ShopCar(getContext());
                shopAdapter.addItem(result);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                expandableListView.setLayoutManager(linearLayoutManager);
                expandableListView.setAdapter(shopAdapter);

            }
        });
        expandableListView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //Toast.makeText(getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setAdapter(shopAdapter);

        //调用P层方法
        shoppingPresenter = new ShoppingPresenter(new Shopping());
        shoppingPresenter.reqeust(userId,sessionId);
        return view;
    }

    //接口回调
    @Override
    public void totalPrice(double totalPrice) {
        sumss.setText("合计：￥" + totalPrice);
    }


    @Override
    public void onResume() {
        super.onResume();
        shoppingPresenter.reqeust(userId, sessionId);
    }


    private class Shopping implements DataCall<ResultLogin<List<ShopCar>>> {

        @Override
        public void success(ResultLogin<List<ShopCar>> data) {
            result = data.getResult();
            shopAdapter.remove();
            shopAdapter.addItem(result);
            //刷新适配器
            shopAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @OnClick(R.id.jiesuan)
    public void jiesuan() {
        List<ShopCar> shoppingBeans = shopAdapter.close();
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), ShopCarDao.TABLENAME);
        ShopCarDao shopCarDao = daoSession.getShopCarDao();
        shopCarDao.deleteAll();
        for (int i = 0; i < shoppingBeans.size(); i++) {
            ShopCar shopCar = shoppingBeans.get(i);
            shopCar.setId(i);
            shopCarDao.insertOrReplace(shopCar);
        }
        Intent intent = new Intent(getActivity(), AccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

/*    @BindView(R.id.xrlv_car)
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
}*/

