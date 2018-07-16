package com.carles.common.ui;

public interface BaseContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showError(int messageId);

        void showError(int messageId, OnRetryListener onRetryListener);
    }

    interface Presenter <V extends BaseContract.View> {

        V getView();

        void onViewCreated(V view);

        void onViewResumed();

        void onViewPaused();

        void onViewDestroyed();

    }

    interface OnRetryListener {
        void onRetry();
    }

}
