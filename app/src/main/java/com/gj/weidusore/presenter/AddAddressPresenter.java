package com.gj.weidusore.presenter;

import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import java.util.List;

import io.reactivex.Observable;

public class AddAddressPresenter extends BasePresenter{

    public AddAddressPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.addAddrss((int)args[0],(String) args[1],(String) args[2],
                (String)args[3],(String)args[4],(String)args[5]);
    }
}
