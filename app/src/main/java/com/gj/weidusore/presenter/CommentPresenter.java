package com.gj.weidusore.presenter;

import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;
/*
评论
 */
public class CommentPresenter extends BasePresenter{

    public CommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.comment((int)args[0],(int)args[1],(int)args[2]);
    }
}
