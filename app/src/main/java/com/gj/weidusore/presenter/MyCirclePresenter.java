package com.gj.weidusore.presenter;

import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;

public class MyCirclePresenter extends BasePresenter{

    private int page = 1;

    public int getPage() {//向外暴露一个page方法
        return page;
    }

    public MyCirclePresenter(DataCall dataCall) {
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
        return iRequest.getMyCircle((long)args[1],(String) args[2],page,20);
    }
}
