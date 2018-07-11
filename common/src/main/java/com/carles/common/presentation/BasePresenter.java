package com.carles.common.presentation;

import com.carles.common.utils.ErrorMessageFactory;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    protected V view;
    protected ErrorMessageFactory errorMessageFactory = new ErrorMessageFactory();

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void onViewCreated(V view) {
        this.view = view;
    }

    @Override
    public void onViewResumed() {}

    @Override
    public void onViewPaused() {}

    @Override
    public void onViewDestroyed() {
        this.view = null;
    }

}
