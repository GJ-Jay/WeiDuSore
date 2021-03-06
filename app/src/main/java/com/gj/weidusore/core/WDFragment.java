package com.gj.weidusore.core;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.core.db.DaoMaster;
import com.gj.weidusore.core.db.UserInfoLoginDao;
import com.gj.weidusore.util.LogUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class WDFragment extends Fragment {

    public Gson mGson = new Gson();
    public SharedPreferences mShare = WDApplication.getShare();
    public UserInfoLogin LOGIN_USER;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserInfoLoginDao userInfoDao = DaoMaster.newDevSession(getActivity(), UserInfoLoginDao.TABLENAME).getUserInfoLoginDao();
        List<UserInfoLogin> userInfos = userInfoDao.queryBuilder().where(UserInfoLoginDao.Properties.Status.eq(1)).list();
        LOGIN_USER = userInfos.get(0);//读取第一项

        // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
        long time = System.currentTimeMillis();
        View view = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        LogUtils.e(this.toString()+"页面加载使用："+(System.currentTimeMillis()-time));
        return view;
    }

    //	@Override
    //	public void onResume() {
    //		super.onResume();
    //		if (!MTStringUtils.isEmpty(getPageName()))
    //			MobclickAgent.onPageStart(getPageName()); // 统计页面
    //	}
    //
    //	@Override
    //	public void onPause() {
    //		super.onPause();
    //		if (!MTStringUtils.isEmpty(getPageName()))
    //			MobclickAgent.onPageEnd(getPageName());// 统计页面
    //	}

    /**
     * 设置页面名字 用于友盟统计
     */
    public abstract String getPageName();
    /**
     * 设置layoutId
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();
}
