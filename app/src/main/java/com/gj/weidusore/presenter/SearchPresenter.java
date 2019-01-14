package com.gj.weidusore.presenter;

import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;

public class SearchPresenter extends BasePresenter{
    private int page = 1;
    private boolean isRefresh = true;

    public SearchPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        isRefresh = (boolean) args[0];
        if(isRefresh){//刷新
            page=1;
        }else {
            page++;
        }
        return iRequest.getSearch((String) args[1],page,(int)args[2]);
    }

    public boolean isRefresh() {
        return isRefresh;
    }
}
