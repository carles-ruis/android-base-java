package com.carles.common.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<T, P> {

    private final CompositeDisposable subscriptions;

    public Interactor() {
        this.subscriptions = new CompositeDisposable();
    }

    protected abstract Observable<T> createObservableUsecase(P params);

    public void execute(P params, DisposableObserver<T> observer) {
        final Observable<T> observable = this.createObservableUsecase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
        addDisposable(observable.subscribeWith(observer));
    }

    void addDisposable(Disposable disposable) {
        subscriptions.add(disposable);
    }

    public void dispose() {
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
    }

}
