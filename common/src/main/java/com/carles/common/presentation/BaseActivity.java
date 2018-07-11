package com.carles.common.presentation;

import com.carles.common.R;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

    protected View progressBar;
    protected AlertDialog alertDialog;

    protected abstract int getLayoutResourceId();
    protected abstract void initViews();
    protected abstract void initComponents();
    protected abstract BaseContract.Presenter getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right_to_left, R.anim.slide_out_from_right_to_left);

        setContentView(getLayoutResourceId());
        progressBar = findViewById(R.id.progressbar_layout);
        initViews();
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPresenter() != null) {
            getPresenter().onViewResumed();
        }
    }

    @Override
    protected void onPause() {
        if (getPresenter() != null) {
            getPresenter().onViewPaused();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onViewDestroyed();
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);
    }

    @Override
    public void showProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(int messageId) {
        showError(messageId, null);
    }

    @Override
    public void showError(int messageId, final BaseContract.OnRetryListener onRetryListener) {
        hideProgress();
        if (!isFinishing()) {
            if (alertDialog == null || !alertDialog.isShowing()) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setMessage(messageId);
                if (onRetryListener != null) {
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(R.string.error_retry, (dialogInterface, which) -> onRetryListener
                        .onRetry());
                }
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
    }

}
