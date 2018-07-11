package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.domain.interactor.GetPoiListInteractor;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.domain.interactor.InteractorObserver;
import com.carles.common.presentation.BaseContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

import static com.carles.carlesbasic.TestHelper.createPoi;
import static com.carles.carlesbasic.TestHelper.createPoiList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class PoiListPresenterTest {

    private PoiListPresenter presenter;
    @Mock
    private GetPoiListInteractor getPoiListInteractor;
    @Mock
    private PoiListContract.View view;

    @Before
    public void setup() {
        presenter = new PoiListPresenter(getPoiListInteractor);
        Whitebox.setInternalState(presenter, "view", view);
    }

    @Test
    public void onViewCreated_shouldGetPoiList() {
        PoiListPresenter spy = spy(presenter);
        spy.onViewCreated(view);
        verify(spy).getPoiList();
    }

    @Test
    public void onViewDestroyed_shouldDisposeInteractor() {
        presenter.onViewDestroyed();
        verify(getPoiListInteractor).dispose();
    }

    @Test
    public void getPoiList_onNext_shouldDisplayPoiList() {
        final List<Poi> poiList = createPoiList("1", "2");
        doAnswer(invocation -> {
            ((InteractorObserver) invocation.getArgument(1)).onNext(poiList);
            return null;
        }).when(getPoiListInteractor).execute(any(), any());

        presenter.getPoiList();
        verify(getPoiListInteractor).execute(any(), any());
        verify(view).displayPoiList(eq(poiList));
    }

    @Test
    public void getPoiList_onError_shouldShowErrorAndRetry() {
        doAnswer(invocation -> {
            ((InteractorObserver) invocation.getArgument(1)).onError(new Throwable());
            return null;
        }).when(getPoiListInteractor).execute(any(), any());
   
        presenter.getPoiList();
        verify(getPoiListInteractor).execute(any(), any());
        verify(view).showError(anyInt(), any(BaseContract.OnRetryListener.class));
    }

    @Test
    public void onPoiClicked_shouldStartPoiDetail() {
        presenter.onPoiClicked(createPoi("1"));
        verify(view).navigateToPoiDetail(eq("1"));
    }
}
