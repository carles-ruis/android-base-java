package com.carles.common;

import android.os.Handler;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private static RxBus INSTANCE;
    private static final long SEND_DELAY_MILLISECONDS = 100;
    private PublishSubject<Object> bus = PublishSubject.create();
    private Handler handler = new Handler();

    public RxBus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RxBus();
        }
        return INSTANCE;
    }

    public void send(Object object) {
        bus.onNext(object);
    }

    public void sendDelayed(Object object) {
        handler.postDelayed(() -> send(object), SEND_DELAY_MILLISECONDS);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

}
