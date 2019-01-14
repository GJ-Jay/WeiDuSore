package com.gj.weidusore.core;

import com.gj.weidusore.core.exception.ApiException;

public interface DataCall<T> {
    void success(T data);
    void fail(ApiException e);
}
