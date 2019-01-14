package com.gj.weidusore.presenter;

import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import java.util.List;

import io.reactivex.Observable;

public class FootPresenter extends BasePresenter{

    private int page = 1;

    public int getPage() {
        return page;
    }

    public FootPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        boolean refresh = (boolean) args[0];//获取刷新状态
        if(refresh){
            page = 1;
        }else {
            page++;
        }
        return iRequest.getFoot((long)args[1],(String) args[2],page,20);
    }
}
