package com.carles.common.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InteractorTest {

    private Interactor interactor;
    @Mock
    private DisposableObserver disposableObserver;
    @Mock
    private Observable observable;

    @Before
    public void setup() {
        interactor = mock(Interactor.class, Mockito.CALLS_REAL_METHODS);
        Whitebox.setInternalState(interactor, "compositeDisposable", new CompositeDisposable());
   }

    @Test
    public void execute_shouldSubscribeToObservable() {
        Object params = new Object();
        TestObserver testObserver = Observable.empty().test();
        when(interactor.createObservableUsecase(any())).thenReturn(observable);
        when(observable.subscribeWith(disposableObserver)).thenReturn(testObserver);

        interactor.execute(params, disposableObserver);

        verify(interactor).createObservableUsecase(eq(params));
        verify(interactor).addDisposable(testObserver);
    }

    @Test
    public void dispose_shouldUnsubscribeFromObservable() {
        TestObserver testObserver = Observable.empty().test();
        interactor.addDisposable(testObserver);
        interactor.dispose();
        assertTrue(testObserver.isDisposed());
    }
}
