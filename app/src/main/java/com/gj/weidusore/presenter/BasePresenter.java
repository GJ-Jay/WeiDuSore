package com.gj.weidusore.presenter;

import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.exception.CustomException;
import com.gj.weidusore.core.exception.ResponseTransformer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {//抽离的P
    private DataCall dataCall;//调用接口
    private boolean running;

    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    protected abstract Observable<ResultLogin<List<Search>>> observable(Object...args);

    public void reqeust(Object...args){
        if(running){//正在运行就驳回
            return;
        }
        running = true;//否则开始运行并发送请求
        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
//                .subscribeOn(Schedulers.newThread())//将请求调度到子线程上
//                .observeOn(AndroidSchedulers.mainThread())//观察响应结果，把响应结果调度到主线程中处理
                .subscribe(new Consumer<ResultLogin>() {
                    @Override
                    public void accept(ResultLogin resultLogin) throws Exception {
                        running = false;
                        dataCall.success(resultLogin);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //处理异常
//                        UIUtils.showToastSafe("请求失败");
                        running = false;
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });
    }
    public void unBind() {//防止内存泄漏
        dataCall = null;
    }

    //向外暴露一个running状态
    public boolean isRunning() {
        return running;
    }
}
