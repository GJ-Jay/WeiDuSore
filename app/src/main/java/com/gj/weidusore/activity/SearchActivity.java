package com.gj.weidusore.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.SearchAdapter;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDActivity;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.SearchPresenter;
import com.gj.weidusore.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.text_search)
    EditText textSearch;
    @BindView(R.id.xlv)
    XRecyclerView xlv;
    @BindView(R.id.search)
    TextView search;
    SearchPresenter searchPresenter = new SearchPresenter(new SearchCall());//调用P层
    @BindView(R.id.search_nogoods)
    LinearLayout searchNogoods;
    /*@BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.text_tishi)
    TextView textTishi;*/
    private SearchAdapter searchAdapter;
    private int page = 1;

    @Override
    protected void initView() {
        myXRecyclerView();
    }

    private void myXRecyclerView() {
        xlv.setLayoutManager(new GridLayoutManager(this, 2));//设置布局管理器
        searchAdapter = new SearchAdapter(); //创建适配器
        xlv.setAdapter(searchAdapter);
        xlv.setLoadingMoreEnabled(true);//允许加载更多
        xlv.setLoadingListener(this);//加载更多的监听器
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.search)
    public void setOnClick(View view){
        xlv.refresh();
    }

    @Override
    public void onRefresh() {
        if (searchPresenter.isRunning()) {//判断状态
            xlv.refreshComplete();//结束刷新
            return;
        }
        page = 1;
        String search = textSearch.getText().toString().trim();//获取输入的数据
        searchPresenter.reqeust(true, search, 5);//请求
    }

    @Override
    public void onLoadMore() {
        if (searchPresenter.isRunning()) {
            xlv.loadMoreComplete();//隐藏加载圈
            return;
        }
        page++;
        String search = textSearch.getText().toString().trim();//获取输入的数据
        searchPresenter.reqeust(false, search, 5);//加载更多不需要重新请求
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private class SearchCall implements DataCall<ResultLogin<List<Search>>> {
        @Override
        public void success(ResultLogin<List<Search>> data) {
            xlv.refreshComplete();
            xlv.loadMoreComplete();
            searchAdapter.delAll();
            if (data.getStatus().equals("0000")) {
                if (data.getResult().size() == 0) {//查询的list长度为0 表示未查询到数据
                    xlv.setVisibility(View.GONE);//没有数据显示相对应的内容
                    searchNogoods.setVisibility(View.VISIBLE);
                } else {
                    searchNogoods.setVisibility(View.GONE);
                    xlv.setVisibility(View.VISIBLE);
                    searchNogoods.setVisibility(View.VISIBLE);
                    List<Search> result = data.getResult();
                    searchAdapter.addAll(result);
                    searchAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void fail(ApiException e) {
            xlv.loadMoreComplete();
            xlv.refreshComplete();
            UIUtils.showToastSafe("没有合适的商品！！！" + e.getMessage());
        }
    }
}
