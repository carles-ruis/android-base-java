package com.carles.common.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BasePresenterTest {

    private BasePresenter presenter;
    @Mock
    private BaseContract.View view;
    @Mock
    private Disposable disposable;

    @Before
    public void setup() {
        presenter = mock(BasePresenter.class, CALLS_REAL_METHODS);
        presenter.disposables = new CompositeDisposable();
    }

    @Test
    public void onViewCreated_shouldSetView() {
        presenter.onViewCreated(view);
        assertEquals(view, presenter.view);
    }

    @Test
    public void onViewDestroyed_shouldUnsetView() {
        presenter.view = view;
        presenter.onViewDestroyed();
        assertNull(presenter.view);
        assertTrue(presenter.disposables.isDisposed());
    }

    @Test
    public void addDisposable_shouldAdd() {
        presenter.addDisposable(disposable);
        assertFalse(presenter.disposables.isDisposed());
        assertTrue(presenter.disposables.size() == 1);
    }
}
