package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.domain.interactor.GetPoiDetailInteractor;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.domain.interactor.InteractorObserver;
import com.carles.common.presentation.BaseContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static com.carles.carlesbasic.TestHelper.createPoi;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class PoiDetailPresenterTest {

    private PoiDetailPresenter presenter;
    @Mock
    private GetPoiDetailInteractor getPoiDetailInteractor;
    @Mock
    private PoiDetailContract.View view;

    @Before
    public void setup() {
        presenter = new PoiDetailPresenter(getPoiDetailInteractor);
        Whitebox.setInternalState(presenter, "view", view);
  }

    @Test
    public void initialize_shouldSetFields() {
        presenter.initialize("id");
        assertEquals("id", presenter.id);
    }

    @Test
    public void onViewCreated_shouldGetPoi() {
        PoiDetailPresenter spy = Mockito.spy(presenter);
        spy.onViewCreated(view);
        verify(spy).getPoiDetail();
    }

    @Test
    public void onViewDestroyed_shouldDisposeInteractor() {
        presenter.onViewDestroyed();
        verify(getPoiDetailInteractor).dispose();
    }

    @Test
    public void getPoiDetail_onNext_shouldDisplayPoi() {
        doAnswer(invocation -> {
            ((InteractorObserver) invocation.getArgument(1)).onNext(createPoi("1"));
            return null;
        }).when(getPoiDetailInteractor).execute(any(), any());

        presenter.getPoiDetail();
        verify(getPoiDetailInteractor).execute(any(), any());
        verify(view).displayPoiDetail(any(Poi.class));
    }

    @Test
    public void getPoiDetail_onError_shouldDisplayErrorAndRetry() {
        doAnswer(invocation -> {
            ((InteractorObserver) invocation.getArgument(1)).onError(new Throwable());
            return null;
        }).when(getPoiDetailInteractor).execute(any(), any());

        presenter.getPoiDetail();
        verify(getPoiDetailInteractor).execute(any(), any());
        verify(view).showError(anyInt(), any(BaseContract.OnRetryListener.class));
    }

}
