package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.poi.data.repository.PoiRepository;
import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BaseContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
public class PoiDetailPresenterTest {

    private TestScheduler testScheduler = new TestScheduler();
    private PoiDetailPresenter presenter;
    @Mock
    private PoiRepository poiRepository;
    @Mock
    private PoiDetailContract.View view;

    @Before
    public void setup() {
        presenter = new PoiDetailPresenter(poiRepository, testScheduler, testScheduler);
        Whitebox.setInternalState(presenter, "view", view);
  }

    @Test
    public void initialize_shouldSetFields() {
        presenter.initialize("id");
        assertEquals("id", presenter.id);
    }

    @Test
    public void onViewCreated_shouldGetPoi() {
        doReturn(Single.just(new Poi())).when(poiRepository).getPoiDetail("id");
        presenter.id = "id";
        presenter.onViewCreated(view);
        verify(view).showProgress();
        verify(poiRepository).getPoiDetail(eq("id"));
    }

    @Test
    public void onGetPoiDetailSuccess_shouldDisplayPoi() {
        Poi poi = new Poi();
        presenter.onGetPoiDetailSuccess(poi);
        verify(view).hideProgress();
        verify(view).displayPoiDetail(eq(poi));
    }

    @Test
    public void onGetPoiDetailError_shouldDisplayErrorAndRetry() {
        presenter.onGetPoiDetailError(new Throwable());
        verify(view).showError(anyInt(), any(BaseContract.OnRetryListener.class));
    }

}
