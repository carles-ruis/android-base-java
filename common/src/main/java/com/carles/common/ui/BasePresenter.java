package com.carles.common.ui;

import com.carles.common.utils.ErrorMessageFactory;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    protected V view;
    protected ErrorMessageFactory errorMessageFactory = new ErrorMessageFactory();
    protected CompositeDisposable disposables = new CompositeDisposable();
    protected final Scheduler uiScheduler;
    protected final Scheduler processScheduler;

    public BasePresenter(Scheduler uiScheduler, Scheduler processScheduler) {
        this.uiScheduler = uiScheduler;
        this.processScheduler = processScheduler;
    }

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
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        this.view = null;
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

}