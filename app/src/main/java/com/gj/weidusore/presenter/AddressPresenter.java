package com.gj.weidusore.presenter;

import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import java.util.List;

import io.reactivex.Observable;

public class AddressPresenter extends BasePresenter{

    public AddressPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.addressList((long)args[0],(String) args[1]);
    }
}
