package com.carles.common.presentation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BasePresenterTest {

    private BasePresenter presenter;
    @Mock
    private BaseContract.View view;

    @Before
    public void setup() {
        presenter = mock(BasePresenter.class, CALLS_REAL_METHODS);
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
    }
}
