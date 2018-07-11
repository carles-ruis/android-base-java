package com.carles.common.domain.interactor;

import io.reactivex.observers.DisposableObserver;

public class InteractorObserver <T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
