package com.gj.weidusore.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 接口回调
     */
    public interface ScrollviewListener{
        void OnScrollChange(MyScrollView scrollView, int l, int t, int oldl, int oldt);
    }

    public ScrollviewListener mScrollviewListener;

    public void setScrollviewListener(ScrollviewListener scrollviewListener){
        mScrollviewListener = scrollviewListener;
    }
}